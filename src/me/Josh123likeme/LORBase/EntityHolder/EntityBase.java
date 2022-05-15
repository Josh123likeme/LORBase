package me.Josh123likeme.LORBase.EntityHolder;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import me.Josh123likeme.LORBase.ResourceLoader;
import me.Josh123likeme.LORBase.BlockHolder.Wall;
import me.Josh123likeme.LORBase.Types.*;

public abstract class EntityBase {
	
	public final Entity type;
	private Vector2D pos;
	
	private double baseMovementSpeed; // m/s
	private double movementSpeed;
	
	public EntityBase(Vector2D initPos, Entity type, double baseMovementSpeed){
		
		pos = initPos;
		
		this.type = type;
		this.baseMovementSpeed = baseMovementSpeed;
		
	}
	
	public Vector2D getPosition() {
		
		return pos;
		
	}
	
	public double getMovementSpeed() {
		
		return baseMovementSpeed;
		
	}
	
	public void moveEntity(Vector2D pos) {
		
		if (this instanceof ICollidable) {
			
			
			
		}
		else {
			
			this.pos = pos;
			
		}
		
	}

}
