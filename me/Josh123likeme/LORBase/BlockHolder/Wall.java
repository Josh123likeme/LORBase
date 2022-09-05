package me.Josh123likeme.LORBase.BlockHolder;

import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.*;

import assets.Assets;
import me.Josh123likeme.LORBase.ResourceLoader;

public enum Wall {

	LABYRINTH_WALL("LABYRINTH_WALL.png"),
	
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
