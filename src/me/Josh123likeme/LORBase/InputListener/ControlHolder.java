package me.Josh123likeme.LORBase.InputListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.Josh123likeme.LORBase.ResourceLoader;

public abstract class ControlHolder {

	private static List<ButtonType> buttons = new ArrayList<ButtonType>();
	private static List<Integer> keys = new ArrayList<Integer>();
	
	public static ButtonType getButton(int keyCode) {
		
		if (keys.indexOf(keyCode) == -1) throw new IllegalArgumentException("No binding exists for that key");
		
		return buttons.get(keys.indexOf(keyCode));
		
	}
	
	public static int getKey(ButtonType button) {
		
		if (buttons.indexOf(button) == -1) throw new IllegalArgumentException("No binding exists for that key");
		
		return keys.get(buttons.indexOf(button));
		
	}
	
	public static void setBind(ButtonType button, int keyCode) {
		
		keys.set(buttons.indexOf(button), keyCode);
		
	}
	
	public static void loadBinds() {
		
		List<String> lines = null;
		
		try {
			
			lines = ResourceLoader.readFromTextFile("data\\settings\\binds.txt");
			
		} catch (IOException e) {};
		
		for (ButtonType button : ButtonType.values()) {
			
			buttons.add(button);
			keys.add(button.key);
			
		}
		
		if (lines != null) {
			
			for (String line : lines) {
				
				keys.set(buttons.indexOf(Enum.valueOf(ButtonType.class, line.split("=")[0])), Integer.parseInt(line.split("=")[1]));
				
			}
			
		}

		saveBinds();
		
	}
	
	public static void saveBinds() {
		
		List<String> lines = new ArrayList<String>();
		
		for (int i = 0; i < buttons.size(); i++) {
			
			lines.add(buttons.get(i) + "=" + keys.get(i));
			
		}
		
		ResourceLoader.writeToTextFile("data\\settings\\binds.txt", lines);
		
	}
	
	public enum ButtonType {
		
		MOVE_UP(87),
		MOVE_LEFT(65),
		MOVE_DOWN(83),
		MOVE_RIGHT(68),
		PAUSE(27),
		ZOOM_IN(61),
		ZOOM_OUT(45),
		DEBUG_TOGGLE(114),
		
		;
		
		int key;
		
		ButtonType(int key) {
			
			this.key = key;
			
		}
		
	}
	
}
