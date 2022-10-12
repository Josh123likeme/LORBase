package me.Josh123likeme.LORBase.BlockHolder;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import me.Josh123likeme.LORBase.SettingsHolder;

public class Chunk {
	
	private static final int chunkSize = 16;
	
	private Floor[][] floor = new Floor[chunkSize][chunkSize];
	private Wall[][] wall = new Wall[chunkSize][chunkSize];
	
	private BufferedImage chunkTexture;
	
	private boolean chunkModified;
	
	
	public Floor getFloor(int x, int y) {
		
		return floor[y][x];
		
	}
	
	public Wall getWall(int x, int y) {
		
		return wall[y][x];
		
	}
	
	public void setFloor(int x, int y, Floor floor) {
		
		if (this.floor[y][x] == floor) return;
		
		this.floor[y][x] = floor;
		
		chunkModified = true;
		
	}
	
	public void setWall(int x, int y, Wall wall) {
		
		if (this.wall[y][x] == wall) return;
		
		this.wall[y][x] = wall;
		
		chunkModified = true;
		
	}
	
	public void renderChunk(int x, int y, int size, Graphics g) {
		
		if (chunkModified) {
			
			stitchTexture();
			
			chunkModified = false;
			
		}
		
		g.drawImage(chunkTexture, x, y, size, size, null);
		
	}

	private void stitchTexture() {
		
		int defaultBlockTextureSize = SettingsHolder.blockPixelSize;
		
		chunkTexture = new BufferedImage(chunkSize * defaultBlockTextureSize, chunkSize * defaultBlockTextureSize, BufferedImage.TYPE_INT_ARGB);
		
		for (int y = 0; y < chunkSize; y++) {
			
			for (int x = 0; x < chunkSize; x++) {

				if(floor[y][x] == null) continue;
				
				chunkTexture.getGraphics().drawImage(floor[y][x].getTexture(),
						x * defaultBlockTextureSize, y * defaultBlockTextureSize, defaultBlockTextureSize, defaultBlockTextureSize, null);
				
			}
			
		}

		for (int y = 0; y < chunkSize; y++) {
			
			for (int x = 0; x < chunkSize; x++) {

				if(wall[y][x] == null) continue;
				
				chunkTexture.getGraphics().drawImage(wall[y][x].getTexture(),
						x * defaultBlockTextureSize, y * defaultBlockTextureSize, defaultBlockTextureSize, defaultBlockTextureSize, null);
				
			}
			
		}
		
	}
	
	public static Chunk[][] chunkify(Floor[][] floor, Wall[][] wall) {
		
		Chunk[][] chunks = new Chunk[(int) Math.ceil((double) floor.length / chunkSize)][(int) Math.ceil((double) floor[0].length / chunkSize)];
		
		for (int yOffset = 0; yOffset < chunks.length; yOffset++) {
			
			for (int xOffset = 0; xOffset < chunks[0].length; xOffset++) {
				
				Chunk chunk = new Chunk();
				
				for (int y = 0; y < chunkSize; y++) {
					
					for (int x = 0; x < chunkSize; x++) {
						
						try {
							
							chunk.setFloor(x, y, floor[yOffset * chunkSize + y][xOffset * chunkSize + x]);
							chunk.setWall(x, y, wall[yOffset * chunkSize + y][xOffset * chunkSize + x]);
							
						} catch(ArrayIndexOutOfBoundsException e) { }
						
						
						
					}
					
				}
				
				chunks[yOffset][xOffset] = chunk;
				
				chunk.stitchTexture();
				
			}
			
		}
		
		return chunks;
		
	}
	
	public static int getChunkSize() {
		
		return chunkSize;
		
	}
	
}
