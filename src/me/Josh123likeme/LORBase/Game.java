package me.Josh123likeme.LORBase;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.List;

import javax.swing.plaf.basic.BasicGraphicsUtils;

import me.Josh123likeme.LORBase.BlockHolder.Floor;
import me.Josh123likeme.LORBase.BlockHolder.Wall;
import me.Josh123likeme.LORBase.EntityHolder.Player;
import me.Josh123likeme.LORBase.InputListener.ControlHolder.ButtonType;
import me.Josh123likeme.LORBase.InputListener.KeyboardWitness;
import me.Josh123likeme.LORBase.InputListener.MouseWitness;
import me.Josh123likeme.LORBase.Types.Cardinal;
import me.Josh123likeme.LORBase.Types.Vector2D;
import me.Josh123likeme.LORBase.UI.Menu;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	public static final int INITIAL_WIDTH = 400, INITIAL_HEIGHT = 400;
	
	private Window window;
	private Thread thread;
	private boolean running = false;
	
	private MouseWitness mouseWitness;
	private KeyboardWitness keyboardWitness;
	
	private FrameData frameData;
	
	private DebugInfo debugInfo;
	private WorldData worldData;
	private boolean displayDebugInfo = true;
	
	public World world;
	
	private Menu menu; //TODO temp menu test
	private boolean menuOpen = true; //TODO temp menu test
	
	private int fps = 0;
	
	private Player player;
	
	private GameState gameState = GameState.START_UP;
	
	public Game() {
		
		window = new Window(INITIAL_WIDTH, INITIAL_HEIGHT, "The Labyrinth Of Recursion", this);
		
		debugInfo = new DebugInfo();
		
		frameData = new FrameData();
		worldData = new WorldData();
		
		initInputs();
		
		gameState = GameState.MAIN_MENU;
		
		//TODO temp force labyrinth load
		startlabyrinth();
		
		//TODO temp menu test
		menu = new Menu();
		
		menu.createButton("Test", 100, 100, 200, 200);
		menu.createButton(ResourceLoader.getTexture(Wall.LABYRINTH_WALL), 100, 400, 200, 200);
		
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
		
		catch(Exception e) {e.printStackTrace();}
		
	}
	
	public void run() {

		double targetfps = 10000000d;
		long targetDeltaFrame = Math.round((1d / targetfps) * 1000000000);
		long lastSecond = System.nanoTime();
		int frames = 0;
		
		long lastFrame = 0;
		
		updateFrameData();
		updateDebugInfo();
		
		frameData.DeltaFrame = targetDeltaFrame;
		
		while (running) {
			
			frames++;
			
			if (lastSecond + 1000000000 < System.nanoTime()) {
				
				fps = frames;
				
				frames = 0;
				
				lastSecond = System.nanoTime();
				
				updateInfrequent();
				
			}
			
			//starting to push frame
			
			long nextTime = System.nanoTime() + targetDeltaFrame;
			
			if (gameState == GameState.IN_GAME) {
				
				//TODO temp zoom
				if (keyboardWitness.isButtonHeld(ButtonType.ZOOM_IN)) {
					
					worldData.Zoom += 0.01;
					
				}
				else if (keyboardWitness.isButtonHeld(ButtonType.ZOOM_OUT)) {
					
					worldData.Zoom -= 0.01;
					
				}
				
				updatePlayer();
				updateWorldData();
				
			}
			
			frameData.DeltaFrame = ((double) (System.nanoTime() - lastFrame)) / 1000000000;
			
			updateFrameData();
			updateDebugInfo();
			
			lastFrame = System.nanoTime();
			
			paint();
			
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
		
		if (gameState == GameState.IN_GAME) {
			
			world.render(g);
			
		}	
		
		//debugging stuff
		
		if (keyboardWitness.isButtonHeld(ButtonType.DEBUG_TOGGLE)) displayDebugInfo = !displayDebugInfo;
		
		if (displayDebugInfo) {
			
			g.setColor(Color.white);
			g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, frameData.Height / 25)); 
			
			List<String> info = debugInfo.getDebugInfo();
			
			for (int i = 0; i < info.size(); i++) {
				
				g.drawString(info.get(i), getWidth() / 100, (getHeight() / 25) * (i + 1));
				
			}
			
			g.drawString("Tasks:", getWidth() - (getWidth() / 100) - g.getFontMetrics().stringWidth("Tasks:"), (getHeight() / 25));
			
			info = debugInfo.getTaskInfo();
			
			for (int i = 0; i < info.size(); i++) {
				
				g.drawString(info.get(i), getWidth() - (getWidth() / 100) - g.getFontMetrics().stringWidth(info.get(i)), (getHeight() / 25) * (i + 2));
				
			}
			
		}
		
		if (menuOpen) {
			
			menu.render(g);
			
		}
		
		//Main.lm.displayLabyrinth(g); //TODO debug
		
		//this pushes the graphics to the window
		bufferStrategy.show();
		
	}
	
	private void updateFrameData() {
		
		frameData.Width = getWidth();
		frameData.Height = getHeight();
		
	}
	
	public FrameData getFrameData() {
		
		return frameData;
		
	}
	
	private void updateWorldData() {
		
		worldData.CameraPosition.X = player.getPosition().X;
		worldData.CameraPosition.Y = player.getPosition().Y;
		
	}
	
	public WorldData getWorldData() {
		
		return worldData;
		
	}
	
	private void updateDebugInfo() {
		
		debugInfo.GameState = gameState;
		
		debugInfo.FPS = fps;
		debugInfo.DeltaFrame = frameData.DeltaFrame;
		
		debugInfo.IsDragging = mouseWitness.isDragging();
		debugInfo.HeldKeys = keyboardWitness.getHeldKeys();
		
		try {
			
			debugInfo.PlayerPos = player.getPosition();
			debugInfo.PlayerFacing = player.getFacing();
			
		} catch (Exception e) { }
		
		try {
			
			debugInfo.CameraPos = worldData.CameraPosition;
			debugInfo.Zoom = worldData.Zoom;
			
		} catch (Exception e) { }
		
	}
	
	private void updatePlayer() {
		
		Vector2D movementVector = new Vector2D(0, 0);
		
		if (keyboardWitness.isButtonHeld(ButtonType.MOVE_UP)) movementVector.Y -= 1;
		if (keyboardWitness.isButtonHeld(ButtonType.MOVE_LEFT)) movementVector.X -= 1;
		if (keyboardWitness.isButtonHeld(ButtonType.MOVE_DOWN)) movementVector.Y += 1;
		if (keyboardWitness.isButtonHeld(ButtonType.MOVE_RIGHT)) movementVector.X += 1;

		movementVector.normalise();
		
		movementVector.X = movementVector.X * player.getMovementSpeed() * frameData.DeltaFrame;
		movementVector.Y = movementVector.Y * player.getMovementSpeed() * frameData.DeltaFrame;
		
		player.moveEntity(new Vector2D(player.getPosition().X + movementVector.X, player.getPosition().Y + movementVector.Y));

		if (movementVector.X == 0 && movementVector.Y < 0) player.setFacing(Cardinal.NORTH);
		if (movementVector.X == 0 && movementVector.Y > 0) player.setFacing(Cardinal.SOUTH);
		if (movementVector.X > 0 && movementVector.Y == 0) player.setFacing(Cardinal.EAST);
		if (movementVector.X < 0 && movementVector.Y == 0) player.setFacing(Cardinal.WEST);
		
		world.setFloor((int) player.getPosition().X, (int) player.getPosition().Y, Floor.MOGUS); //TODO remove
		
	}
	
	private void updateInfrequent() {
		
		
		
	}
	
	private void startlabyrinth() {
		
		player = new Player(new Vector2D(0, 0), Cardinal.NORTH);
		
		worldData = new WorldData();
		
		worldData.CameraPosition = new Vector2D(0, 0);
		worldData.Zoom = 5d;
		
		world = new World(player);
		
		gameState = GameState.IN_GAME;
		
	}
	
	public enum GameState {
		
		START_UP,
		MAIN_MENU,
		IN_GAME,
		
		;
		
	}
	
}
