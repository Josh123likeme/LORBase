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
		
		facing %= 360;
		
		if (facing < 0) facing = 360 + facing;
		
		this.facing = facing;
		
	}
	/*
	public boolean canSee(Vector2D target) {
		
		if (!(this instanceof ISmart)) throw new IllegalArgumentException("This entity cant see!");

		if (pos.distanceTo(target) > ((ISmart) this).getViewDistance()) return false;
		
		Vector2D looking = new Vector2D(facing);
		Vector2D direction = new Vector2D(target.X, target.Y).subtract(pos).normalise();

		if (looking.angleBetween(direction) > ((ISmart) this).getFieldOfView()) return false;
		
		Vector2D current = pos.clone();
		
		while (current.distanceTo(target) > 0.5) {
			
			if (world.getWall((int) current.X, (int) current.Y) != Wall.AIR) return false;
			
			current.add(direction);
			
		}
		
		return true;
		
	}
	*/
	public abstract double getSize();
	
}
