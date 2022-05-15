package me.Josh123likeme.LORBase;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.Josh123likeme.LORBase.EntityHolder.Player;
import me.Josh123likeme.LORBase.InputListener.KeyboardWitness;
import me.Josh123likeme.LORBase.InputListener.MouseWitness;
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
	
	private World world;
	
	private static Random random = new Random();
	
	private int fps = 0;
	private Vector2D camPos;
	private double guiScale;
	
	private Player player;
	
	public Game() {
		
		window = new Window(INITIAL_WIDTH, INITIAL_HEIGHT, "The Labyrinth Of Recursion", this);
		frameData = new FrameData();
		camPos = new Vector2D(10, 10);
		guiScale = 0.5d;
		player = new Player(new Vector2D(0, 0));
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

		double targetfps = 60d;
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
				
				//updateInfrequent();
				
			}
			
			//starting to push frame
			
			long nextTime = System.nanoTime() + targetDeltaFrame;
			
			updatePlayer();
			
			frameData.DeltaFrame = ((double) (System.nanoTime() - lastFrame)) / 1000000000;
			
			frameData.CameraPosition = player.getPosition();
			
			updateFrameData();
			
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
		
		world.render(g);
		
		if (keyboardWitness.getHeldKeys().contains(61)) {
			
			guiScale += 0.01;
			
		}
		else if (keyboardWitness.getHeldKeys().contains(45)) {
			
			guiScale -= 0.01;
			
		}
		
		//debugging stuff
		
		ArrayList<String> debugInfo = new ArrayList<String>();
				
		debugInfo.add("FPS: " + fps + " (Delta Frame: " + frameData.DeltaFrame + ")");
		
		debugInfo.add("Is Dragging: " + mouseWitness.isDragging());
		
		String keysPressed = "";
		
		List<Integer> keys = keyboardWitness.getHeldKeys();
		
		for (int i = 0; i < keys.size(); i++) {
			
			keysPressed += KeyEvent.getKeyText(keys.get(i)) + ", ";
			
		}
		
		debugInfo.add("Keys pressed: " + keysPressed);
		
		debugInfo.add("Player Pos: (" + player.getPosition().X + ", " + player.getPosition().Y + ")");
		
		g.setFont(new Font("", 0, getHeight() / 25));
		
		g.setColor(Color.white);
		
		for (int i = 0; i < debugInfo.size(); i++) {
			
			g.drawString(debugInfo.get(i), getWidth() / 100, (getHeight() / 25) * (i + 1));
			
		}
		
		//this pushes the graphics to the window
		bufferStrategy.show();
		
	}
	
	private void updateFrameData() {
		
		frameData.Width = getWidth();
		frameData.Height = getHeight();
		
		frameData.CameraPosition = camPos;
		frameData.GuiScale = guiScale;
		
	}
	
	public FrameData getFrameData() {
		
		return frameData;
		
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
		
	}
	
	private void updateInfrequent() {
		
		
		
	}
	
}
