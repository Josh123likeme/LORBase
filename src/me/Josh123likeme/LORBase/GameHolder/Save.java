package me.Josh123likeme.LORBase.GameHolder;

import java.awt.Graphics;

import me.Josh123likeme.LORBase.Game;
import me.Josh123likeme.LORBase.Main;
import me.Josh123likeme.LORBase.EntityHolder.PLAYER;
import me.Josh123likeme.LORBase.Types.Vector2D;
import me.Josh123likeme.LORBase.UI.Menu;

public class Save {

	private Game game;
	
	public Lobby lobby;
	public World world;
	
	private Thread worldLoadThread;
	private boolean worldReady = false;
	public int level = 1;
	
	public PLAYER player;
	
	public boolean gamePaused;
	private Menu pauseMenu;
	
	public boolean running;
	
	public Save() {
		
		running = true;
		
		game = Main.game;
		
		player = new PLAYER(world, new Vector2D(1, 1), 90);
		
		initMenus();
		
		startLobby();		
		
		gamePaused = false;
	}
	
	private void initMenus() {
		
		pauseMenu = new Menu();
		pauseMenu.createButton("RESUME", "Resume Game", 0.4, 0.2, 0.2, 0.2);
		//pauseMenu.createButton("SETTINGS", "Settings", 0.4, 0.45, 0.2, 0.2);
		pauseMenu.createButton("EXIT", "Quit Game", 0.4, 0.45, 0.2, 0.2);
		
	}
	
	public void startWorld() {
		
		if (worldLoadThread != null && worldLoadThread.isAlive()) return;
		
		final Save thiz = this;
		
		worldLoadThread = new Thread(new Runnable() {
		    @Override
		    public void run() {
		    	
		        world = new World(thiz, level);
		        
		        player.updatePlayerInstance(world);
		        
		        worldReady = true;
		        
		    }
		});  
		worldLoadThread.start();
		
		
		
	}
	
	public void closeWorld() {
		
		world = null;
		worldReady = false;
		
	}
	
	public void startLobby() {
		
		lobby = new Lobby(this);
		
	}
	
	public void closeLobby() {
		
		lobby = null;
		
	}
	
	public void render(Graphics g) {
		
		if (worldReady) world.render(g);
		if (lobby != null) lobby.render(g);	
		
		if (gamePaused) {
			
			pauseMenu.render(g);
			
		}
		
		
		
	}
	
	private void save() {
		
		
		
	}
	
	public void update() {
		
		if (worldReady) {
			
			world.update();
			
		}
		if (lobby != null) {
			
			lobby.update();
			lobby.detectMenuClicks();
				
		}
		
		//menu interaction
		
		if (game.mouseWitness.isLeftClicked() && gamePaused && pauseMenu.getButtonAt(game.mouseWitness.getMouseX(), game.mouseWitness.getMouseY()) != null) {
			
			switch (pauseMenu.getButtonAt(game.mouseWitness.getMouseX(), game.mouseWitness.getMouseY())) {
			
			case "RESUME":
				
				gamePaused = !gamePaused;
				
				break;
			
			case "SETTINGS":	
				
				break;
			
			case "EXIT":
				
				if (lobby != null) {
					
					closeLobby();
					
					save();
					
					running = false;
					
				}
				else {
					
					closeWorld();
					
					startLobby();
					
					gamePaused = !gamePaused;
					
				}
				
				break;
			
			}
			
		}
		
	}
	
	public void updateInfrequent() {
		
		if (world != null) {
			
			world.updateInfrequent();
			
		}
		
	}
	
	public boolean inWorld() {
		
		if (world != null) return true;
		
		return false;
		
	}
	
}
