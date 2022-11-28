package me.Josh123likeme.LORBase.EntityHolder;

import me.Josh123likeme.LORBase.GameHolder.World;
import me.Josh123likeme.LORBase.ItemHolder.Item;
import me.Josh123likeme.LORBase.ItemHolder.ZOMBIE_FLESH;
import me.Josh123likeme.LORBase.ParticleHolder.DAMAGE_NUMBER;
import me.Josh123likeme.LORBase.Types.Vector2D;

public class ZOMBIE extends Entity implements IAttack, IChase, ICollidable, IMoveable, IHealthy {
	
	private double health;
	
	private long nextAttackTime;
	private final double attackCooldown = 1d;
	
	public ZOMBIE(World world, Vector2D pos, double facing) {
		super(world, pos, facing);
		
		health = 100d;
		
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
	public double getFieldOfView() {
		
		return 120d;
		
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
	public void tryAttack() {
		
		double reach = 1d;
		
		if (state == BrainState.ATTACK && pos.distanceTo(world.player.getPosition()) < reach && System.nanoTime() > nextAttackTime) {
			
			world.player.damage(random.nextDouble() * 10 + 15);
			
			nextAttackTime = (long) (System.nanoTime() + attackCooldown * 1000000000);
			
		}
		
	}
}
