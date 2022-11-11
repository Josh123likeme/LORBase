package me.Josh123likeme.LORBase.Types;

import java.util.Random;

public class Vector2D implements Cloneable {
	
	private static Random random = new Random();
	
	public double X, Y;
	
	public Vector2D() { }
	
	public Vector2D(double angle) {
		
		X = Math.cos(Math.toRadians(angle));
		Y = Math.sin(Math.toRadians(angle));
		
	}
	
	public Vector2D(double x, double y) {
		
		X = x;
		Y = y;
		
	}
	
	public double length() {
		
		return distanceTo(0, 0);
		
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
	
	public double distanceTo(double x, double y) {
		
		return Math.sqrt((X - x) * (X - x) + (Y - y) * (Y - y));
		
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
	
	public double directionTo(double x, double y) {
		
		double dx = x - X;
		double dy = x - Y;
		
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
	
	public double angleBetween(Vector2D vec) {
		
		return Math.toDegrees(Math.acos((dot(vec)) / (length() * vec.length())));
		
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
	
	public double dot(Vector2D vec) {
		
		return X * vec.X + Y * vec.Y;
		
	}
	
	public Vector2D fluctuate(double maxChange) {
		
		X += random.nextDouble() * maxChange * 2 - maxChange;
		Y += random.nextDouble() * maxChange * 2 - maxChange;
		
		return this;
		
	}
	
	@Override
	public Vector2D clone() {
		
		return new Vector2D(X, Y);
		
	}

}
