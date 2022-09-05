package me.Josh123likeme.LORBase;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import me.Josh123likeme.LORBase.Types.Cardinal;
import me.Josh123likeme.LORBase.Types.Vector2D;

public class DebugInfo {

	public int FPS;
	public double DeltaFrame;
	
	public boolean IsDragging;
	public List<Integer> HeldKeys;
	
	public Vector2D PlayerPos;
	public Cardinal PlayerFacing;
	public Vector2D CameraPos;
	
	public double GuiScale;
	
	private String task;
	private String progress;
	
	public String getTask() throws Exception {
		
		if (task.equals(null)) throw new Exception("No task currently running");
		
		if (progress.equals(null)) {
			
			return task;
			
		}
		
		return task + " (" + progress + ")";
		
	}
	
	public void setTask(String task) {
		
		this.task = task;
		this.progress = null;
		
	}
	public void setTask(String task, String progress) {
		
		this.task = task;
		this.progress = progress;
		
	}
	public void completeTask() {
		
		this.task = null;
		this.progress = null;
		
	}
	
	public List<String> getDebugInfo() {
		
		List<String> info = new ArrayList<String>();
		
		info.add("FPS: " + FPS + " (Delta Frame: " + DeltaFrame + ")");
		info.add("Is Dragging: " + IsDragging);
		
		String keysPressed = "";
		
		for (int i = 0; i < HeldKeys.size(); i++) {
			
			keysPressed += KeyEvent.getKeyText(HeldKeys.get(i)) + ", ";
			
		}
		
		info.add("Keys pressed: " + keysPressed);
		
		info.add("Player Pos: (" + PlayerPos.X + ", " + PlayerPos.Y + ") Facing: " + PlayerFacing);
		info.add("Camera Pos: (" + CameraPos.X + ", " + CameraPos.Y + ")");
		
		info.add("GUI Scale: " + GuiScale);
		
		return info;
		
	}
	
}
