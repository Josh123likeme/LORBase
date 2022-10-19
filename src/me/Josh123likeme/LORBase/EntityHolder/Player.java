package me.Josh123likeme.LORBase.EntityHolder;

import java.awt.Graphics;

import me.Josh123likeme.LORBase.ItemHolder.Inventory;
import me.Josh123likeme.LORBase.Types.Vector2D;

public class Player extends Entity implements ICollidable, IMoveable, IHasInventory {
	
	Inventory inventory = new Inventory(8, 4);
	
	public Player(Vector2D initPos, double facing) {
		super(initPos, facing, 0.8d);
		
	}

	@Override
	public double getMovementSpeed() {
		
		return 5d;
		
	}

	@Override
	public Inventory getInventory() {
		
		return inventory;
		
	}

	@Override
	public void renderInventory(Graphics g) {
		
		inventory.render(g);
		
	}
	
}
