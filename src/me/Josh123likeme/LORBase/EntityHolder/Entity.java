package me.Josh123likeme.LORBase.EntityHolder;

import java.awt.image.BufferedImage;
import java.util.Random;

import me.Josh123likeme.LORBase.BlockHolder.Wall;
import me.Josh123likeme.LORBase.GameHolder.World;
import me.Josh123likeme.LORBase.ParticleHolder.INDICATOR;
import me.Josh123likeme.LORBase.Types.Vector2D;
import me.Josh123likeme.LORBase.Utils.AStar;
import me.Josh123likeme.LORBase.Main;
import assets.Assets;

public abstract class Entity {
	
	protected static Random random = new Random();
	
	protected World world;
	
	protected Vector2D pos;
	protected double facing;
	
	protected BrainState state;
	private Vector2D[] path;
	
	protected BufferedImage defaultTexture;
	
	public Entity(World world, Vector2D pos, double facing) {
		
		this.world = world;
		this.pos = pos;
		this.facing = facing;
		
		this.state = BrainState.WANDER;
		this.path = new Vector2D[] {pos};
		
	}
	
	/**
	 * override this to give conditional textures
	 * @return
	 */
	public BufferedImage getTexture() {
		
		if (defaultTexture == null) loadTextures();
		
		return defaultTexture;
		
	}
	
	/**
	 * override this to load textures
	 */
	protected void loadTextures() {
		
		 defaultTexture = Assets.LoadTextureFromAssets("textures/entity/" + this.getClass().getSimpleName() + ".png");
		
	}
	
	public void update(World world) {
		
		if (this instanceof IChase) {

			updatePathfinding();
			
		}
		if (this instanceof IAttack) {
			
			((IAttack) this).tryAttack();
			
		}
		
	}
	
	private void updatePathfinding() {
		
		IChase entity = (IChase) this;
		
		//update brain state
		if (pos.distanceTo(world.player.getPosition()) < 1) state = BrainState.ATTACK;
		else if (pos.distanceTo(world.player.getPosition()) < entity.getViewDistance()) state = BrainState.CHASE;
		else state = BrainState.WANDER;
		
		double angle = 0d;
		Vector2D next = new Vector2D();
		
		switch (state) {
		
		case WANDER:
			
			//choose new target
			if (random.nextDouble() < Main.game.getDeltaFrame() / 5) {
				
				Vector2D choice = new Vector2D();
				
				while (true) {
					
					choice.X = pos.X + random.nextInt(10) - 5;
					choice.Y = pos.Y + random.nextInt(10) - 5;
					
					if (choice.X < 0 || choice.X > world.getWidth()) continue;
					if (choice.Y < 0 || choice.Y > world.getHeight()) continue;
					
					if (world.getWall((int) choice.X, (int) choice.Y) != Wall.AIR) continue;
					
					break;
					
				}
				
				path = new Vector2D[] {choice};
	
			}	
			
			break;
			
		case CHASE:
			
			//temp for testing pathfinding quickly
			Vector2D[] newPath = AStar.doAStar(world, this, world.player);
			
			//the target is unreachable
			if (newPath == null) break;
			
			//the pathfinding is significantly different, so we update it
			if (path[0].distanceTo(newPath[1]) > 0.1) {
				
				path = newPath;
				
			}
			
			break;
			
		case ATTACK:
		
			path = new Vector2D[] {world.player.getPosition()};
			
			break;
		
		}	
		
		//progress along path
		if (pos.distanceTo(path[0]) < 0.01 && path.length > 1) {
			
			Vector2D[] newPath = new Vector2D[path.length - 1];
			
			for (int i = 0; i < newPath.length; i++) {
				
				newPath[i] = path[i + 1];
				
			}
			
			path = newPath;
			
		}
		
		//path visualisation
		for (int i = 0; i < path.length; i++) {
			
			world.addParticle(new INDICATOR(path[i]));
			
		}
		
		angle = pos.directionTo(path[0]);
		
		next = pos.clone();
		next.X += Math.cos(Math.toRadians(angle)) * ((IMoveable) entity).getMovementSpeed() * Main.game.getDeltaFrame();
		next.Y += Math.sin(Math.toRadians(angle)) * ((IMoveable) entity).getMovementSpeed() * Main.game.getDeltaFrame();
		
		facing = angle;
		if (!(path.length == 1 && pos.distanceTo(path[0]) < 0.1)) moveEntity(next, world);
		
	}
	
	public void moveEntity(Vector2D pos, World world) {
		
		if (this instanceof ICollidable) {
			
			if (world.getWall((int) pos.X, (int) pos.Y) != Wall.AIR) return;
			if (world.getWall((int) (pos.X + getSize()), (int) pos.Y) != Wall.AIR) return;
			if (world.getWall((int) pos.X, (int) (pos.Y + getSize())) != Wall.AIR) return;
			if (world.getWall((int) (pos.X + getSize()), (int) (pos.Y + getSize())) != Wall.AIR) return;
			
		}
		
		this.pos = pos;
		
		Main.game.debugInfo.addOrUpdateTask(this.getClass().getSimpleName(),  pos.X + ", " + pos.Y);
		
	}
	
	public Vector2D getPosition() {
		
		return pos;
		
	}
	
	public void setPosition(Vector2D position) {
		
		pos = position.clone();
		
	}
	
	public double getFacing() {
		
		return facing;
		
	}
	
	public void setFacing(double facing) {
		
		facing %= 360;
		
		if (facing < 0) facing = 360 + facing;
		
		this.facing = facing;
		
	}
	/*
	public boolean canSee(Vector2D target) {
		
		if (!(this instanceof ISmart)) throw new IllegalArgumentException("This entity cant see!");

		if (pos.distanceTo(target) > ((ISmart) this).getViewDistance()) return false;
		
		Vector2D looking = new Vector2D(facing);
		Vector2D direction = new Vector2D(target.X, target.Y).subtract(pos).normalise();

		if (looking.angleBetween(direction) > ((ISmart) this).getFieldOfView()) return false;
		
		Vector2D current = pos.clone();
		
		while (current.distanceTo(target) > 0.5) {
			
			if (world.getWall((int) current.X, (int) current.Y) != Wall.AIR) return false;
			
			current.add(direction);
			
		}
		
		return true;
		
	}
	*/
	public abstract double getSize();
	
}
