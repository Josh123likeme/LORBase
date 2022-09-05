package me.Josh123likeme.LORBase;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import me.Josh123likeme.LORBase.BlockHolder.Floor;
import me.Josh123likeme.LORBase.EntityHolder.Player;
import me.Josh123likeme.LORBase.Generators.LabyrinthMaze;
import me.Josh123likeme.LORBase.InputListener.KeyboardWitness;
import me.Josh123likeme.LORBase.InputListener.MouseWitness;
import me.Josh123likeme.LORBase.Types.Cardinal;
import me.Josh123likeme.LORBase.Types.Vector2D;

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
	private boolean displayDebugInfo = true;
	
	public World world;
	
	private int fps = 0;
	
	private Player player;
	
	public Game() {
		
		window = new Window(INITIAL_WIDTH, INITIAL_HEIGHT, "The Labyrinth Of Recursion", this);
		frameData = new FrameData();
		debugInfo = new DebugInfo();
		frameData.CameraPosition = new Vector2D(50, 50);
		frameData.GuiScale = 5d;
		player = new Player(new Vector2D(1, 1), Cardinal.NORTH);
		world = new World(player);
		
		initInputs();
		
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
				
				//updateInfrequent();
				
			}
			
			//starting to push frame
			
			long nextTime = System.nanoTime() + targetDeltaFrame;
			
			updatePlayer();
			
			frameData.DeltaFrame = ((double) (System.nanoTime() - lastFrame)) / 1000000000;
			
			frameData.CameraPosition.X = player.getPosition().X;
			frameData.CameraPosition.Y = player.getPosition().Y;
			
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
		
		//TODO temporary scaling 
		if (keyboardWitness.getHeldKeys().contains(KeyEvent.VK_EQUALS)) {
			
			frameData.GuiScale += 0.01;
			
		}
		else if (keyboardWitness.getHeldKeys().contains(KeyEvent.VK_MINUS)) {
			
			frameData.GuiScale -= 0.01;
			
		}
		
		//put rendering stuff here
		
		world.render(g);
		
		//debugging stuff
		
		if (keyboardWitness.getHeldKeys().contains(KeyEvent.VK_F3)) displayDebugInfo = !displayDebugInfo;
		
		if (displayDebugInfo) {
			
			g.setColor(Color.white);
			g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, frameData.Height / 25)); 
			
			List<String> info = debugInfo.getDebugInfo();
			
			for (int i = 0; i < info.size(); i++) {
				
				g.drawString(info.get(i), getWidth() / 100, (getHeight() / 25) * (i + 1));
				
			}
			
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
	
	private void updateDebugInfo() {
		
		debugInfo.FPS = fps;
		debugInfo.DeltaFrame = frameData.DeltaFrame;
		
		debugInfo.IsDragging = mouseWitness.isDragging();
		debugInfo.HeldKeys = keyboardWitness.getHeldKeys();
		
		debugInfo.PlayerPos = player.getPosition();
		debugInfo.PlayerFacing = player.getFacing();
		debugInfo.CameraPos = frameData.CameraPosition;
		
		debugInfo.GuiScale = frameData.GuiScale;
		
	}
	
	private void updatePlayer() {
		
		List<Integer> keys = keyboardWitness.getHeldKeys();
		
		Vector2D movementVector = new Vector2D(0, 0);
		
		if (keys.contains(87)) movementVector.Y -= 1;
		if (keys.contains(65)) movementVector.X -= 1;
		if (keys.contains(83)) movementVector.Y += 1;
		if (keys.contains(68)) movementVector.X += 1;

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
	
}
