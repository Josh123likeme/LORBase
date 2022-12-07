package me.Josh123likeme.LORBase.ParticleHolder;

import java.awt.image.BufferedImage;
import java.util.Random;

import assets.Assets;
import me.Josh123likeme.LORBase.Types.Vector2D;

public abstract class Particle {

	protected static Random random = new Random();
	
	protected BufferedImage defaultTexture;
	
	Vector2D pos;
	long expiryTime; //in nano seconds
	
	/**
	 * used for accessing the texture
	 * @return
	 */
	public BufferedImage getTexture() {
		
		if (defaultTexture == null) loadTextures();
		
		return defaultTexture;
		
	}
	
	/**
	 * loads textures into memory
	 */
	protected void loadTextures() {
		
		 defaultTexture = Assets.LoadTextureFromAssets("textures/particle/" + this.getClass().getSimpleName() + ".png");
		
	}
	
	public boolean isAlive() {
		
		if (System.nanoTime() > expiryTime) return false;
		return true;
		
	}
	
	public Vector2D getPosition() {
		
		return pos;
		
	}
	
	public abstract double getSize();
	
}
