package me.Josh123likeme.LORBase.EntityHolder;

import me.Josh123likeme.LORBase.Types.Vector2D;

public class Player extends Entity implements ICollidable, IMoveable {
	
	public Player(Vector2D initPos, double facing) {
		super(initPos, facing, 0.8d);
		
	}

	@Override
	public double getMovementSpeed() {
		
		return 5d;
		
	}
	
}
