package me.Josh123likeme.LORBase;

public class Main {

	public static Game game;
	
	public static void main(String[] args) {
		
		ResourceLoader.loadResources();
		
		game = new Game();
		
		game.start();
		
	}
	
}
