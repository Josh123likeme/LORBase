package me.Josh123likeme.LORBase.UI;

import java.awt.image.*;

class Button {

	private String id;
	
	private String text;
	private BufferedImage image;
	private String description;
	
	private double posX;
	private double posY;
	private double width;
	private double height;
	
	public final double borderSize = 0.1d;
	
	public Button(String id, String text, BufferedImage image, String description, double x, double y, double sizeX, double sizeY) {
		
		this.id = id;
		
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
	
	public String getId() {
		
		return id;
		
	}
	
	public String getText() {
		
		return text;
		
	}
	
	void setText(String text) {
		
		this.text = text;
		
	}
	
	public BufferedImage getImage() {
		
		return image;
		
	}
	
	void setImage(BufferedImage image) {
		
		this.image = image;
		
	}
	
	public String getDescription() {
		
		return description;
		
	}
	
	void setDescription(String description) {
		
		this.description = description;
		
	}
	
	public double getX() {
		
		return posX;
		
	}
	
	public double getY() {
		
		return posY;
		
	}
	
	public double getWidth() {
		
		return width;
		
	}
	
	public double getHeight() {
		
		return height;
		
	}
	
}
