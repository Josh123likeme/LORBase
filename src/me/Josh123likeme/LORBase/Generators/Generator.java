package me.Josh123likeme.LORBase.Generators;

public abstract class Generator {
	
	//simple maze
	public static SimpleMaze createNewRandomMazeGenerator() {
		
		return new SimpleMaze();
		
	}
	
	//normal labyrinth generation
	public static LabyrinthMaze createNewLabyrinthGenerator() {
		
		return new LabyrinthMaze();
		
	}
	
	public static CaveMaze createNewCaveMaze() {
		
		return new CaveMaze();
		
	}
	
}
