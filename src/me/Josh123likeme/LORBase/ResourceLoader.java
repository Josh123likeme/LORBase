package me.Josh123likeme.LORBase;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.HashMap;

import me.Josh123likeme.LORBase.BlockHolder.Floor;
import me.Josh123likeme.LORBase.BlockHolder.Wall;
import me.Josh123likeme.LORBase.EntityHolder.Entity;

public abstract class ResourceLoader {

	private static HashMap<Floor, BufferedImage> floorTextures = new HashMap<Floor, BufferedImage>();
	private static HashMap<Wall, BufferedImage> wallTextures = new HashMap<Wall, BufferedImage>();
	private static HashMap<Entity, BufferedImage> entityTextures = new HashMap<Entity, BufferedImage>();
	
	public static void loadResources() {
		
		floorTextures = Floor.loadTextures();
		wallTextures = Wall.loadTextures();
		entityTextures = Entity.loadTextures();
		
	}
	
	public static BufferedImage getTexture(Floor floor) {
		
		return floorTextures.get(floor);
		
	}
	
	public static BufferedImage getTexture(Wall wall) {
		
		return wallTextures.get(wall);
		
	}

	public static BufferedImage getTexture(Entity entity) {
	
	return entityTextures.get(entity);
	
	}
	
	public static BufferedImage copyImage(BufferedImage source){
		
		//courtesy of clic on stack overflow
		
	    BufferedImage bi = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
	    byte[] sourceData = ((DataBufferByte)source.getRaster().getDataBuffer()).getData();
	    byte[] biData = ((DataBufferByte)bi.getRaster().getDataBuffer()).getData();
	    System.arraycopy(sourceData, 0, biData, 0, sourceData.length);
	    return bi;
	}
	
	public static BufferedImage rotateImageByDegrees(BufferedImage source, double angle) {
		
		//courtesy of MadProgrammer on stack overflow
		
		double rads = Math.toRadians(angle);
	    double sin = Math.abs(Math.sin(rads)), cos = Math.abs(Math.cos(rads));
	    int w = source.getWidth();
	    int h = source.getHeight();
	    int newWidth = (int) Math.floor(w * cos + h * sin);
	    int newHeight = (int) Math.floor(h * cos + w * sin);

	    BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2d = rotated.createGraphics();
	    AffineTransform at = new AffineTransform();
	    at.translate((newWidth - w) / 2, (newHeight - h) / 2);

	    int x = w / 2;
	    int y = h / 2;

	    at.rotate(rads, x, y);
	    g2d.setTransform(at);
	    g2d.drawImage(source, 0, 0, null);
	    g2d.dispose();

	    return rotated;
		
	}
	
}
