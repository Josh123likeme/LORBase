package me.Josh123likeme.LORBase.BlockHolder;

import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.*;

import me.Josh123likeme.LORBase.ResourceLoader;

public enum Wall {

	LABYRINTH_WALL("LABYRINTH_WALL.png"),
	
	;
	
	String texturePath;
	
	Wall(String texturePath){
		
		this.texturePath = texturePath;
		
	}
	
	public static void loadTextures() {
		
		for (int i = 0; i < Wall.values().length; i++) {
			
			BufferedImage image = null;
			
			try {
				
			    image = ImageIO.read(new File("./src/assets/textures/block/" + Wall.values()[i].texturePath));
			    
			}
			catch (IOException e) {
				
				try {
					
					image = ImageIO.read(new File("./src/assets/textures/DEFAULT.png"));
					
				}
				catch (IOException e1) {}
				
			}
			
			ResourceLoader.wallTextures.put(Wall.values()[i], image);
			
		}
		
	}
	
}
