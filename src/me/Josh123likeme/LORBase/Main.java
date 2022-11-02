package me.Josh123likeme.LORBase;

import me.Josh123likeme.LORBase.Types.Vector2D;
import me.Josh123likeme.LORBase.Utils.AStar;

public class Main {

	public static Game game;
	
	public static void main(String[] args) {
		
		ResourceLoader.loadResources();
		
		game = new Game();
		
		game.start();
		
	}
	
}
