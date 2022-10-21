package me.Josh123likeme.LORBase.EntityHolder;

import java.awt.image.BufferedImage;

import me.Josh123likeme.LORBase.GameHolder.World;
import me.Josh123likeme.LORBase.Types.Vector2D;
import assets.Assets;

public abstract class Entity {
	
	protected Vector2D pos;
	protected double facing;
	protected double size;
	
	protected BufferedImage defaultTexture;
	
	public Entity(Vector2D pos, double facing){
		
		this.pos = pos;
		this.facing = facing;
		
	}
	
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
		
		 defaultTexture = Assets.LoadTextureFromAssets("textures/entity/" + this.getClass().getSimpleName() + ".png");
		
	}
	
	public void moveEntity(Vector2D pos, World world) {
		
		if (this instanceof ICollidable){
			
			if (world.getWall((int) pos.X, (int) pos.Y) != null) {
				
				return;
				
			}
			if (world.getWall((int) (pos.X + size), (int) pos.Y) != null) {
				
				return;
				
			}
			if (world.getWall((int) pos.X, (int) (pos.Y + size)) != null) {
				
				return;
				
			}
			if (world.getWall((int) (pos.X + size), (int) (pos.Y + size)) != null) {
				
				return;
				
			}
			
		}
		
		this.pos = pos;
		
	}
	
	public Vector2D getPosition() {
		
		return pos;
		
	}
	
	public double getFacing() {
		
		return facing;
		
	}
	
	public void setFacing(double facing) {
		
		this.facing = facing;
		
	}
	
	public double getSize() {
		
		return size;
		
	}
	
	public double getDistance(double x, double y) {
		
		return Math.sqrt((pos.X - x) * (pos.X - x) + (pos.Y - y) * (pos.Y - y));
		
	}
	
}
