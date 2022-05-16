package me.Josh123likeme.LORBase;

import java.util.HashMap;
import java.awt.image.*;

import me.Josh123likeme.LORBase.BlockHolder.Floor;
import me.Josh123likeme.LORBase.BlockHolder.Wall;
import me.Josh123likeme.LORBase.EntityHolder.Entity;
import me.Josh123likeme.LORBase.EntityHolder.EntityBase;

public abstract class ResourceLoader {

	public static HashMap<Floor, BufferedImage> floorTextures = new HashMap<Floor, BufferedImage>();
	public static HashMap<Wall, BufferedImage> wallTextures = new HashMap<Wall, BufferedImage>();
	public static HashMap<Entity, BufferedImage> entityTextures = new HashMap<Entity, BufferedImage>();
	
	public static void loadResources() {
		
		Floor.loadTextures();
		Wall.loadTextures();
		Entity.loadTextures();
		
	}
	
	public static BufferedImage copyImage(BufferedImage source){
	    BufferedImage bi = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
	    byte[] sourceData = ((DataBufferByte)source.getRaster().getDataBuffer()).getData();
	    byte[] biData = ((DataBufferByte)bi.getRaster().getDataBuffer()).getData();
	    System.arraycopy(sourceData, 0, biData, 0, sourceData.length);
	    return bi;
	}
	
}
