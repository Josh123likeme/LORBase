package me.Josh123likeme.LORBase;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.Josh123likeme.LORBase.InputListener.KeyboardWitness;
import me.Josh123likeme.LORBase.InputListener.MouseWitness;

public class DebugInfo {
	
	Game game;
	
	private HashMap<String, String> tasks = new HashMap<String, String>();
	
	public DebugInfo(Game game) {
		
		this.game = game;
		
	}
	
	public void addTask(String task) {
		
		if (tasks.keySet().contains(task)) throw new IllegalArgumentException("There is already a task running with that name");
		
		tasks.put(task, null);
		
	}
	
	public void addTask(String task, String progress) {
		
		if (tasks.keySet().contains(task)) throw new IllegalArgumentException("There is already a task running with that name");
		
		tasks.put(task, progress);
		
	}
	
	public void addOrUpdateTask(String task) {
		
		tasks.put(task, null);
		
	}
	
	public void addOrUpdateTask(String task, String progress) {
		
		tasks.put(task, progress);
		
	}
	
	public void updateTask(String task, String progress) {
		
		if (!tasks.keySet().contains(task)) throw new IllegalArgumentException("There is no task running with that name");
		
		tasks.put(task, progress);
		
	}
	
	public void removeTask(String task) {
		
		tasks.remove(task);
		
	}
	
	public void render(Graphics g) {
		
		//debug info
		
		MouseWitness mouseWitness = game.mouseWitness;
		KeyboardWitness keyboardWitness = game.keyboardWitness;
		
		List<String> lines = new ArrayList<String>();
		
		lines.add("Game State: " + game.getGameState());
		lines.add("FPS: " + game.getFPS() + " (Delta Frame: " + game.getDeltaFrame() + ")");
		
		lines.add("Is Dragging: " + mouseWitness.isDragging());
		
		String keysPressed = "";
		
		//just in case a key is pressed during the loops runtime
		try {
			
			for (int i = 0; i < keyboardWitness.getHeldKeys().size(); i++) {
				
				keysPressed += KeyEvent.getKeyText(keyboardWitness.getHeldKeys().get(i)) + "(" + keyboardWitness.getHeldKeys().get(i) + "), ";
				
			}
			
		} catch (IndexOutOfBoundsException e) { }
		
		lines.add("Keys pressed: " + keysPressed);
		
		if (game.save != null) {
			
			lines.add("Player Pos: (" + 
				(double) Math.round(game.save.player.getPosition().X * 10000) / 10000 + ", " + 
				(double) Math.round(game.save.player.getPosition().Y * 10000) / 10000 + ") Facing: " + 
				game.save.player.getFacing());
			
		}
		else lines.add("Player Pos: N/A");
		
		if (game.save != null && game.save.world != null) {
			
			lines.add("Camera Pos: (" + 
				(double) Math.round(game.save.world.worldData.CameraPosition.X * 10000) / 10000 + ", " + 
				(double) Math.round(game.save.world.worldData.CameraPosition.Y * 10000) / 10000 + ")");
			
		}
		else lines.add("CameraPos: N/A");
		
		if (game.save != null && game.save.world != null) {
			
			lines.add("Zoom: " + (double) Math.round(Main.game.save.world.worldData.Zoom * 10000) / 10000);
			
		}
		else lines.add("Zoom: N/A");
		
		FrameData frameData = Main.game.frameData;
		
		g.setColor(Color.white);
		
		g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, frameData.Height / 25)); 
		
		for (int i = 0; i < lines.size(); i++) {
			
			g.drawString(lines.get(i), frameData.Width / 100, (frameData.Height / 25) * (i + 1));
			
		}
		
		//tasks
		
		lines = new ArrayList<String>();
		
		for (String task : tasks.keySet()) {
			
			if (tasks.get(task) != null) lines.add(task + " (" + tasks.get(task) + ")");	
			else lines.add(task);
			
		}
		
		g.drawString("Tasks:", frameData.Width - (frameData.Width / 100) - g.getFontMetrics().stringWidth("Tasks:"), (frameData.Height / 25));
		
		for (int i = 0; i < lines.size(); i++) {
			
			g.drawString(lines.get(i), frameData.Width - (frameData.Width / 100) - g.getFontMetrics().stringWidth(lines.get(i)), (frameData.Height / 25) * (i + 2));
			
		}
		
	}
	
}
