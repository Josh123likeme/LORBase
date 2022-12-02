package me.Josh123likeme.LORBase.Utils;

import java.util.Random;

public class Vector2DInt implements Cloneable {
	
	private static Random random = new Random();
	
	public int X, Y;
	
	public Vector2DInt() { }
	
	public Vector2DInt(int x, int y) {
		
		X = x;
		Y = y;
		
	}
	
	public Vector2DInt normalise() {
		
		double mag = Math.sqrt(X*X + Y*Y);
		
		if (mag == 0) return this;
		
		X = (int) (X / mag);
		Y = (int) (Y / mag);
		
		return this;
		
	}
	
	public double distanceTo(Vector2DInt vec) {
		
		return Math.sqrt((X - vec.X) * (X - vec.X) + (Y - vec.Y) * (Y - vec.Y));
		
	}
	
	public double directionTo(Vector2DInt vec) {
		
		double dx = vec.X - X;
		double dy = vec.Y - Y;
		
		if (dx > 0 && dy > 0) return Math.toDegrees(Math.atan(dy / dx));
		if (dx < 0 && dy > 0) return 90 + Math.toDegrees(Math.atan(-dx / dy));
		if (dx < 0 && dy < 0) return 180 + Math.toDegrees(Math.atan(-dy / -dx));
		if (dx > 0 && dy < 0) return 270 + Math.toDegrees(Math.atan(dx / -dy));
		
		if (dx > 0 && dy == 0) return 0;
		if (dx == 0 && dy > 0) return 90;
		if (dx < 0 && dy == 0) return 180;
		if (dx == 0 && dy < 0) return 270;
		
		return 0d;
		
	}
	
	public Vector2DInt set(int x, int y) {
		
		X = x;
		Y = y;
		
		return this;
		
	}
	
	public Vector2DInt add(Vector2DInt vec) {
		
		X += vec.X;
		Y += vec.Y;
		
		return this;
		
	}
	
	public Vector2DInt subtract(Vector2DInt vec) {
		
		X -= vec.X;
		Y -= vec.Y;
		
		return this;
		
	}
	
	public Vector2DInt scale(double scale) {
		
		X *= scale;
		Y *= scale;
		
		return this;
		
	}
	
	public Vector2DInt fluctuate(double maxChange) {
		
		X += random.nextDouble() * maxChange * 2 - maxChange;
		Y += random.nextDouble() * maxChange * 2 - maxChange;
		
		return this;
		
	}
	
	@Override
	public Vector2DInt clone() {
		
		return new Vector2DInt(X, Y);
		
	}

}