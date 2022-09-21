package me.Josh123likeme.LORBase.UI;

import java.awt.image.*;

public class Button {

	private String text;
	private BufferedImage image;
	private String description;
	
	private int posX;
	private int posY;
	private int width;
	private int height;
	
	/**
	 * Used for text buttons
	 * @param text
	 * @param image
	 * @param description
	 * @param x
	 * @param y
	 * @param sizeX
	 * @param sizeY
	 */
	public Button(String text, BufferedImage image, String description, int x, int y, int sizeX, int sizeY) {
		
		this.text = text;
		this.image = image;
		this.description = description;
		
		this.posX = x;
		this.posY = y;
		this.width = sizeX;
		this.height = sizeY;
		
	}

	public boolean isTextButton() {
		
		if (text != null && image == null) return true;
		if (text == null && image != null) return false;
		
		throw new IllegalStateException("For some reason the button is neither a text button or an image button");
		
	}
	
	public String getText() {
		
		return text;
		
	}
	
	public BufferedImage getImage() {
		
		return image;
		
	}
	
	public String getDescription() {
		
		return description;
		
	}
	
	public int getX() {
		
		return posX;
		
	}
	
	public int getY() {
		
		return posY;
		
	}
	
	public int getWidth() {
		
		return width;
		
	}
	
	public int getHeight() {
		
		return height;
		
	}
	
}
