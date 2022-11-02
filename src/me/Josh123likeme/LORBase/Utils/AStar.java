package me.Josh123likeme.LORBase.Utils;

import java.util.ArrayList;
import java.util.List;

import me.Josh123likeme.LORBase.GameHolder.World;
import me.Josh123likeme.LORBase.Types.Vector2D;

public class AStar {

	public static Vector2D[] doAStar(World world, Vector2D start, Vector2D end) {
		
		List<Vector2D> path = new ArrayList<Vector2D>();
		
		int offsetX = (int) (start.X < end.X ? start.X : end.X);
		int offsetY = (int) (start.Y < end.Y ? start.Y : end.Y);
		
		Tile[][] board = new Tile[(int) (start.Y - end.Y < 0 ? end.Y - start.Y : start.Y - end.Y)][(int) (start.X - end.X < 0 ? end.X - start.X : start.X - end.X)];
		
		
		
		return (Vector2D[]) path.toArray();
		
	}
	
	public class Tile {
		
		private double g;
		private double h;
		
		public double getG() {
			
			return g;
			
		}
		
		public double getH() {
			
			return h;
			
		}
		
		public double getF() {
			
			return g + h;
			
		}
		
	}
	
}
