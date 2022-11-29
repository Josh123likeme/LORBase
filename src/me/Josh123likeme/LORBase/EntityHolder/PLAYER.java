package me.Josh123likeme.LORBase.EntityHolder;

import me.Josh123likeme.LORBase.Main;
import me.Josh123likeme.LORBase.GameHolder.World;
import me.Josh123likeme.LORBase.ItemHolder.Inventory;
import me.Josh123likeme.LORBase.ParticleHolder.DAMAGE_NUMBER;
import me.Josh123likeme.LORBase.Types.Vector2D;

public class PLAYER extends Entity implements ICollidable, IMoveable, IHasInventory, IHealthy {
	
	private double health;
	private double maxHealth;
	
	private double mana;
	private double maxMana;
	
	private double healingRate = 0.01d;
	
	private Inventory inventory;
	
	public PLAYER(World world, Vector2D initPos, double facing) {
		super(world, initPos, facing);
		
		health = 100d;
		maxHealth = 100d;
		
		mana = 100d;
		maxMana = 100d;
		
		inventory = new Inventory(8, 4);
		
	}
	
	public void updatePlayerInstance(World world) {
		
		this.world = world;
		
	}

	@Override
	public double getMovementSpeed() {
		
		return 5d;
		
	}

	@Override
	public Inventory getInventory() {
		
		return inventory;
		
	}

	@Override
	public double getHealth() {
		
		return health;
	}

	@Override
	public double getMaxHealth() {
		
		return maxHealth;
	}
	
	public double getMana() {
		
		return mana;
	}

	public double getMaxMana() {
		
		return maxMana;
	}
	
	@Override
	public void updateStats() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void update(World world) {
		
		health += maxHealth * healingRate * Main.game.getDeltaFrame();
		
		if (health > maxHealth) health = maxHealth;
		
	}

	@Override
	public void damage(double damage) {
		
		//calculate damage
		
		Vector2D particlePos = this.pos.clone();
		
		particlePos.fluctuate(getSize());
		
		world.addParticle(new DAMAGE_NUMBER(particlePos, damage));
		
		health -= damage;
		
		if (health < 0) {
			
			health = 0;
			
			kill();
			
		}
		
	}

	@Override
	public void kill() {
		Main.game.save.closeWorld();
		Main.game.save.startLobby();
		health = maxHealth;
		
	}

	@Override
	public double getSize() {
		
		return 0.8d;
	}
	
}
