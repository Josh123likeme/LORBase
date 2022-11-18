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
	
	//cave labyrinth generation
	public static CaveMaze createNewCaveMaze() {
		
		return new CaveMaze();
		
	}
	
	//just an empty space
	public static EmptyMaze createNewEmptyMaze() {
		
		return new EmptyMaze();
		
	}
	
	//a maze filled with random boxes
	public static BoxyMaze createNewBoxyMaze() {
			
		return new BoxyMaze();
			
	}
	
}
