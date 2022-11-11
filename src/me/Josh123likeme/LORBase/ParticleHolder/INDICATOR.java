package me.Josh123likeme.LORBase.ParticleHolder;

import java.awt.image.BufferedImage;

import me.Josh123likeme.LORBase.Types.Vector2D;

public class INDICATOR extends Particle {

	public INDICATOR(Vector2D pos) {
		
		this.pos = pos;
		
		defaultTexture = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		
		for (int y = 0; y < defaultTexture.getHeight(); y++) {
			
			for (int x = 0; x < defaultTexture.getWidth(); x++) {
				
				defaultTexture.setRGB(x, y, 255 << 24 | 30 << 16 | 57 << 8 | 218);
				
			}
			
		}
		
		
		
	}
	
	@Override
	public double getSize() {
		// TODO Auto-generated method stub
		return 0.1;
	}

	
	
}
