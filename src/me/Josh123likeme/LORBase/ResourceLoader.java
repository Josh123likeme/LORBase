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
	
}
