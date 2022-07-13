package me.Josh123likeme.LORBase;

import java.io.IOException;

import me.Josh123likeme.LORBase.Types.*;

public class Main {

	public static Game game;
	
	public static void main(String[] args) {
		
		try {
			ResourceLoader.loadResources();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		game = new Game();
		
		game.start();
		
	}
	
}
