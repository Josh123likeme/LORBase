package me.Josh123likeme.LORBase.UI;

import java.awt.Color;
import java.awt.Graphics;

import me.Josh123likeme.LORBase.FrameData;
import me.Josh123likeme.LORBase.Main;
import me.Josh123likeme.LORBase.EntityHolder.*;
import me.Josh123likeme.LORBase.InputListener.MouseWitness;
import me.Josh123likeme.LORBase.ItemHolder.Item;

public class Inventory {

	public final int Width;
	public final int Height;
	
	private Item[] items;
	private Item held;
	
	public Inventory(int width, int height) {
		
		Width = width;
		Height = height;
		
		items = new Item[width * height];
		
	}
	
	public Inventory(int width, int height, Item[] items) {
		
		Width = width;
		Height = height;
		
		this.items = new Item[width * height];
		
		if (width * height > items.length) throw new IndexOutOfBoundsException("These items will not fit within the inventory");
		
		for (int i = 0; i < items.length; i++) {
			
			this.items[i] = items[i];
			
		}
		
	}
	
	public void render(Graphics g) {
		
		FrameData frameData = Main.game.frameData;
		
		int cellSize = frameData.Width / Width < frameData.Height / Height ? frameData.Width / Width : frameData.Height / Height;
		
		cellSize *= 0.6;
		
		int offsetX = frameData.Width / 2 - cellSize * Width / 2;
		int offsetY = frameData.Height / 2 - cellSize * Height / 2;
		
		g.setColor(new Color(146, 146, 146));
		
	    g.fillRect(offsetX, offsetY,
	    		(int) (Width * cellSize + cellSize * 0.1),
	    		(int) (Height * cellSize + cellSize * 0.1));
		
		g.setColor(new Color(162, 162, 162));
		g.setColor(new Color(200, 200, 200));
		
		for (int y = 0; y < Height; y++) {
			
			for (int x = 0; x < Width; x++) {
				
				g.fillRect((int) (offsetX + x * cellSize + cellSize * 0.1),
						(int) (offsetY + y * cellSize + cellSize * 0.1),
						(int) (cellSize * 0.9), (int) (cellSize * 0.9));
				
				int index = y * Width + x;
				
				if (items[index] != null) {
					
					g.drawImage(items[index].getTexture(), 
							(int) (offsetX + x * cellSize + cellSize * 0.1),
							(int) (offsetY + y * cellSize + cellSize * 0.1),
							(int) (cellSize * 0.9), (int) (cellSize * 0.9), null);
					
				}
				
			}
			
		}
		
		
		
		if (held != null) {
			
			MouseWitness mouseWitness = Main.game.mouseWitness;
			
			g.drawImage(held.getTexture(), 
					mouseWitness.getMouseX() - (int) (cellSize * 0.45), 
					mouseWitness.getMouseY() - (int) (cellSize * 0.45),
					(int) (cellSize * 0.9), (int) (cellSize * 0.9), null);
			
		}
		
	}
	
	public void updateInventoryUI() {
		
		MouseWitness mouseWitness = Main.game.mouseWitness;
		
		if (mouseWitness.isLeftClicked()) {
			
			int slot = getSlot(mouseWitness.getMouseX(), mouseWitness.getMouseY());
			
			if (slot != -1) {
				
				if (held == null) {
					
					held = items[slot];
					items[slot] = null;
					
				}
				else if (held != null && items[slot] == null) {
					
					items[slot] = held;
					held = null;
					
				}
				else if (held != null && items[slot] != null) {
					
					Item temp = held;
					held = items[slot];
					items[slot] = temp;
					
				}
				
			}
			else if (slot == -1) {
				
				if (Main.game.save.inWorld()) {
					
					Entity droppedItem = new ITEM_ENTITY(Main.game.save.player.getPosition(), 90, held, 5d);
					
					held = null;
					
					Main.game.save.world.addEntity(droppedItem);
					
				}
	
			}
			
		}
		
	}
	
	private int getSlot(int x, int y) {
		
		FrameData frameData = Main.game.frameData;
		
		int cellSize = frameData.Width / Width < frameData.Height / Height ? frameData.Width / Width : frameData.Height / Height;
		
		cellSize *= 0.6;
		
		int offsetX = frameData.Width / 2 - cellSize * Width / 2;
		int offsetY = frameData.Height / 2 - cellSize * Height / 2;
		
		if (((x - offsetX) < cellSize * Width && x - offsetX >= 0) &&
			((y - offsetY) < cellSize * Height && y - offsetY >= 0)) {
			
			int cellX = (x - offsetX) / cellSize;
			int cellY = (y - offsetY) / cellSize;
			
			return cellY * Width + cellX;
			
		}
		
		return -1;
		
	}
	
	public void addItem(Item item) throws IndexOutOfBoundsException {
		
		for (int i = Width * (Height - 1); i < Width * Height; i++) {
			
			if (items[i] == null) {
				
				items[i] = item;
				
				return;
				
			}
			
		}
		
		for (int i = 0; i < Width * (Height - 1); i++) {
			
			if (items[i] == null) {
				
				items[i] = item;
				
				return;
				
			}
			
		}
		
		throw new IndexOutOfBoundsException("there is no space for the item in the inventory");
		
	}
	
	public void addItem(Item item, int index) throws IndexOutOfBoundsException {
		
		if (items[index] != null) throw new IndexOutOfBoundsException("there is already an item there");
		
		items[index] = item;
		
	}
	
	public boolean isFull() {
		
		for (int i = 0; i < items.length; i++) {
			
			if (items[i] == null) return false;
			
		}
		
		return true;
		
	}
	
}
