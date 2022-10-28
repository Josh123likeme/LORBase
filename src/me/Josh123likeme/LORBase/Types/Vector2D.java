package me.Josh123likeme.LORBase.Types;

public class Vector2D implements Cloneable {
	
	public double X, Y;
	
	public Vector2D() { }
	
	public Vector2D(double x, double y) {
		
		X = x;
		Y = y;
		
	}
	
	public Vector2D normalise() {
		
		double mag = Math.sqrt(X*X + Y*Y);
		
		if (mag == 0) return this;
		
		X = X / mag;
		Y = Y / mag;
		
		return this;
		
	}
	
	public double distanceTo(Vector2D vec) {
		
		return Math.sqrt((X - vec.X) * (X - vec.X) + (Y - vec.Y) * (Y - vec.Y));
		
	}
	
	public double directionTo(Vector2D vec) {
		
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
	
	public Vector2D set(double x, double y) {
		
		X = x;
		Y = y;
		
		return this;
		
	}
	
	public Vector2D add(Vector2D vec) {
		
		X += vec.X;
		Y += vec.Y;
		
		return this;
		
	}
	
	public Vector2D subtract(Vector2D vec) {
		
		X -= vec.X;
		Y -= vec.Y;
		
		return this;
		
	}
	
	public Vector2D scale(double scale) {
		
		X *= scale;
		Y *= scale;
		
		return this;
		
	}
	
	@Override
	public Vector2D clone() {
		
		return new Vector2D(X, Y);
		
	}

}
