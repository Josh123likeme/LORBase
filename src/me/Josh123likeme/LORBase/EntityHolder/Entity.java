package me.Josh123likeme.LORBase.EntityHolder;

import java.awt.image.BufferedImage;
import java.util.Random;

import me.Josh123likeme.LORBase.BlockHolder.Wall;
import me.Josh123likeme.LORBase.GameHolder.World;
import me.Josh123likeme.LORBase.Types.Vector2D;
import assets.Assets;

public abstract class Entity {
	
	protected static Random random = new Random();
	
	protected World world;
	
	protected Vector2D pos;
	protected double facing;
	
	protected BufferedImage defaultTexture;
	
	public Entity(World world, Vector2D pos, double facing) {
		
		this.world = world;
		this.pos = pos;
		this.facing = facing;
		
	}
	
	/**
	 * override this to give conditional textures
	 * @return
	 */
	public BufferedImage getTexture() {
		
		if (defaultTexture == null) loadTextures();
		
		return defaultTexture;
		
	}
	
	/**
	 * override this to load textures
	 */
	protected void loadTextures() {
		
		 defaultTexture = Assets.LoadTextureFromAssets("textures/entity/" + this.getClass().getSimpleName() + ".png");
		
	}
	
	/**
	 * override this to make your own update method
	 * 
	 * @param world
	 */
	public void update(World world) {
		
		if (this instanceof ISmart) {
			
			((ISmart) this).updateBrain();
			
		}
		
	}
	
	public void moveEntity(Vector2D pos, World world) {
		
		if (this instanceof ICollidable) {
			
			if (world.getWall((int) pos.X, (int) pos.Y) != Wall.AIR) return;
			if (world.getWall((int) (pos.X + getSize()), (int) pos.Y) != Wall.AIR) return;
			if (world.getWall((int) pos.X, (int) (pos.Y + getSize())) != Wall.AIR) return;
			if (world.getWall((int) (pos.X + getSize()), (int) (pos.Y + getSize())) != Wall.AIR) return;
			
		}
		
		this.pos = pos;
		
	}
	
	public Vector2D getPosition() {
		
		return pos;
		
	}
	
	public void setPosition(Vector2D position) {
		
		pos = position.clone();
		
	}
	
	public double getFacing() {
		
		return facing;
		
	}
	
	public void setFacing(double facing) {
		
		this.facing = facing;
		
	}
	
	public abstract double getSize();
	
	public double getDistance(double x, double y) {
		
		return Math.sqrt((pos.X - x) * (pos.X - x) + (pos.Y - y) * (pos.Y - y));
		
	}
	
}
