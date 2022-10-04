package me.Josh123likeme.LORBase.UI;

import java.awt.image.BufferedImage;

public class Element {

	private String id;
	
	private String text;
	private BufferedImage image;
	
	private double posX;
	private double posY;
	private double width;
	private double height;
	
	public Element(String id, String text, BufferedImage image, double x, double y, double sizeX, double sizeY) {
		
		this.id = id;
		
		this.text = text;
		this.image = image;
		
		this.posX = x;
		this.posY = y;
		this.width = sizeX;
		this.height = sizeY;
		
	}
	
	public boolean isTextElement() {
		
		if (text != null && image == null) return true;
		if (text == null && image != null) return false;
		
		throw new IllegalStateException("For some reason the element is neither a text element or an image element");
		
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
