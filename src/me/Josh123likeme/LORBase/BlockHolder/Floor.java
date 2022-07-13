package me.Josh123likeme.LORBase.BlockHolder;

import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.*;

import assets.Assets;
import me.Josh123likeme.LORBase.ResourceLoader;

public enum Floor {

	LABYRINTH_FLOOR("LABYRINTH_FLOOR.png"),
	MOGUS("MOGUS.png"), //for testing high res textures
	
	;
	
	String texturePath;
	
	Floor(String texturePath){
		
		this.texturePath = texturePath;
		
	}
	
	public static HashMap<Floor, BufferedImage> loadTextures() throws IOException {
		
		HashMap<Floor, BufferedImage> textures = new HashMap<Floor, BufferedImage>();
		
		for (int i = 0; i < Floor.values().length; i++) {
			
			BufferedImage image = null;
				
		    image = Assets.LoadTextureFromAssets("./textures/block/" + Floor.values()[i].texturePath);
			
			textures.put(Floor.values()[i], image);
			
		}
		
		return textures;
		
	}
	
}
