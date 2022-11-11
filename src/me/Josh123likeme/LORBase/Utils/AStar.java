package me.Josh123likeme.LORBase.Utils;

import java.util.*;

import me.Josh123likeme.LORBase.BlockHolder.Wall;
import me.Josh123likeme.LORBase.EntityHolder.Entity;
import me.Josh123likeme.LORBase.GameHolder.World;
import me.Josh123likeme.LORBase.ParticleHolder.DAMAGE_NUMBER;
import me.Josh123likeme.LORBase.Types.Vector2D;

public class AStar {

	public static Vector2D[] doAStar(World world, Vector2D start, Vector2D end) {
		
		Vector2DInt startInt = new Vector2DInt((int) start.X, (int) start.Y);
		Vector2DInt endInt = new Vector2DInt((int) end.X, (int) end.Y);
		
		//creating the board
		Board board = new Board(world.getWidth(), world.getHeight());
		
		for (int y = 0; y < board.getHeight(); y++) {
			
			for (int x = 0; x < board.getWidth(); x++) {
				
				if (world.getWall(x, y) != Wall.AIR) board.blockBlockAt(x, y);
				
			}
			
		}
		
		//starting algorithm
		
		boolean[][] cleared = new boolean[board.getHeight()][board.getWidth()];
		boolean[][] tried = new boolean[board.getHeight()][board.getWidth()];
		
		double[][] gCosts = new double[board.getHeight()][board.getWidth()];
		
		Vector2DInt[][] parents = new Vector2DInt[board.getHeight()][board.getWidth()];
		
		tried[startInt.Y][startInt.X] = true;
		
		Vector2DInt pick = startInt.clone();
		
		do {
			
			//expand
			for (int y = -1; y <= 1; y++) {
				
				for (int x = -1; x <= 1; x++) {
					
					if (x == 0 && y == 0) continue;
					
					Vector2DInt attempt = new Vector2DInt(pick.X + x, pick.Y + y);
					
					if (attempt.X < 0 || attempt.X >= board.getWidth() || attempt.Y < 0 || attempt.Y >= board.getHeight()) continue;
						
					if (board.getBlockAt(attempt.X, attempt.Y)) continue;
							
					if (tried[attempt.Y][attempt.X]) continue;
					
					if (parents[attempt.Y][attempt.X] == null) {
						
						cleared[attempt.Y][attempt.X] = true;
						parents[attempt.Y][attempt.X] = pick.clone();
						
						if (x == 0 || y == 0) gCosts[attempt.Y][attempt.X] = gCosts[pick.Y][pick.X] + 1;
						else gCosts[attempt.Y][attempt.X] = gCosts[pick.Y][pick.X] + Math.sqrt(2);
						
					}
					
					if (gCosts[pick.Y][pick.X] < gCosts[parents[attempt.Y][attempt.X].Y][parents[attempt.Y][attempt.X].X]) {
						
						parents[attempt.Y][attempt.X] = pick.clone();
						
						if (x == 0 || y == 0) gCosts[attempt.Y][attempt.X] = gCosts[pick.Y][pick.X] + 1;
						else gCosts[attempt.Y][attempt.X] = gCosts[pick.Y][pick.X] + Math.sqrt(2);
						
					}	
					
				}
				
			}

			//pick best
			List<Vector2DInt> bestFCosts = new ArrayList<Vector2DInt>();
			double bestFCost = Double.MAX_VALUE;
			
			for (int y = 0; y < board.getHeight(); y++) {
				
				for (int x = 0; x < board.getWidth(); x++) {

					if (!cleared[y][x]) continue;
					if (tried[y][x]) continue;
					if (startInt.X == x && startInt.Y == y) continue;
					
					Vector2DInt current = new Vector2DInt(x, y);
					
					double fCost = gCosts[current.Y][current.X] + current.distanceTo(endInt);

					if (fCost < bestFCost) {
						
						bestFCosts.clear();
						
						bestFCosts.add(current);
						bestFCost = fCost;
						
					}
					else if (fCost <= bestFCost) {
						
						bestFCosts.add(current);
						
					}
					
				}
				
			}
			
			List<Vector2DInt> bestHCosts = new ArrayList<Vector2DInt>();
			double bestHCost = Double.MAX_VALUE;
			
			for (Vector2DInt current : bestFCosts) {
				
				double hCost = current.distanceTo(endInt);
				
				if (hCost < bestHCost) {
					
					bestHCosts.clear();
					
					bestHCosts.add(current);
					bestHCost = hCost;
					
				}
				else if (hCost <= bestHCost) {
					
					bestHCosts.add(current);
					
				}
				
			}
			
			if (endInt.X == pick.X && endInt.Y == pick.Y) break;
			
			pick = bestHCosts.get(0);
			tried[pick.Y][pick.X] = true;
			
		} while (pick.X != endInt.X || pick.Y != endInt.Y);
		
		//backtracking path
		List<Vector2DInt> reversePath = new ArrayList<Vector2DInt>();
		
		reversePath.add(endInt);
		
		Vector2DInt parent = pick;
		
		do {
			
			reversePath.add(parent);
			
			parent = parents[parent.Y][parent.X];
			
		} while (parent != null);
		
		//flip path to correct direction and convert to double
		Vector2D[] flipped = new Vector2D[reversePath.size()];
		
		for (int i = 0; i < reversePath.size(); i++) {
			
			Vector2D vector = new Vector2D(reversePath.get(i).X, reversePath.get(i).Y);
			
			flipped[flipped.length - i - 1] = vector;
			
		}
		
		List<Vector2D> cornersAdded = new ArrayList<Vector2D>();
	
		cornersAdded.add(flipped[0]);
		
		//corner re-evaluation
		for (int i = 1; i < flipped.length; i++) {
			
			Vector2D prev = flipped[i - 1];
			Vector2D curr = flipped[i];
			
			if (prev.X != curr.X && prev.Y != curr.Y) {
				
				if (board.getBlockAt((int) prev.X, (int) curr.Y))  {
					
					cornersAdded.add(new Vector2D(curr.X, prev.Y));

				}
				if (board.getBlockAt((int) curr.X, (int) prev.Y))  {
					
					cornersAdded.add(new Vector2D(prev.X, curr.Y));
					
				}
				
			}
			
			cornersAdded.add(curr);
			
		}
		
		//convert to array
		Vector2D[] asArray = new Vector2D[cornersAdded.size()];
		
		for (int i = 0; i < asArray.length; i++) {
			
			asArray[i] = cornersAdded.get(i);
			
		}
		
		//offset points
		for (int i = 0; i < asArray.length; i++) {
			
			Vector2D vector = asArray[i];
			
			vector.X += 0.1;
			vector.Y += 0.1;
			
			asArray[i] = vector;
			
		}
		
		return asArray;
		
	}
	
}
