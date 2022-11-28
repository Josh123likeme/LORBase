package me.Josh123likeme.LORBase.Generators;

import java.util.Random;

import me.Josh123likeme.LORBase.BlockHolder.Floor;
import me.Josh123likeme.LORBase.BlockHolder.Wall;
import me.Josh123likeme.LORBase.GameHolder.Chunk;
import me.Josh123likeme.LORBase.Types.Vector2D;
import me.Josh123likeme.LORBase.Utils.Prims;

public class CaveMaze {
	
	private static Random random = new Random();
	
	private int width;
	private int height;
	private int numberOfCaverns;
	
	private Floor[][] floor;
	private Wall[][] wall;
	
	public Chunk[][] generateMaze(int difficulty) {
		
		if (difficulty <= 0) throw new IllegalArgumentException("The difficulty must be larger than zero");
		
		if (difficulty > 50) throw new IllegalArgumentException("The difficulty cannot exceed 50");
		
		//parameter functions
		width = 80 + 16 * difficulty;
		height = 80 + 16 * difficulty;
		numberOfCaverns = (width * height) / 500;
		
		//init
		floor = new Floor[height][width];
		wall = new Wall[height][width];
		
		for (int y = 0; y < height; y++) {
			
			for (int x = 0; x < width; x++) {
				
				floor[y][x] = Floor.LABYRINTH_FLOOR;
				wall[y][x] = Wall.LABYRINTH_WALL;
				
			}
			
		}
		
		//random points
		Vector2D[] points = new Vector2D[numberOfCaverns];
		
		for (int i = 0; i < points.length; i++) {
			
			points[i] = new Vector2D();
			
			points[i].X = random.nextInt(width - 20) + 10;
			points[i].Y = random.nextInt(height - 20) + 10;
			
			floor[(int) points[i].Y][(int) points[i].X] = Floor.STATUE_PODIUM;
			
		}
		
		//cavern formation
		for (int i = 0; i < points.length; i++) {
			
			generateCavern((int) points[i].X, (int) points[i].Y);
			
		}
		
		//tunnel formation
		
		Vector2D[][] tunnels = Prims.doPrims(points);
		
		for (Vector2D[] pair : tunnels) {
			
			generateTunnel((int) pair[0].X, (int) pair[0].Y, (int) pair[1].X, (int) pair[1].Y);
			
		}
		
		//border assembly
		for (int i = 0; i < wall[0].length; i++) {
			
			wall[0][i] = Wall.LABYRINTH_WALL;
			
		}
		
		for (int i = 1; i < wall.length - 1; i++) {
			
			wall[i][0] = Wall.LABYRINTH_WALL;
			wall[i][wall.length - 1] = Wall.LABYRINTH_WALL;
			
		}
		
		for (int i = 0; i < wall[wall.length - 1].length; i++) {
			
			wall[wall.length - 1][i] = Wall.LABYRINTH_WALL;
			
		}
		
		//finalisation
		Chunk[][] maze = Chunk.chunkify(floor, wall);
		
		return maze;
		
	}
	
	private void generateCavern(int x, int y) {
		
		recurGenerateCavern(x, y, 1d);
		
	}
	
	private void recurGenerateCavern(int x, int y, double clearProbability) {
		
		if (x < 0 || x >= width || y < 0 || y >= height) return;
		
		generateSquare(x, y);
		
		if (random.nextDouble() <= clearProbability) recurGenerateCavern(x + 1, y, clearProbability - 0.1);
		if (random.nextDouble() <= clearProbability) recurGenerateCavern(x, y + 1, clearProbability - 0.1);
		if (random.nextDouble() <= clearProbability) recurGenerateCavern(x - 1, y, clearProbability - 0.1);
		if (random.nextDouble() <= clearProbability) recurGenerateCavern(x, y - 1, clearProbability - 0.1);
			
	}
	
	private void generateTunnel(int x1, int y1, int x2, int y2) {
		
		double slope = (y1 - y2) / ((x1 - x2) + 0.01);
		
		double offset = slope * -x1 + y1;

		if (x1 > x2) {
			
			x1 -= x2;
			x2 += x1;
			x1 = x2 - x1;
			
		}
		
		for (double x = x1; x <= x2; x += 0.01) {

			generateSquare((int) x, (int) (slope * x + offset));
			
			wall[(int) (slope * x + offset)][(int) x] = Wall.AIR;
			
		}
		
	}
	
	private void generateSquare(int x, int y) {
		
		for (int row = y - 1; row <= y + 1; row++) {
			
			for (int column = x - 1; column <= x + 1; column++) {
				
				if (column < 0 || column >= width || row < 0 || row >= height) continue;
				
				wall[row][column] = Wall.AIR;
				
			}
			
		}
		
	}
	
}
