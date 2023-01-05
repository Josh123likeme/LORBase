package me.Josh123likeme.LORBase.GameHolder;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

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
		mainMenu.createButton("INCREASE_LEVEL", " > ", 0.625, 0.825, 0.05, 0.1);
		mainMenu.createButton("DECREASE_LEVEL", " < ", 0.525, 0.825, 0.05, 0.1);
		mainMenu.createElement("LEVEL", " " + Integer.toString(save.level) + " ", 0.575, 0.825, 0.05, 0.1);
		
	}
	
	public void render(Graphics g) {

		save.player.getInventory().render(g);
		
		peekMenuStack().render(g);
		
	}
	
	public void update() {
		
		save.player.getInventory().updateInventoryUI();
		
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
		else if (topMenu == mainMenu && button == "INCREASE_LEVEL") {
			
			save.level = save.level + 1 <= 10 ? save.level + 1 : 1;
			
			mainMenu.updateElementText("LEVEL", " " + Integer.toString(save.level) + " ");
			
		}
		else if (topMenu == mainMenu && button == "DECREASE_LEVEL") {
			
			save.level = save.level - 1 >= 1 ? save.level - 1 : 10;
			
			mainMenu.updateElementText("LEVEL", " " + Integer.toString(save.level) + " ");
			
		}
		
	}
	
	private Menu peekMenuStack() {
		
		return menuStack.get(menuStack.size() - 1);
		
	}
	
	private void popMenuStack() {
		
		menuStack.remove(menuStack.size() - 1);
		
	}
	
}
