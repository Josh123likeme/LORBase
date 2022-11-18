package me.Josh123likeme.LORBase.Utils;

import me.Josh123likeme.LORBase.Types.Vector2D;

public class Prims {

	public static Vector2D[][] doPrims(Vector2D[] points) {
		
		double[][] matrix = new double[points.length][points.length];
		
		for (int row = 0; row < matrix.length; row++) {
			
			for (int column = 0; column < matrix[0].length; column++) {
				
				if (row != column) matrix[row][column] = points[row].distanceTo(points[column]);
				else matrix[row][column] = Double.MAX_VALUE;
				
			}
			
		}
		
		Vector2D[][] pairs = new Vector2D[matrix.length - 1][2];
		
		boolean[] availableStarts = new boolean[matrix.length];
		boolean[] blockedEnds = new boolean[matrix.length];
		
		availableStarts[0] = true;
		blockedEnds[0] = true;
		
		for (int i = 0; i < matrix.length - 1; i++) {
			
			int smallestStart = 0;
			int smallestEnd = 0;
			
			for (int start = 0; start < availableStarts.length; start++) {
				
				if (!availableStarts[start]) continue;
				
				for (int end = 0; end < blockedEnds.length; end++) {
					
					if (blockedEnds[end]) continue;
					
					if (matrix[start][end] < matrix[smallestStart][smallestEnd]) {
						
						smallestStart = start;
						smallestEnd = end;
						
					}
					
				}
				
			}
			
			availableStarts[smallestEnd] = true;
			blockedEnds[smallestEnd] = true;
			
			pairs[i][0] = points[smallestStart];
			pairs[i][1] = points[smallestEnd];
			
		}
		
		return pairs;
		
	}
	
}
