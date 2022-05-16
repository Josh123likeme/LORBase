package me.Josh123likeme.LORBase.EntityHolder;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import me.Josh123likeme.LORBase.Main;
import me.Josh123likeme.LORBase.ResourceLoader;
import me.Josh123likeme.LORBase.BlockHolder.Wall;
import me.Josh123likeme.LORBase.Types.*;

public abstract class EntityBase {
	
	public final Entity type;	
	private final double size;
	
	private Vector2D pos;
	private double baseMovementSpeed; // m/s
	private double movementSpeed;
	private Direction facing;
	
	public EntityBase(Vector2D initPos, Entity type, double baseMovementSpeed, double size, Direction facing){
		
		pos = initPos;
		this.type = type;
		this.baseMovementSpeed = baseMovementSpeed;
		this.size = size;
		this.facing = facing;
		
	}
	
	public Vector2D getPosition() {
		
		return pos;
		
	}
	
	public double getMovementSpeed() {
		
		return baseMovementSpeed;
		
	}
	
	public Direction getFacing() {
		
		return facing;
		
	}
	
	public void setFacing(Direction facing) {
		
		this.facing = facing;
		
	}
	
	public void moveEntity(Vector2D pos) {
		
		double x = pos.X;
		double y = pos.Y;
		
		if (this instanceof ICollidable) {
			
			if (Main.game.world.wall[(int) y][(int) x] != null || Main.game.world.wall[(int) (y + size)][(int) (x + size)] != null) {
				
				//move as far as possible
				
			}
			else {
				
				this.pos = pos;
				
			}
			
		}
		else {
			
			this.pos = pos;
			
		}
		
	}

}
