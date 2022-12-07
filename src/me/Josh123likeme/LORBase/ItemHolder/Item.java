package me.Josh123likeme.LORBase.ItemHolder;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import assets.Assets;
import me.Josh123likeme.LORBase.TUID;

public abstract class Item {
	
	protected String tuid = TUID.generateRandomTimeStampedId();
	
	protected String displayName;
	protected List<String> description = new ArrayList<String>();
	
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
		
		 defaultTexture = Assets.LoadTextureFromAssets("textures/item/" + this.getClass().getSimpleName() + ".png");
		
	}
	
	public String getDisplayName() {
		
		return displayName;
		
	}
	
	public String[] getDescription() {
		
		String[] content = new String[description.size()];
		
		for (int i = 0; i < description.size(); i++) {
			
			content[i] = description.get(i);
			
		}
		
		return content;
		
	}
	
}
