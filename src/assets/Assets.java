package assets;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class Assets {

	public static BufferedImage LoadTextureFromAssets(String path) {
		
		try {
			
			return ImageIO.read(Assets.class.getResourceAsStream(path));
			
		}
		catch (IOException e) {
			
			System.out.println("couldn't find texture at " + path + "\nusing default texture instead");
			
			try {
				
				return ImageIO.read(Assets.class.getResourceAsStream("./textures/DEFAULT.png"));
				
			} 
			catch (IOException e1) {
				
				System.out.println("Couldn't find default texture! THIS IS A BIG PROBLEM");
				
				return null;
				
			}
			
		}
		
	}
	
}
