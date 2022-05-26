package me.Josh123likeme.LORBase.EntityHolder;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import me.Josh123likeme.LORBase.ResourceLoader;
import me.Josh123likeme.LORBase.BlockHolder.Floor;
import me.Josh123likeme.LORBase.BlockHolder.Wall;

public enum Entity {

	PLAYER("PLAYER.png"),
	
	;
	
	
	String texturePath;
	
	Entity(String texturePath){
		
		this.texturePath = texturePath;
		
	}
	
	public static HashMap<Entity, BufferedImage> loadTextures() {
		
		HashMap<Entity, BufferedImage> textures = new HashMap<Entity, BufferedImage>();
		
		for (int i = 0; i < Entity.values().length; i++) {
			
			BufferedImage image = null;
			
			try {
				
			    image = ImageIO.read(new File("./src/assets/textures/entity/" + Entity.values()[i].texturePath));
			    
			}
			catch (IOException e) {
				
				try {
					
					image = ImageIO.read(new File("./src/assets/textures/DEFAULT.png"));
					
				}
				catch (IOException e1) {}
				
			}
			
			textures.put(Entity.values()[i], image);
			
		}
		
		return textures;
		
	}
	
}
