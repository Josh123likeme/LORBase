package me.Josh123likeme.LORBase.EntityHolder;

import java.awt.Graphics;

import me.Josh123likeme.LORBase.Types.Vector2D;
import me.Josh123likeme.LORBase.UI.Inventory;

public class PLAYER extends Entity implements ICollidable, IMoveable, IHasInventory {
	
	Inventory inventory;
	
	public PLAYER(Vector2D initPos, double facing) {
		super(initPos, facing);
		size = 0.8d;
		
		inventory = new Inventory(8, 4);
		
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
