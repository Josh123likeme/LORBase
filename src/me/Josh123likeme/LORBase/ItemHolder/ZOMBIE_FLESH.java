package me.Josh123likeme.LORBase.ItemHolder;

public class ZOMBIE_FLESH extends Item implements IStackable {

	private final int maxStackSize = 20;
	private int stackSize;
	
	public ZOMBIE_FLESH() {
		
		displayName = "Zombie Flesh";
		
		description.add("A piece of flesh from a zombie");
		
	}
	
	@Override
	public int getStackSize() {
		
		return stackSize;
	}

	@Override
	public int getMaxStackSize() {
		
		return maxStackSize;
		
	}

	@Override
	public void addOne() {
		
		if (stackSize + 1 > maxStackSize) throw new IllegalArgumentException("The stack is already at max capacity");
		
		stackSize++;
		
	}
	
}
