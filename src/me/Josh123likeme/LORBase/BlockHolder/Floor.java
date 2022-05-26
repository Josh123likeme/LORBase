package me.Josh123likeme.LORBase.BlockHolder;

import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.*;

import me.Josh123likeme.LORBase.ResourceLoader;

public enum Floor {

	LABYRINTH_FLOOR("LABYRINTH_FLOOR.png"),
	
	;
	
	String texturePath;
	
	Floor(String texturePath){
		
		this.texturePath = texturePath;
		
	}
	
	public static HashMap<Floor, BufferedImage> loadTextures() {
		
		HashMap<Floor, BufferedImage> textures = new HashMap<Floor, BufferedImage>();
		
		for (int i = 0; i < Floor.values().length; i++) {
			
			BufferedImage image = null;
			
			try {
				
			    image = ImageIO.read(new File("./src/assets/textures/block/" + Floor.values()[i].texturePath));
			    
			}
			catch (IOException e) {
				
				try {
					
					image = ImageIO.read(new File("./src/assets/textures/DEFAULT.png"));
					
				}
				catch (IOException e1) {}
				
			}
			
			textures.put(Floor.values()[i], image);
			
		}
		
		return textures;
		
	}
	
}
