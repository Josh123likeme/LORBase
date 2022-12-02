package me.Josh123likeme.LORBase.ItemHolder;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class COLOUR_SQUARE extends Item {
	
	public COLOUR_SQUARE(Color colour) {
		
		displayName = "Colour Square";
		description.add("A coloured square used for testing");
		
		String red = Integer.toHexString(colour.getRed());
		String green = Integer.toHexString(colour.getGreen());
		String blue = Integer.toHexString(colour.getBlue());

		if (red.length() == 1) red = "0" + red;
		if (green.length() == 1) green = "0" + green;
		if (blue.length() == 1) blue = "0" + blue;

		String hexColor = "§" + red + green + blue;
		
		description.add("Colour: " + hexColor + " " + colour.getRed() + " " + colour.getGreen() + " " + colour.getBlue());
		
		defaultTexture = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		
		defaultTexture.setRGB(0, 0, colour.getRGB());
		
	}
	
}
