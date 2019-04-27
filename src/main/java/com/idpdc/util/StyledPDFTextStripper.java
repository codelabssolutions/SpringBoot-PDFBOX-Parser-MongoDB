package com.idpdc.util;


import java.io.IOException;
import java.util.List;

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
import org.apache.pdfbox.pdmodel.graphics.color.PDColor;
import org.apache.pdfbox.pdmodel.graphics.state.RenderingMode;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;


public class StyledPDFTextStripper extends PDFTextStripper {
	private String page_ignore=null;
	
	public StyledPDFTextStripper() throws IOException {
		super();
		//add to ignore list
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
		page_ignore="page ";
	}

	@Override
	protected void writeString(String text, List<TextPosition> textPositions) throws IOException {
		System.out.println(text);
		for (TextPosition textPosition : textPositions) {
			String style = determineStyle(textPosition);
			//System.out.println(text+":"+style);
			if (!style.equals(currentStyle)) {
				output.write(style.toString());
				currentStyle = style;
			}

		}
		if(!text.toLowerCase().startsWith(page_ignore)) {
			//lookup summary
			output.write(text);
		}
	}

	private String determineStyle(TextPosition textPosition) {
		String result="";
		super.processTextPosition(textPosition);
		
        PDColor strokingColor = getGraphicsState().getStrokingColor();
        PDColor nonStrokingColor = getGraphicsState().getNonStrokingColor();
        String unicode = textPosition.getUnicode();
        RenderingMode renderingMode = getGraphicsState().getTextState().getRenderingMode();
       // System.out.println("Unicode:            " + unicode);
       // System.out.println("Rendering mode:     " + renderingMode);
        System.out.println("Stroking color:     " + strokingColor.getColorSpace().getName());
        System.out.println("Non-Stroking color: " + nonStrokingColor.getColorSpace().getName());
       // System.out.println("Non-Stroking color: " + nonStrokingColor);
        //System.out.println();
		//System.out.println(textPosition.getFont().getName()+" Size"+textPosition.getFontSize());
		if (textPosition.getFont().getName().toLowerCase().contains("bold")) {
			if(textPosition.getFontSize()==20) {
				result="bold20:";
			}else {
				result= "bold:";
			}
			//System.out.println(textPosition.getFontSize());
		}

		if (textPosition.getFont().getName().toLowerCase().contains("italic")) {
			result="italic:";
		}
		return result;
	}

	String currentStyle = null;
}
