package me.Josh123likeme.LORBase;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import me.Josh123likeme.LORBase.GameHolder.Save;
import me.Josh123likeme.LORBase.InputListener.ControlHolder.ButtonType;
import me.Josh123likeme.LORBase.InputListener.KeyboardWitness;
import me.Josh123likeme.LORBase.InputListener.MouseWitness;
import me.Josh123likeme.LORBase.UI.Menu;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	public static final int INITIAL_WIDTH = 400, INITIAL_HEIGHT = 400;
	
	private Window window;
	private Thread thread;
	private boolean running = false;
	
	public MouseWitness mouseWitness;
	public KeyboardWitness keyboardWitness;
	
	public FrameData frameData;
	public DebugInfo debugInfo;
	private boolean displayDebugInfo = false;
	
	public Save save;
	
	private Menu mainMenu;
	private Menu settingsMenu;
	private Menu bindsMenu;
	
	private int fps = 0;
	private double deltaFrame;
	
	private GameState gameState = GameState.START_UP;
	
	public Game() {
		
		window = new Window(INITIAL_WIDTH, INITIAL_HEIGHT, "The Labyrinth Of Recursion", this);
		
		debugInfo = new DebugInfo(this);
		
		frameData = new FrameData();
		
		initInputs();
		
		initMenus();
		
		gameState = GameState.MAIN_MENU;
		
	}
	
	public void initInputs() {
		
		mouseWitness = new MouseWitness();
		keyboardWitness = new KeyboardWitness();
		
		addMouseListener(mouseWitness);
		addMouseMotionListener(mouseWitness);
		addKeyListener(keyboardWitness);
		
		requestFocus();
		
	}
	
	public synchronized void start() {
		
		thread = new Thread(this);
		thread.start();
		running = true;
		
	}
	
	public synchronized void stop() {
		
		try 
		{
			thread.join();
			running = false;
		}
		
		catch(Exception e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	public void run() {

		double targetfps = SettingsHolder.targetfps;
		long targetDeltaFrame = Math.round((1d / targetfps) * 1000000000);
		long lastSecond = System.nanoTime();
		int frames = 0;
		
		long lastFrame = 0;
		
		updateFrameData();
		
		frameData.DeltaFrame = targetDeltaFrame;
		
		while (running) {
			
			frames++;
			
			if (lastSecond + 1000000000 < System.nanoTime()) {
				
				fps = frames;
				
				frames = 0;
				
				lastSecond = System.nanoTime();
				
				targetfps = SettingsHolder.targetfps;
				targetDeltaFrame = Math.round((1d / targetfps) * 1000000000);
				
				if (gameState == GameState.IN_GAME) save.updateInfrequent();
				
			}
			
			//starting to push frame
			
			long nextTime = System.nanoTime() + targetDeltaFrame;
			
			if (gameState == GameState.IN_GAME) {
				
				save.update();
				
				if (!save.running) {
					
					closeSave();
					
				}
				
			}
			
			deltaFrame = ((double) (System.nanoTime() - lastFrame)) / 1000000000;
			frameData.DeltaFrame = deltaFrame;
			
			
			updateFrameData();
			
			lastFrame = System.nanoTime();
			
			preFrame();
			
			paint();
			
			keyboardWitness.purgeTypedKeys();
			mouseWitness.purgeClickedButtons();
			
			//finished pushing frame
			
			while (nextTime > System.nanoTime());
			
		}
		stop();
		
	}

	private void paint() {
	
		BufferStrategy bufferStrategy = this.getBufferStrategy();
		if(bufferStrategy == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bufferStrategy.getDrawGraphics();
		
		//basic black background to stop flashing
		g.setColor(Color.black); 
		g.fillRect(0, 0, getWidth(), getHeight());
		
		//put rendering stuff here
		
		if (gameState == GameState.IN_GAME) save.render(g);	
		
		if (gameState == GameState.MAIN_MENU) mainMenu.render(g);
		
		if (displayDebugInfo) debugInfo.render(g);
		
		//this pushes the graphics to the window
		bufferStrategy.show();
		
	}
	
	private void preFrame() {
		
		//menu interaction
		
		if (gameState == GameState.MAIN_MENU) {
		
			if (mouseWitness.isLeftClicked()) {
				
				if (mainMenu.getButtonAt(mouseWitness.getMouseX(), mouseWitness.getMouseY()) == "START_GAME") {
					
					startSave();
					
				}
				
				if (mainMenu.getButtonAt(mouseWitness.getMouseX(), mouseWitness.getMouseY()) == "EXIT") {
					
					running = false;
					
				}
				
			}
			
		}
		
		//toggle checks
		
		if (gameState == GameState.IN_GAME) {
			
			if (keyboardWitness.isButtonTyped(ButtonType.PAUSE) && save != null) save.gamePaused = !save.gamePaused;
			
		}
		
		if (keyboardWitness.isButtonTyped(ButtonType.DEBUG_TOGGLE)) displayDebugInfo = !displayDebugInfo;
		
		//temp zoom
		
		if (save != null && save.world != null) {
			
			if (keyboardWitness.isButtonHeld(ButtonType.ZOOM_IN)) save.world.worldData.Zoom += 0.01;
			if (keyboardWitness.isButtonHeld(ButtonType.ZOOM_OUT)) save.world.worldData.Zoom -= 0.01;
		}
		
	}
	
	public boolean isDebugInfoDisplayed() {
		
		return displayDebugInfo;
		
	}
	
	private void updateFrameData() {
		
		frameData.Width = getWidth();
		frameData.Height = getHeight();
	
		
	}
	
	private void startSave() {

		save = new Save();
		
		gameState = GameState.IN_GAME;
		
	}
	
	private void closeSave() {
		
		save = null;
		
		gameState = GameState.MAIN_MENU;
		
	}
	
	private void initMenus() {
		
		mainMenu = new Menu();
		mainMenu.createElement("TITLE", "LABYRINTH OF RECURSION", 0.2, 0.05, 0.6, 0.2);
		mainMenu.createButton("START_GAME", "Start Game", 0.25, 0.3, 0.2, 0.2);
		mainMenu.createButton("SETTINGS", "Settings", 0.55, 0.3, 0.2, 0.2);
		mainMenu.createButton("EXIT", "Quit Game", "THERE IS NO ESCAPE", 0.55, 0.6, 0.2, 0.2);
		
		settingsMenu = new Menu();
		settingsMenu.createButton("TARGET_FPS", "Target Fps", 0.05, 0.05, 0.1, 0.1);
		settingsMenu.createButton("BLOCK_PIXEL_SIZE", "Block Pixel Size", 0.2, 0.2, 0.1, 0.1);
		settingsMenu.createButton("ZOOM", "Zoom", 0.35, 0.35, 0.1, 0.1);
		
	}
	
	public int getFPS() {
		
		return fps;
		
	}
	
	public double getDeltaFrame() {
		
		return deltaFrame;
		
	}
	
	public GameState getGameState() {
		
		return gameState;
		
	}
	
	public enum GameState {
		
		START_UP,
		MAIN_MENU,
		IN_GAME,
		
		;
		
	}
	
}
