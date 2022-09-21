package me.Josh123likeme.LORBase;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.Josh123likeme.LORBase.Types.Cardinal;
import me.Josh123likeme.LORBase.Types.Vector2D;
import me.Josh123likeme.LORBase.Game.GameState;;

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
	
	public void updateTask(String task, String progress) {
		
		if (!tasks.keySet().contains(task)) throw new IllegalArgumentException("There isn't a task running with that name");
		
		tasks.put(task, progress);
		
	}
	
	public void removeTask(String task) {
		
		tasks.remove(task);
		
	}
	
	public List<String> getDebugInfo() {
		
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
		
		return lines;
		
	}
	
	public List<String> getTaskInfo(){
		
		List<String> lines = new ArrayList<String>();
		
		for (String task : tasks.keySet()) {
			
			if (tasks.get(task) != null) lines.add(task + " (" + tasks.get(task) + ")");	
			else lines.add(task);
			
		}
		
		return lines;
		
	}
	
}
