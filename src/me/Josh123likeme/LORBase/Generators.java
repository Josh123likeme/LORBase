package me.Josh123likeme.LORBase;

import java.util.Random;

import me.Josh123likeme.LORBase.Types.*;

public abstract class Generators {

	private static Random random = new Random();
	
	private static Vector2D pos;
	private static Boolean[][] maze;
	
	public static Boolean[][] generateRandomMaze(int width, int height) {
		
		maze = new Boolean[height][width];
		
		for (int i = 0; i < maze[0].length; i++) {
			
			maze[0][i] = true;
			
		}
		
		for (int y = 1; y < maze.length - 1; y++) {
			
			maze[y][0] = true;
			
			for (int x = 1; x < maze[0].length - 1; x++) {
				
				maze[y][x] = false;
				
			}
			
			maze[y][maze[0].length - 1] = true;
			
		}
		
		for (int i = 0; i < maze[0].length; i++) {
			
			maze[maze.length - 1][i] = true;
			
		}
		
		goForward(new Vector2D(2, (int) (2 * (height / 4))), new Vector2D(1,0));
		
		return maze;
		
	}
	
	private static void goForward(Vector2D pos, Vector2D dir) {
		
		/*
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		int maxDistance = maxDistance(pos, dir);
		
		if (maxDistance <= 0) return;
		
		int distance = 2 * random.nextInt(10);
		
		if (distance > maxDistance) distance = maxDistance;
			
		for (int i = 0; i < distance; i++) {
			
			pos.X += dir.X;
			pos.Y += dir.Y;
				
			maze[(int) pos.Y][(int) pos.X] = true;
			
		}
		
		int[] order = new int[] {0,1,2,3};
		
		for (int i = 0; i < 10; i++) {
			
			int one = random.nextInt(4);
			int two = random.nextInt(4);
			
			int temp = order[one];			
			order[one] = order[two];			
			order[two] = temp;
			
		}
		
		for (int i = 0; i < order.length; i++) {
			
			switch (order[i]) {
			
			case 0:
				
				goForward(new Vector2D(pos.X, pos.Y), new Vector2D(1,0));
				
				break;
				
			case 1:
				
				goForward(new Vector2D(pos.X, pos.Y), new Vector2D(-1,0));
				
				break;
				
			case 2:
				
				goForward(new Vector2D(pos.X, pos.Y), new Vector2D(0,-1));
				
				break;
				
			case 3:
				
				goForward(new Vector2D(pos.X, pos.Y), new Vector2D(0,1));
				
				break;
			
			}
			
		}
		
	}
	
	private static int maxDistance(Vector2D pos, Vector2D dir) {
		
		int x = (int) pos.X;
		int y = (int) pos.Y;
		
		int distance = 0;
		
		do  {	
			
			x += 2 * dir.X;
			y += 2 * dir.Y;
			
			distance += 2;

		}
		while (!maze[y][x]);
		
		return distance - 2;
		
	}
	
}
