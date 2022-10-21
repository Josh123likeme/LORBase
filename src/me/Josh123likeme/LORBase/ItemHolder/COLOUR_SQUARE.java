package me.Josh123likeme.LORBase.ItemHolder;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class COLOUR_SQUARE extends Item {
	
	public COLOUR_SQUARE(Color colour) {
		
		defaultTexture = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		
		defaultTexture.setRGB(0, 0, colour.getRGB());
		
	}
	
}
