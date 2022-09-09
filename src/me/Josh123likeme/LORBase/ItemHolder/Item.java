package me.Josh123likeme.LORBase.ItemHolder;

import java.awt.image.BufferedImage;

import assets.Assets;

public abstract class Item {
	
	protected BufferedImage defaultTexture;
	
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
		
		Assets.LoadTextureFromAssets("textures/item/" + this.getClass().getName() + ".png");
		
	}
	
}
