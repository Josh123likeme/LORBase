package me.Josh123likeme.LORBase.Types;

public class Vector2D {
	
	public double X, Y;
	
	public Vector2D(double x, double y) {
		
		X = x;
		Y = y;
		
	}
	
	public void normalise() {
		
		double mod = Math.sqrt(X*X + Y*Y);
		
		if (mod == 0) return;
		
		X = X / mod;
		Y = Y / mod;
		
	}

}
