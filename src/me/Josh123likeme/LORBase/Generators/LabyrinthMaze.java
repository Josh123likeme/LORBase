package me.Josh123likeme.LORBase.Generators;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import me.Josh123likeme.LORBase.BlockHolder.Floor;
import me.Josh123likeme.LORBase.BlockHolder.Wall;
import me.Josh123likeme.LORBase.Types.Vector2D;

public class LabyrinthMaze {

	final int smallRoomSize = 16;
	final int hallwayWidth = 4;
	final double chanceOfRoomPlace = 0.3d;
	
	private Floor[][] floor;
	private Wall[][] wall;
	
	private int width;
	private int height;
	
	private int[][] template;
	
	private static Random random = new Random();
	
	public void generateMaze(int width, int height) {
		
		floor = new Floor[height][width];
		wall = new Wall[height][width];
		
		this.width = width;
		this.height = height;
		
		template = new int[height][width];
		
		generate();
		
	}
	
	private void generate() {
		
		for (int y = 0; y < height; y++) {
			
			for (int x = 0; x < width; x++) {
				
				template[y][x] = 0;
				
			}
			
		}
		
		//generating room map
		
		int roomCounter = 1;
		
		for (Room room : Room.values()) {
			
			for (int y = 0; y < height; y++) {
				
				for (int x = 0; x < width; x++) {
					
					if (willRoomFit(room, x, y)) {
						
						if (random.nextDouble() > chanceOfRoomPlace && !room.equals(Room.SINGLE)) continue;
						
						for (Vector2D vec : room.getOriginPositions()) {
							
							template[(int) (vec.Y + y)][(int) (vec.X + x)] = roomCounter;
								
						}
						
						roomCounter++;
						
					}
					
				}
				
			}
			
		}
		
	}
	
	private Boolean willRoomFit(Room room, int x, int y) {
		
		for (Vector2D vec : room.getOriginPositions()) {
			
			if (vec.X + x >= width || vec.Y + y >= height) return false;
			
			if (template[(int) (vec.Y + y)][(int) (vec.X + x)] != 0) return false;
			
		}
		
		return true;
		
	}
	
	public void displayLabyrinth(Graphics g) {
		
		for (int y = 0; y < height; y++) {
			
			for (int x = 0; x < width; x++) {
				
				g.setColor(hashColor(template[y][x] - 1));
				
				g.fillRect(x * 64, y * 64, 64, 64);
				
			}
			
		}
		
	}
	
	private Color hashColor(int value) {
		
		
		//69 provided by Emily Conlon
		//196 provided by Bradley Conlon
		//56 provided by Michael Aldridge
		
		return new Color((value * 69) % 256, (value * 196) % 256, (value * 56) % 256);
		
	}
	
	private enum Room {

		BOX(new Vector2D(0,0), new Vector2D(1,0), new Vector2D(1,1), new Vector2D(0,1)),
		// OO
		// OO
		L_0(new Vector2D(0,0), new Vector2D(1,0), new Vector2D(0,1)),
		// OO
		// O
		L_1(new Vector2D(0,0), new Vector2D(1,0), new Vector2D(1,1)),
		// OO
		//  O
		L_2(new Vector2D(1,0), new Vector2D(1,1), new Vector2D(0,1)),
		//  O
		// OO
		L_3(new Vector2D(0,0), new Vector2D(0,1), new Vector2D(1,1)),
		// O
		// OO
		SUPERTALL_0(new Vector2D(0,0), new Vector2D(0,1), new Vector2D(0,2)),
		// O
		// O
		// O
		SUPERTALL_1(new Vector2D(0,0), new Vector2D(1,0), new Vector2D(2,0)),
		// OOO
		TALL_0(new Vector2D(0,0), new Vector2D(0,1)),
		// O
		// O
		TALL_1(new Vector2D(0,0), new Vector2D(1,0)),
		// OO
		SINGLE(new Vector2D(0,0)),
		// O
		
		;
		
		private final Vector2D[] originPositions;
		
		Room(Vector2D o0){
			
			 originPositions = new Vector2D[] {o0};
			
		}
		
		Room(Vector2D o0, Vector2D o1){
			
			 originPositions = new Vector2D[] {o0, o1};
			
		}
		
		Room(Vector2D o0, Vector2D o1, Vector2D o2){
			
			 originPositions = new Vector2D[] {o0, o1, o2};
			
		}
		
		Room(Vector2D o0, Vector2D o1, Vector2D o2, Vector2D o3){
			
			 originPositions = new Vector2D[] {o0, o1, o2, o3};
			
		}
		
		public Vector2D[] getOriginPositions() {
			
			return originPositions;
			
		}
		
	}
	
}
