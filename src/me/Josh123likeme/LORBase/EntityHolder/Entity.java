package me.Josh123likeme.LORBase.EntityHolder;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import me.Josh123likeme.LORBase.ResourceLoader;
import me.Josh123likeme.LORBase.BlockHolder.Floor;

public enum Entity {

	PLAYER("PLAYER.png"),
	
	;
	
	
	String texturePath;
	
	Entity(String texturePath){
		
		this.texturePath = texturePath;
		
	}
	
	public static void loadTextures() {
		
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
			
			ResourceLoader.entityTextures.put(Entity.values()[i], image);
			
		}
		
	}
	
}
