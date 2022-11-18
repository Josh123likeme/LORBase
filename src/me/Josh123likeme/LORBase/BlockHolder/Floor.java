package me.Josh123likeme.LORBase.BlockHolder;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import me.Josh123likeme.LORBase.ResourceLoader;
import assets.Assets;

public enum Floor {

	LABYRINTH_FLOOR("LABYRINTH_FLOOR.png"),
	
	STATUE_PODIUM("STATUE_PODIUM.png"),
	
	MOGUS("MOGUS.png"), //for testing high res textures
	DEBUG_16("DEBUG_16.png"),
	DEBUG_32("DEBUG_32.png"),
	DEBUG_64("DEBUG_64.png"),
	
	;
	
	String texturePath;
	
	Floor(String texturePath){
		
		this.texturePath = texturePath;
		
	}
	
	public static HashMap<Floor, BufferedImage> loadTextures() {
		
		HashMap<Floor, BufferedImage> textures = new HashMap<Floor, BufferedImage>();
		
		for (int i = 0; i < Floor.values().length; i++) {
			
			BufferedImage image = null;
				
		    image = Assets.LoadTextureFromAssets("textures/block/" + Floor.values()[i].texturePath);
			
			textures.put(Floor.values()[i], image);
			
		}
		
		return textures;
		
	}
	
	public BufferedImage getTexture() {
		
		return ResourceLoader.getTexture(this);
		
	}
	
}
