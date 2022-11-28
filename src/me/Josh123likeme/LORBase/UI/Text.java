package me.Josh123likeme.LORBase.UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Text {

	public static void writeText(Graphics g, String text, int x, int y, int textHeight) {
		
		g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, textHeight)); 
		
		String[] words = text.split(" ");
		
		String newLine = "";
		
		for (int i = 0; i < words.length; i++) {
			
			String word = words[i];

			//check for colour codes
			if (word.matches("§[0-9a-fA-F]{6}")) {
				
				g.setColor(Color.decode("#" + word.substring(1)));
				
				continue;
				
			}
			//check for escaping colour codes
			else if (word.matches("\\\\§[0-9a-fA-F]{6}")) {
				
				word = word.substring(1);
				
			}
			
			newLine += word + " ";
			
		}
		
		g.drawString(newLine, x, y + textHeight);
		
	}
	
	public static void writeLines(Graphics g, String[] text, int x, int y, int width, int textHeight) {
		
		int currentLine = -1;
		
		g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, textHeight)); 
		
		for (String line : text) {
			
			currentLine++;
			
			String lineToAdd = "";
			
			String[] words = line.split(" ");
			
			for (int i = 0; i < words.length; i++) {
				
				String word = words[i];

				//check for colour codes
				if (word.matches("§[0-9a-fA-F]{6}")) {
					
					g.setColor(Color.decode("#" + word.substring(1)));
					
					continue;
					
				}
				//check for escaping colour codes
				else if (word.matches("\\\\§[0-9a-fA-F]{6}")) {
					
					word = word.substring(1);
					
				}
				
				if (g.getFontMetrics().stringWidth(lineToAdd) + g.getFontMetrics().stringWidth(word) > width) {
					
					g.drawString(lineToAdd, x, y + ((currentLine + 1) * textHeight) + (currentLine * 5));
					
					currentLine++;
					
					lineToAdd = word;
					
				}
				else {
					
					lineToAdd += " " + word;
					
				}
				
				if (i == words.length - 1) {
					
					g.drawString(lineToAdd, x, y + ((currentLine + 1) * textHeight) + (currentLine * 5));
					
				}
				
			}
			
		}
		
	}
	
	public static int predictLines(Graphics g, String[] text, int x, int y, int width, int textHeight) {
		
		int currentLine = -1;
		
		g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, textHeight)); 
		
		for (String line : text) {
			
			currentLine++;
			
			String lineToAdd = "";
			
			String[] words = line.split(" ");
			
			for (int i = 0; i < words.length; i++) {
				
				if (g.getFontMetrics().stringWidth(lineToAdd) + g.getFontMetrics().stringWidth(words[i]) > width) {

					currentLine++;
					
					lineToAdd = words[i];
					
				}
				else {
					
					lineToAdd += " " + words[i];
					
				}
				
			}
			
		}
		
		return currentLine + 1;
		
	}
	
}
