package me.Josh123likeme.LORBase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class SettingsHolder {

	static int targetfps;
	static final int defaultTargetfps = 60;
	
	static int blockPixelSize;
	static final int defaultBlockPixelSize = 16;
	
	static double zoom;
	static final double defaultZoom = 5d;
	
	public static void loadSettings() {
		
		List<String> lines = null;
		
		try {
			
			lines = ResourceLoader.readFromTextFile("data\\settings\\settings.txt");
			
		} catch (IOException e) {};
		
		targetfps = defaultTargetfps;
		blockPixelSize = defaultBlockPixelSize;
		zoom = defaultZoom;
		
		if (lines != null) {
			
			for (String line : lines) {
				
				switch (line.split("=")[0]) {
				
					case "TARGET_FPS":
						targetfps = Integer.parseInt(line.split("=")[1]);
						break;
						
					case "BLOCK_PIXEL_SIZE":
						blockPixelSize = Integer.parseInt(line.split("=")[1]);
						break;
				
					case "ZOOM":
						zoom = Double.parseDouble(line.split("=")[1]);
						break;
						
				}
				
			}
			
		}

		saveSettings();
		
	}
	
	public static void saveSettings() {
		
		List<String> lines = new ArrayList<String>();
		
		lines.add("TARGET_FPS=" + targetfps);
		lines.add("BLOCK_PIXEL_SIZE=" + blockPixelSize);
		lines.add("ZOOM=" + zoom);
		
		ResourceLoader.writeToTextFile("data\\settings\\settings.txt", lines);
		
	}
	
}