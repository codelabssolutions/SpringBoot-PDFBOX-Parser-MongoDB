package com.codelabs.main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.contentstream.PDFStreamEngine;
import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
 
/**
* This is an example on how to extract images from pdf.
*/
public class SaveImagesInPdf extends PDFStreamEngine
{
    /**
     * Default constructor.
     *
     * @throws IOException If there is an error loading text stripper properties.
     */
    public SaveImagesInPdf() throws IOException
    {
    }
    
    public int imageNumber = 1;
 
    /**
     * @param args The command line arguments.
     *
     * @throws IOException If there is an error parsing the document.
     */
    public static void main( String[] args ) throws IOException
    {
       // PDDocument document = null;
        String fileName = "F:\\JUSTMENTOR_WORKSPACE\\pdfparser1\\docs\\med\\16212 - MARCELO STEINEMEYER PRADA 533973.pdf";
        try {
        	PDDocument document = PDDocument.load(new File(fileName));
        	PDFRenderer pdfRenderer = new PDFRenderer(document);
        	BufferedImage bim = pdfRenderer.renderImageWithDPI(5, 300, ImageType.RGB);
       	    // suffix in filename will be used as the file format
        	ImageIOUtil.writeImage(bim, "D://tests.png", 300);
        	
    } catch (Exception e) {
        e.printStackTrace();
    }
 
	}

 
    /**
     * @param operator The operation to perform.
     * @param operands The list of arguments.
     *
     * @throws IOException If there is an error processing the operation.
     */
    @Override
    protected void processOperator( Operator operator, List<COSBase> operands) throws IOException
    {
        String operation = operator.getName();
        if( "Do".equals(operation) )
        {
            COSName objectName = (COSName) operands.get( 0 );
            PDXObject xobject = getResources().getXObject( objectName );
            if( xobject instanceof PDImageXObject)
            {
                PDImageXObject image = (PDImageXObject)xobject;
                int imageWidth = image.getWidth();
                int imageHeight = image.getHeight();
 
              // same image to local
                BufferedImage bImage = new BufferedImage(imageWidth,imageHeight,BufferedImage.TYPE_INT_ARGB);
                bImage = image.getImage();
                ImageIO.write(bImage,"PNG",new File("image_"+imageNumber+".png"));
                System.out.println("Image saved.");
                imageNumber++;
                
            }
            else if(xobject instanceof PDFormXObject)
            {
                PDFormXObject form = (PDFormXObject)xobject;
                showForm(form);
            }
        }
        else
        {
            super.processOperator( operator, operands);
        }
    }
 
}