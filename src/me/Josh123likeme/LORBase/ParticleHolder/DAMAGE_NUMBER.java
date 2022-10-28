package me.Josh123likeme.LORBase.ParticleHolder;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import me.Josh123likeme.LORBase.Types.Vector2D;
import me.Josh123likeme.LORBase.UI.Text;

public class DAMAGE_NUMBER extends Particle {

	String content;
	
	public DAMAGE_NUMBER(Vector2D pos, double damage) {
		
		expiryTime = System.nanoTime() + 5000000000L;
		this.pos = pos.clone();
		
		content = generateRandomColour() + " " + damage;
		
		BufferedImage temp = new BufferedImage(20, 20, BufferedImage.TYPE_INT_ARGB);
		Graphics graphics = temp.getGraphics();
		graphics.setFont(new Font(graphics.getFont().getFontName(), Font.PLAIN, 20)); 
		
		int width = graphics.getFontMetrics().stringWidth(Double.toString(damage));
		
		defaultTexture = new BufferedImage(width, 20, BufferedImage.TYPE_INT_ARGB);
		graphics = defaultTexture.getGraphics();
		graphics.setFont(new Font(graphics.getFont().getFontName(), Font.PLAIN, 20)); 
		
		Text.writeText(graphics, content, 0, 0, 20);
		
	}
	
	private String generateRandomColour() {
		
		String green = Integer.toHexString(random.nextInt(255));
		
		if (green.length() == 1) green = "0" + green;

		return "§FF" + green + "00";
		
	}
	
	
	@Override
	public BufferedImage getTexture() {
		
		return defaultTexture;
		
	}
	

	@Override
	public double getSize() {
		
		return 0.5d;
	}
	
}
