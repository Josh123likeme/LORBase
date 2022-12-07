package me.Josh123likeme.LORBase.BlockHolder;

import java.awt.image.BufferedImage;
import java.util.HashMap;

import me.Josh123likeme.LORBase.ResourceLoader;
import assets.Assets;

public enum Wall {

	LABYRINTH_WALL("LABYRINTH_WALL.png"),
	
	STATUE_PODIUM("STATUE_PODIUM.png"),
	
	AIR("AIR.png");
	
	;
	
	String texturePath;
	
	Wall(String texturePath){
		
		this.texturePath = texturePath;
		
	}
	
	public static HashMap<Wall, BufferedImage> loadTextures() {
		
		HashMap<Wall, BufferedImage> textures = new HashMap<Wall, BufferedImage>();
		
		for (int i = 0; i < Wall.values().length; i++) {
			
			BufferedImage image = null;
			
			image = Assets.LoadTextureFromAssets("textures/block/" + Wall.values()[i].texturePath);
			
			textures.put(Wall.values()[i], image);
			
		}
		
		return textures;
		
	}
	
	public BufferedImage getTexture() {
		
		return ResourceLoader.getTexture(this);
		
	}
	
}
