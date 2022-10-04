package assets;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class Assets {

	private static BufferedImage defaultTexture;
	
	public static BufferedImage LoadTextureFromAssets(String path){
		
		try {
			
			return ImageIO.read(Assets.class.getResourceAsStream(path));
			
		}
		catch (IOException | IllegalArgumentException e) {
			
			System.out.println("couldn't find texture at " + path + "\nusing default texture instead");	
			
			if (defaultTexture == null) generateDefaultTexture();
			
			return defaultTexture;
			
		}
		
	}
	
	private static void generateDefaultTexture() {
		
		BufferedImage texture = new BufferedImage(2, 2, BufferedImage.TYPE_INT_ARGB);
		
		int col = (255 << 24) | (0 << 16) | (0 << 8) | 0;
		texture.setRGB(0, 0, col);
		texture.setRGB(1, 1, col);
		
		col = (255 << 24) | (170 << 16) | (45 << 8) | 170;
		texture.setRGB(1, 0, col);
		texture.setRGB(0, 1, col);
		
		defaultTexture = texture;
		
	}
	
}
