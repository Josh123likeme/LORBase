package me.Josh123likeme.LORBase.EntityHolder;

import java.awt.Graphics;

import me.Josh123likeme.LORBase.ItemHolder.Inventory;

public interface IHasInventory {

	public Inventory getInventory();
	
	public void renderInventory(Graphics g);
	
}
