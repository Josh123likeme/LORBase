package me.Josh123likeme.LORBase.ItemHolder;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.Josh123likeme.LORBase.FrameData;
import me.Josh123likeme.LORBase.Main;
import me.Josh123likeme.LORBase.EntityHolder.Entity;
import me.Josh123likeme.LORBase.EntityHolder.ITEM_ENTITY;
import me.Josh123likeme.LORBase.InputListener.MouseWitness;
import me.Josh123likeme.LORBase.UI.Text;
import me.Josh123likeme.LORBase.UI.UIParams;

public class Inventory {

	public final int width;
	public final int height;
	
	private Item[] items;
	private Item held;
	
	private double UISize = 0.5d;
	
	public Inventory(int width, int height) {
		
		this.width = width;
		this.height = height;
		
		items = new Item[width * height];
		
	}
	
	public Inventory(int width, int height, Item[] items) {
		
		this.width = width;
		this.height = height;
		
		this.items = new Item[width * height];
		
		if (width * height > items.length) throw new IndexOutOfBoundsException("These items will not fit within the inventory");
		
		for (int i = 0; i < items.length; i++) {
			
			this.items[i] = items[i];
			
		}
		
	}
	
	public void render(Graphics g) {
		
		FrameData frameData = Main.game.frameData;
		MouseWitness mouseWitness = Main.game.mouseWitness;
		int cellSize = frameData.Width / width < frameData.Height / height ? frameData.Width / width : frameData.Height / height;
		
		cellSize *= UISize;
		
		int offsetX = frameData.Width / 2 - cellSize * width / 2;
		int offsetY = frameData.Height / 2 - cellSize * height / 2;
		
		g.setColor(new Color(146, 146, 146));
		
	    g.fillRect(offsetX, offsetY,
	    		(int) (width * cellSize + cellSize * 0.1),
	    		(int) (height * cellSize + cellSize * 0.1));
		
		g.setColor(new Color(162, 162, 162));
		g.setColor(new Color(200, 200, 200));
		
		for (int y = 0; y < height; y++) {
			
			for (int x = 0; x < width; x++) {
				
				g.fillRect((int) (offsetX + x * cellSize + cellSize * 0.1),
						(int) (offsetY + y * cellSize + cellSize * 0.1),
						(int) (cellSize * 0.9), (int) (cellSize * 0.9));
				
				int index = y * width + x;
				
				if (items[index] != null) {
					
					g.drawImage(items[index].getTexture(), 
							(int) (offsetX + x * cellSize + cellSize * 0.1),
							(int) (offsetY + y * cellSize + cellSize * 0.1),
							(int) (cellSize * 0.9), (int) (cellSize * 0.9), null);
					
				}
				
			}
			
		}
		
		
		
		if (held != null) {
			
			g.drawImage(held.getTexture(), 
					mouseWitness.getMouseX() - (int) (cellSize * 0.45), 
					mouseWitness.getMouseY() - (int) (cellSize * 0.45),
					(int) (cellSize * 0.9), (int) (cellSize * 0.9), null);
			
		}
		
		int slot = getSlot(mouseWitness.getMouseX(), mouseWitness.getMouseY());
		
		if (held == null && slot != -1 && items[slot] != null) {
			
			int x = mouseWitness.getMouseX();
			int y = mouseWitness.getMouseY();
			
			int relWidth = (int) (frameData.Width * 0.2);
			
			int textHeight = 20;

			int numberOfLines = Text.predictLines(g, items[slot].getDescription(), 
					x + 10 + (int) (relWidth * 0.05) + 10, y + 10 + (int) (relWidth * 0.05) + 10, 
					relWidth - (int) (relWidth * 0.1) - 20, textHeight);
			
			g.setColor(new Color(21, 25, 71));
			
			g.fillRect(x + 10, y + 10, relWidth, textHeight * (numberOfLines + 4) + 40);
			
			g.setColor(new Color(58, 65, 165));
			
			g.fillRect(x + 10 + (int) (relWidth * 0.05), y + 10 + (int) (relWidth * 0.05), 
					relWidth - (int) (relWidth * 0.1), (textHeight * (numberOfLines + 4)) - (int) (relWidth * 0.1) + 40);
			
			g.setColor(new Color(255, 255, 255));
			
			g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, 40)); 
			
			g.drawString(items[slot].getDisplayName(), 
					x + 10 + (int) (relWidth * 0.05) + 10, y + 10 + (int) (relWidth * 0.05) + 40);
			
			Text.writeLines(g, items[slot].getDescription(), 
					x + 10 + (int) (relWidth * 0.05) + 10, y + 10 + (int) (relWidth * 0.05) + 10 + 40, 
					relWidth - (int) (relWidth * 0.1) - 20, textHeight);
			
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
				
				if (Main.game.save.inWorld() && held != null) {
					
					Entity droppedItem = new ITEM_ENTITY(Main.game.save.world, 
							Main.game.save.player.getPosition(), 90, held, 3d);
					
					held = null;
					
				}
	
			}
			
		}
		
	}
	
	private int getSlot(int x, int y) {
		
		FrameData frameData = Main.game.frameData;
		
		int cellSize = frameData.Width / width < frameData.Height / height ? frameData.Width / width : frameData.Height / height;
		
		cellSize *= UISize;
		
		int offsetX = frameData.Width / 2 - cellSize * width / 2;
		int offsetY = frameData.Height / 2 - cellSize * height / 2;
		
		if (((x - offsetX) < cellSize * width && x - offsetX >= 0) &&
			((y - offsetY) < cellSize * height && y - offsetY >= 0)) {
			
			int cellX = (x - offsetX) / cellSize;
			int cellY = (y - offsetY) / cellSize;
			
			return cellY * width + cellX;
			
		}
		
		return -1;
		
	}
	
	public void closeInventory() {
		
		if (held != null) {
			
			addItem(held);
			
			held = null;
			
		}
		
	}
	
	public Item[] getItems() {
		
		return items;
		
	}
	
	public Item getHeld() {
		
		return held;
		
	}
	
	public void setHeld(Item held) {
		
		this.held = held;
		
	}
	
	public void addItem(Item item) throws IndexOutOfBoundsException {
		
		for (int i = width * (height - 1); i < width * height; i++) {
			
			if (items[i] == null) {
				
				items[i] = item;
				
				return;
				
			}
			
		}
		
		for (int i = 0; i < width * (height - 1); i++) {
			
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
	
	public void updateParameters(UIParams parameters) {
		
		UISize = parameters.getParameter("UISIZE");
		
	}
	
}
