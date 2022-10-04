package me.Josh123likeme.LORBase;

import java.util.Random;

public abstract class TUID {

	private static Random random = new Random();
	
	public static String generateRandomTimeStampedId() {
		
		String timestamp = String.format("%16s", Long.toHexString(System.currentTimeMillis())).replace(' ', '0');
		
		String id = "";
		
		for (int i = 0; i < 16; i++) {
			
			id += Long.toHexString(random.nextInt(16));
			
		}
		
		return timestamp + id;
		
	}
	
}
