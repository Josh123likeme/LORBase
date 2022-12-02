package me.Josh123likeme.LORBase.EntityHolder;

import java.awt.image.BufferedImage;

import assets.Assets;
import me.Josh123likeme.LORBase.GameHolder.World;
import me.Josh123likeme.LORBase.ItemHolder.Item;
import me.Josh123likeme.LORBase.ItemHolder.VIAL;
import me.Josh123likeme.LORBase.ParticleHolder.DAMAGE_NUMBER;
import me.Josh123likeme.LORBase.Types.Vector2D;

public class GARGOYLE extends Entity implements IAttack, IChase, IMoveable, IHealthy, ISleep {

	private int level;
	
	private double health;
	private double maxHealth;
	
	private long nextAttackTime;
	private final double attackCooldown = 0.5d;
	
	private boolean sleeping = true;
	private static BufferedImage sleepingTexture;
	
	public GARGOYLE(World world, Vector2D pos, double facing, int level) {
		super(world, pos, facing);
		
		this.level = level;
		
		maxHealth = 200 * level;
		health = maxHealth;
		
	}
	
	@Override
	public BufferedImage getTexture() {
		
		if (defaultTexture == null) loadTextures();
		
		if (sleeping) return sleepingTexture;
		
		return defaultTexture;
		
	}
	
	@Override
	protected void loadTextures() {
		
		 defaultTexture = Assets.LoadTextureFromAssets(
				 "textures/entity/" + this.getClass().getSimpleName() + ".png");
		 sleepingTexture = Assets.LoadTextureFromAssets(
				 "textures/entity/" + this.getClass().getSimpleName() + "_SLEEPING" + ".png");
		 
	}

	@Override
	public double getHealth() {
		// TODO Auto-generated method stub
		return health;
	}

	@Override
	public double getMaxHealth() {
		// TODO Auto-generated method stub
		return maxHealth;
	}

	@Override
	public void updateStats() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void damage(double damage) {
		
		sleeping = false;
		
		health -= damage;
		
		Vector2D particlePos = this.pos.clone();
		
		particlePos.fluctuate(getSize());
		
		world.addParticle(new DAMAGE_NUMBER(particlePos, damage));
		
	}

	@Override
	public void kill() {
		// TODO Auto-generated method stub
		
		if (random.nextDouble() < 0.1d) {
			
			Item drop = new VIAL();
			
			world.addEntity(new ITEM_ENTITY(world, pos, 270, drop, 0.2d));
			
		}
			
	}

	@Override
	public double getMovementSpeed() {
		// TODO Auto-generated method stub
		return 4;
	}

	@Override
	public double getViewDistance() {
		// TODO Auto-generated method stub
		return 20;
	}

	@Override
	public double getFieldOfView() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void tryAttack() {
		
		double reach = 2d;
		
		if (state == BrainState.ATTACK && pos.distanceTo(world.player.getPosition()) < reach && System.nanoTime() > nextAttackTime) {
			
			world.player.damage(random.nextDouble() * 10 * level + 10 * level);
			
			nextAttackTime = (long) (System.nanoTime() + attackCooldown * 1000000000);
			
		}
		
	}

	@Override
	public double getSize() {
		// TODO Auto-generated method stub
		return 0.8d;
	}

	@Override
	public boolean isSleeping() {
		
		return sleeping;
		
	}

}
