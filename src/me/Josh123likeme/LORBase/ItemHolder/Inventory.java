package me.Josh123likeme.LORBase.ItemHolder;

import java.awt.Color;
import java.awt.Graphics;

import me.Josh123likeme.LORBase.FrameData;
import me.Josh123likeme.LORBase.Main;

public class Inventory {

	public final int Width;
	public final int Height;
	
	private Item[] items;
	
	public Inventory(int width, int height) {
		
		Width = width;
		Height = height;
		
		items = new Item[width * height - 1];
		
	}
	
	public Inventory(int width, int height, Item[] items) {
		
		Width = width;
		Height = height;
		
		if (width * height > items.length) throw new IndexOutOfBoundsException("These items will not fit within the inventory");
		
		this.items = items;
		
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
		
		for (int y = 0; y < Height; y++) {
			
			for (int x = 0; x < Width; x++) {
				
				g.fillRect((int) (offsetX + x * cellSize + cellSize * 0.1),
						(int) (offsetY + y * cellSize + cellSize * 0.1),
						(int) (cellSize * 0.9),
						(int) (cellSize * 0.9));
				
			}
			
		}
		
	}
	
}
