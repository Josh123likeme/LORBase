package me.Josh123likeme.LORBase.EntityHolder;
import me.Josh123likeme.LORBase.Types.Cardinal;
import me.Josh123likeme.LORBase.Types.Vector2D;

public abstract class EntityBase {
	
	public final Entity type;	
	private final double size;
	
	private Vector2D pos;
	private double baseMovementSpeed; // m/s
	private double movementSpeed;
	private Cardinal facing;
	
	public EntityBase(Vector2D initPos, Entity type, double baseMovementSpeed, double size, Cardinal facing){
		
		pos = initPos;
		this.type = type;
		this.baseMovementSpeed = baseMovementSpeed;
		this.size = size;
		this.facing = facing;
		
	}
	
	public Vector2D getPosition() {
		
		return pos;
		
	}
	
	public double getMovementSpeed() {
		
		return baseMovementSpeed;
		
	}
	
	public Cardinal getFacing() {
		
		return facing;
		
	}
	
	public void setFacing(Cardinal facing) {
		
		this.facing = facing;
		
	}
	
	public void moveEntity(Vector2D pos) {
		
		double x = pos.X;
		double y = pos.Y;
		
		this.pos = pos;
		
	}

}