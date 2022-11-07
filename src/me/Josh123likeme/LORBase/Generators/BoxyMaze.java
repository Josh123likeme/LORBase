package me.Josh123likeme.LORBase.Generators;

import java.util.Random;

import me.Josh123likeme.LORBase.BlockHolder.Floor;
import me.Josh123likeme.LORBase.BlockHolder.Wall;
import me.Josh123likeme.LORBase.GameHolder.Chunk;

public class BoxyMaze {

	private static Random random = new Random();
	
	public Chunk[][] generateMaze() {

		Floor[][] floor = new Floor[128][128];
		Wall[][] wall = new Wall[128][128];
		
		for (int y = 0; y < floor.length; y++) {
			
			for (int x = 0; x < floor[0].length; x++) {
				
				floor[y][x] = Floor.LABYRINTH_FLOOR;
				wall[y][x] = Wall.AIR;
				
			}
				
		}
		
		for (int i = 0; i < wall[0].length * wall.length / 150; i++) {
			
			int ox = random.nextInt(wall[0].length);
			int oy = random.nextInt(wall.length);
			
			int w = random.nextInt(20);
			int h = random.nextInt(20);
			
			for (int y = 0; y < h; y++) {
				
				for (int x = 0; x < w; x++) {
					
					if (ox + x < 10 || ox + x >= wall[0].length - 10 || oy + y < 10 || oy + y >= wall.length - 10) continue;
					
					wall[oy + y][ox + x] = Wall.LABYRINTH_WALL;
					
				}
				
			}
			
		}
		
		return Chunk.chunkify(floor, wall);
		
	}
	
}
