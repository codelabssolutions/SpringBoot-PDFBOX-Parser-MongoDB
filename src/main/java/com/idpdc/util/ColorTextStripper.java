package com.idpdc.util;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.contentstream.operator.color.SetNonStrokingColor;
import org.apache.pdfbox.contentstream.operator.color.SetNonStrokingColorN;
import org.apache.pdfbox.contentstream.operator.color.SetNonStrokingColorSpace;
import org.apache.pdfbox.contentstream.operator.color.SetNonStrokingDeviceCMYKColor;
import org.apache.pdfbox.contentstream.operator.color.SetNonStrokingDeviceGrayColor;
import org.apache.pdfbox.contentstream.operator.color.SetNonStrokingDeviceRGBColor;
import org.apache.pdfbox.contentstream.operator.color.SetStrokingColor;
import org.apache.pdfbox.contentstream.operator.color.SetStrokingColorN;
import org.apache.pdfbox.contentstream.operator.color.SetStrokingColorSpace;
import org.apache.pdfbox.contentstream.operator.color.SetStrokingDeviceCMYKColor;
import org.apache.pdfbox.contentstream.operator.color.SetStrokingDeviceGrayColor;
import org.apache.pdfbox.contentstream.operator.color.SetStrokingDeviceRGBColor;
import org.apache.pdfbox.pdmodel.graphics.color.PDColorSpace;
import org.apache.pdfbox.pdmodel.graphics.state.RenderingMode;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

public class ColorTextStripper extends PDFTextStripper
{
	   Map<TextPosition, RenderingMode> renderingMode = new HashMap<TextPosition, RenderingMode>();
	    Map<TextPosition, PDColorSpace> strokingColor = new HashMap<TextPosition,PDColorSpace>();
	    Map<TextPosition, PDColorSpace> nonStrokingColor = new HashMap<TextPosition,PDColorSpace>();

	    final static List<Integer> FILLING_MODES = Arrays.asList(0, 2, 4, 6);
	    final static List<Integer> STROKING_MODES = Arrays.asList(1, 2, 5, 6);
	    final static List<Integer> CLIPPING_MODES = Arrays.asList(4, 5, 6, 7);

	
    public ColorTextStripper() throws IOException
    {
        super();
        setSuppressDuplicateOverlappingText(false);

        addOperator(new SetStrokingColorSpace());
        addOperator(new SetNonStrokingColorSpace());
        addOperator(new SetStrokingDeviceCMYKColor());
        addOperator(new SetNonStrokingDeviceCMYKColor());
        addOperator(new SetNonStrokingDeviceRGBColor());
        addOperator(new SetStrokingDeviceRGBColor());
        addOperator(new SetNonStrokingDeviceGrayColor());
        addOperator(new SetStrokingDeviceGrayColor());
        addOperator(new SetStrokingColor());
        addOperator(new SetStrokingColorN());
        addOperator(new SetNonStrokingColor());
        addOperator(new SetNonStrokingColorN());
    }

    @Override
    protected void processTextPosition(TextPosition text)
    {
        renderingMode.put(text, getGraphicsState().getTextState().getRenderingMode());
        strokingColor.put(text, getGraphicsState().getStrokingColor().getColorSpace());
        nonStrokingColor.put(text, getGraphicsState().getNonStrokingColor().getColorSpace());

        super.processTextPosition(text);
    }

 
    @Override
    protected void writeString(String text, List<TextPosition> textPositions) throws IOException
    {
        for (TextPosition textPosition: textPositions)
        {
            RenderingMode charRenderingMode = renderingMode.get(textPosition);
            PDColorSpace charStrokingColor = strokingColor.get(textPosition);
            PDColorSpace charNonStrokingColor = nonStrokingColor.get(textPosition);
            System.out.println(text+" : "+charRenderingMode);
           // System.out.println(charStrokingColor);
            System.out.println(charNonStrokingColor.getNumberOfComponents());
            StringBuilder textBuilder = new StringBuilder();
        //    textBuilder.append(textPosition.getCharacter()).append("{");
/*
            if (FILLING_MODES.contains(charRenderingMode))
            {
                textBuilder.append("FILL:")
                           .append(toString(charNonStrokingColor))
                           .append(';');
            }

            if (STROKING_MODES.contains(charRenderingMode))
            {
                textBuilder.append("STROKE:")
                           .append(toString(charStrokingColor))
                           .append(';');
            }

            if (CLIPPING_MODES.contains(charRenderingMode))
            {
                textBuilder.append("CLIP;");
            }
*/
           // textBuilder.append("}");
            writeString(textBuilder.toString());
        }
    }

    String toString(float[] values)
    {
        if (values == null)
            return "null";
        StringBuilder builder = new StringBuilder();
        switch(values.length)
        {
        case 1:
            builder.append("GRAY"); break;
        case 3:
            builder.append("RGB"); break;
        case 4:
            builder.append("CMYK"); break;
        default:
            builder.append("UNKNOWN");
        }
        for (float f: values)
        {
            builder.append(' ')
                   .append(f);
        }

        return builder.toString();
    }
}