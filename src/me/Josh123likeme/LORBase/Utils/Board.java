package me.Josh123likeme.LORBase.Utils;

public class Board {

	private boolean[][] blocks;
	
	public Board(int width, int height) {
		
		blocks = new boolean[height][width];
		
	}
	
	public boolean getBlockAt(int x, int y) {
		
		return blocks[y][x];
		
	}
	
	public void blockBlockAt(int x, int y) {
		
		blocks[y][x] = true;
		
	}
	
	public int getWidth() {
		
		return blocks[0].length;
		
	}
	
	public int getHeight() {
		
		return blocks.length;
		
	}
	
}

