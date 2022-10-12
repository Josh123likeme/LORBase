package me.Josh123likeme.LORBase.GameHolder;

import java.util.ArrayList;
import java.util.List;
import java.awt.Graphics;

import me.Josh123likeme.LORBase.Game;
import me.Josh123likeme.LORBase.Main;
import me.Josh123likeme.LORBase.UI.Menu;

class Lobby {

	private Game game;
	
	private Menu mainMenu;
	private Save save;
	
	private List<Menu> menuStack = new ArrayList<Menu>();
	
	public Lobby(Save save) {
		
		this.save = save;
		
		game = Main.game;
		
		initMenus();
		
		menuStack.add(mainMenu);
		
	}
	
	private void initMenus() {
		
		mainMenu = new Menu();
		mainMenu.createButton("ENTER", "Enter Labyrinth", 0.7, 0.8, 0.25, 0.15);
		
	}
	
	public void render(Graphics g) {

		peekMenuStack().render(g);
		
	}
	
	public void detectMenuClicks() {
		
		Menu topMenu = peekMenuStack();
		
		String button = game.mouseWitness.isLeftClicked() ? topMenu.getButtonAt(game.mouseWitness.getMouseX(), game.mouseWitness.getMouseY()) : null;
		
		if (button == null) return;
		
		if (button == "BACK") {
			
			popMenuStack();
			
		}
		
		if (topMenu == mainMenu && button == "ENTER") {
			
			save.startWorld();
			save.closeLobby();
			
		}
		
	}
	
	private Menu peekMenuStack() {
		
		return menuStack.get(menuStack.size() - 1);
		
	}
	
	private void popMenuStack() {
		
		menuStack.remove(menuStack.size() - 1);
		
	}
	
}
