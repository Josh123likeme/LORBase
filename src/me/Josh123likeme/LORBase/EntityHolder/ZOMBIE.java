package me.Josh123likeme.LORBase.EntityHolder;

import me.Josh123likeme.LORBase.Main;
import me.Josh123likeme.LORBase.BlockHolder.Wall;
import me.Josh123likeme.LORBase.GameHolder.World;
import me.Josh123likeme.LORBase.ItemHolder.Item;
import me.Josh123likeme.LORBase.ItemHolder.ZOMBIE_FLESH;
import me.Josh123likeme.LORBase.ParticleHolder.DAMAGE_NUMBER;
import me.Josh123likeme.LORBase.Types.Vector2D;

public class ZOMBIE extends Entity implements ISmart, ICollidable, IMoveable, IHealthy {
	
	private double health;
	
	WanderState state;
	Vector2D target;
	
	private long nextAttackTime;
	private final double attackCooldown = 1d;
	
	public ZOMBIE(World world, Vector2D pos, double facing) {
		super(world, pos, facing);
		
		health = 100d;
		
		state = WanderState.WANDER;
		target = pos.clone();
		
		nextAttackTime = System.nanoTime();
		
	}

	@Override
	public double getMovementSpeed() {
		
		return 2d;	
	}
	
	@Override
	public double getViewDistance() {
		
		return 10d;
	}
	
	@Override
	public double getHealth() {
		// TODO Auto-generated method stub
		return health;
	}

	@Override
	public double getMaxHealth() {
		// TODO Auto-generated method stub
		return 100;
	}
	
	@Override
	public void updateStats() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void damage(double damage) {
		
		health -= damage;
		
		Vector2D particlePos = this.pos.clone();
		
		particlePos.fluctuate(getSize());
		
		world.addParticle(new DAMAGE_NUMBER(particlePos, damage));
		
	}
	
	@Override
	public double getSize() {
		
		return 0.8d;
	}
	
	@Override
	public void kill() {
		
		Item drop = new ZOMBIE_FLESH();
		
		world.addEntity(new ITEM_ENTITY(world, pos, 270, drop, 1d));
		
	}

	@Override
	public void updateBrain() {
		
		if (pos.distanceTo(world.player.getPosition()) > getViewDistance()) state = WanderState.WANDER;
		else state = WanderState.ATTACK;
		
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
				
				target = choice;
	
			}	
			
			break;
			
		case ATTACK:

			target = world.player.getPosition().clone();
		
			break;
		
		}	
		
		if (pos.distanceTo(target) > 0.4) {
			
			angle = pos.directionTo(target);
			
			next = pos.clone();
			next.X += Math.cos(Math.toRadians(angle)) * getMovementSpeed() * Main.game.getDeltaFrame();
			next.Y += Math.sin(Math.toRadians(angle)) * getMovementSpeed() * Main.game.getDeltaFrame();
			
			facing = angle;
			moveEntity(next, world);
			
		}
		
		if (state == WanderState.ATTACK && pos.distanceTo(target) < 0.8 && System.nanoTime() > nextAttackTime) {
			
			world.player.damage(random.nextDouble() * 10 + 15);
			
			nextAttackTime = (long) (System.nanoTime() + attackCooldown * 1000000000);
			
		}
		
	}

}
