package me.Josh123likeme.LORBase;

import me.Josh123likeme.LORBase.Generators.Generator;
import me.Josh123likeme.LORBase.Generators.LabyrinthMaze;

public class Main {

	public static Game game;
	
	public static LabyrinthMaze lm;
	
	public static void main(String[] args) {
		
		ResourceLoader.loadResources();
		
		lm = Generator.createNewLabyrinthGenerator();
		
		lm.generateMaze(10, 10);
		
		game = new Game();
		
		game.start();
		
	}
	
}
