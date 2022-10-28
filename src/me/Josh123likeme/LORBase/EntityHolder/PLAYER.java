package me.Josh123likeme.LORBase.EntityHolder;

import me.Josh123likeme.LORBase.GameHolder.World;
import me.Josh123likeme.LORBase.ItemHolder.Inventory;
import me.Josh123likeme.LORBase.Types.Vector2D;

public class PLAYER extends Entity implements ICollidable, IMoveable, IHasInventory, IHealthy {
	
	private double health;
	private double maxHealth;
	
	private Inventory inventory;
	
	public PLAYER(World world, Vector2D initPos, double facing) {
		super(world, initPos, facing);
		
		health = 100d;
		maxHealth = 100d;
		
		inventory = new Inventory(8, 4);
		
	}
	
	public void updateWorld(World world) {
		
		this.world = world;
		
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
	public double getHealth() {
		
		return health;
	}

	@Override
	public double getMaxHealth() {
		
		return maxHealth;
	}
	
	@Override
	public void updateStats() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void damage(double damage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void kill() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getSize() {
		
		return 0.8d;
	}
	
}
