package me.Josh123likeme.LORBase.EntityHolder;

public interface IHealthy {

	public double getHealth();
	
	public double getMaxHealth();
	
	public void updateStats();
	
	public void damage(double damage);
	
	public void kill();
	
}
