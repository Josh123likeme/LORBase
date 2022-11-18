package me.Josh123likeme.LORBase.EntityHolder;

import java.awt.image.BufferedImage;

import me.Josh123likeme.LORBase.GameHolder.World;
import me.Josh123likeme.LORBase.ItemHolder.Item;
import me.Josh123likeme.LORBase.Types.Vector2D;

public class ITEM_ENTITY extends Entity {

	private Item item;
	private long droppedTime;
	private final double pickupCooldown;
	
	public ITEM_ENTITY(World world, Vector2D initPos, double facing, Item item, double pickupCooldown) {
		super(world, initPos, facing);
		
		this.item = item;
		this.droppedTime = System.nanoTime();
		this.pickupCooldown = pickupCooldown;
		
	}
	
	public BufferedImage getTexture() {
		
		return item.getTexture();
		
	}
	
	public Item getItem() {
		
		return item;
		
	}
	
	public boolean canPickup() {
		
		return (System.nanoTime() - droppedTime > pickupCooldown * 1000000000) ? true : false;
		
	}

	@Override
	public double getSize() {
		
		return 0.5d;
	}
	
}
