package me.Josh123likeme.LORBase.Generators;

import me.Josh123likeme.LORBase.BlockHolder.*;
import me.Josh123likeme.LORBase.GameHolder.Chunk;

public class EmptyMaze {

	public Chunk[][] generateMaze() {
		
		Floor[][] floor = new Floor[128][128];
		Wall[][] wall = new Wall[128][128];
		
		for (int y = 0; y < floor.length; y++) {
			
			for (int x = 0; x < floor[0].length; x++) {
				
				floor[y][x] = Floor.LABYRINTH_FLOOR;
				wall[y][x] = Wall.AIR;
				
			}
				
		}
		
		for (int y = 20; y < 100; y++) {
			
			for (int x = 20; x < 100; x++) {
				
				wall[y][x] = Wall.LABYRINTH_WALL;
				
			}
			
		}
		
		return Chunk.chunkify(floor, wall);
		
	}
	
}
