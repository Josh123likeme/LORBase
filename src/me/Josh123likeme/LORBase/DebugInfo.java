package me.Josh123likeme.LORBase;

import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.Josh123likeme.LORBase.Types.Cardinal;
import me.Josh123likeme.LORBase.Types.Vector2D;
import me.Josh123likeme.LORBase.Game.GameState;

public class DebugInfo {

	public GameState GameState;
	
	public int FPS;
	public double DeltaFrame;
	
	public boolean IsDragging;
	public List<Integer> HeldKeys;
	
	public Vector2D PlayerPos;
	public Cardinal PlayerFacing;
	public Vector2D CameraPos;
	
	public double Zoom;
	
	private HashMap<String, String> tasks = new HashMap<String, String>();
	
	public void addTask(String task) {
		
		if (tasks.keySet().contains(task)) throw new IllegalArgumentException("There is already a task running with that name");
		
		tasks.put(task, null);
		
	}
	
	public void addTask(String task, String progress) {
		
		if (tasks.keySet().contains(task)) throw new IllegalArgumentException("There is already a task running with that name");
		
		tasks.put(task, progress);
		
	}
	
	public void render(Graphics g) {
		
		//debug info
		
		List<String> lines = new ArrayList<String>();
		
		lines.add("Game State: " + GameState);
		lines.add("FPS: " + FPS + " (Delta Frame: " + DeltaFrame + ")");
		lines.add("Is Dragging: " + IsDragging);
		
		String keysPressed = "";
		
		for (int i = 0; i < HeldKeys.size(); i++) {
			
			keysPressed += KeyEvent.getKeyText(HeldKeys.get(i)) + ", ";
			
		}
		
		lines.add("Keys pressed: " + keysPressed);
		
		if (PlayerPos != null) lines.add("Player Pos: (" + PlayerPos.X + ", " + PlayerPos.Y + ") Facing: " + PlayerFacing);
		else lines.add("Player Pos: N/A");
		
		if (CameraPos != null) lines.add("Camera Pos: (" + CameraPos.X + ", " + CameraPos.Y + ")");
		else lines.add("CameraPos: N/A");
		
		lines.add("Zoom: " + Zoom);
		
		FrameData frameData = Main.game.getFrameData();
		
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
