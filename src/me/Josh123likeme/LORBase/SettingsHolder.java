package me.Josh123likeme.LORBase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class SettingsHolder {

	public static double targetfps;
	public static final double defaultTargetfps = 10000;
	
	public static int blockPixelSize;
	public static final int defaultBlockPixelSize = 16;
	
	public static double zoom;
	public static final double defaultZoom = 5d;
	
	public static double descriptionSize;
	public static final double defaultDescriptionSize = 0.025d;
	
	public static void loadSettings() {
		
		List<String> lines = null;
		
		try {
			
			lines = ResourceLoader.readFromTextFile("data\\settings\\settings.txt");
			
		} catch (IOException e) {};
		
		targetfps = defaultTargetfps;
		blockPixelSize = defaultBlockPixelSize;
		zoom = defaultZoom;
		descriptionSize = defaultDescriptionSize;
		
		if (lines != null) {
			
			for (String line : lines) {
				
				if (line.split("=").length != 2) continue;
				
				String operand = line.split("=")[1];
				
				switch (line.split("=")[0]) {
				
					case "TARGET_FPS":
						targetfps = Double.parseDouble(operand);
						break;
						
					case "BLOCK_PIXEL_SIZE":
						blockPixelSize = Integer.parseInt(operand);
						break;
				
					case "ZOOM":
						zoom = Double.parseDouble(operand);
						break;
						
					case "DESCRPTION_SIZE":
						descriptionSize = Double.parseDouble(operand);
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
		lines.add("DESCRIPTION_SIZE=" + descriptionSize);
		
		ResourceLoader.writeToTextFile("data\\settings\\settings.txt", lines);
		
	}
	
}