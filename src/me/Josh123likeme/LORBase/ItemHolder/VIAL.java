package me.Josh123likeme.LORBase.ItemHolder;

public class VIAL extends Item {
	
	private static final int maxCompressionLevel = 10;
	int compressionLevel;
	double maxCapacity;
	
	Essence essence;
	double essenceVolume;
	
	public VIAL() {
		
		displayName = "Vial";
		description.add("A small glass ampoule used for harnessing compressed essentia");
		description.add("this vial is empty");
		
	}
	
	public void addEssence(int amount) {
		
		if (essenceVolume + amount > maxCapacity) throw new IllegalArgumentException("cannot increase amount of essence above maximum");
		
		essenceVolume += amount;
		
	}
	
	public void addEssence(Essence essence, int amount) {
		
		if (this.essence != null) {
			
			if (essence != this.essence) throw new IllegalArgumentException("you can only add essence of the same type");
			
		}
		
		if (essenceVolume + amount > maxCapacity) throw new IllegalArgumentException("cannot increase amount of essence above maximum");
		
		essenceVolume += amount;
		
	}
	
	public void upgradeCompressionLevel() {
		
		if (compressionLevel + 1 > maxCompressionLevel) throw new IllegalArgumentException("cannot increase compression level above maximum");
		
		compressionLevel++;
		
		maxCapacity = getCapacityAtLevel(compressionLevel);
		
	}

	public int getCapacityAtLevel(int level) {
		
		if (level <= 0) throw new IllegalArgumentException("level must be positive");
		
		return (int) (level >= 10 ? 100 : 0.5 * (level + 3) * (level + 4));
		
	}
	
}
