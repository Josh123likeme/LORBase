package me.Josh123likeme.LORBase.EntityHolder;

import me.Josh123likeme.LORBase.Types.Cardinal;
import me.Josh123likeme.LORBase.Types.Vector2D;

public class Player extends EntityBase implements ICollidable {
	
	public Player(Vector2D initPos, Cardinal facing) {
		super(initPos, Entity.PLAYER, 5, 0.8, facing);
		
	}
	
}
