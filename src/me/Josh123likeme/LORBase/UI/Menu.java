package me.Josh123likeme.LORBase.UI;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Menu {
	
	private List<Button> buttons = new ArrayList<Button>();
	
	public void createButton(String text, int x, int y, int sizeX, int sizeY) {
		
		buttons.add(new Button(text, null, null, x, y, sizeX, sizeY));
		
	}
	
	public void createButton(BufferedImage image, int x, int y, int sizeX, int sizeY) {
		
		buttons.add(new Button(null, image, null, x, y, sizeX, sizeY));
		
	}

	public void createButton(String text, String description, int x, int y, int sizeX, int sizeY) {
	
	buttons.add(new Button(text, null, description, x, y, sizeX, sizeY));
	
	}
	
	public void createButton(BufferedImage image, String description, int x, int y, int sizeX, int sizeY) {
	
	buttons.add(new Button(null, image, description, x, y, sizeX, sizeY));
	
	}
	
	public void render(Graphics g) {
		
		for (Button button : buttons) {
			
			g.setColor(new Color(162, 162, 162));
			
			g.fillRect(button.getX(), button.getY(), button.getWidth(), button.getHeight());
			
			if (button.isTextButton()) {
				
				Font oldFont = new Font(g.getFont().getFontName(), Font.PLAIN, g.getFont().getSize());
				
				g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, (button.getHeight()))); 
				
				g.setColor(Color.black);
				
				g.drawString(button.getText(), button.getX(), button.getY() + button.getHeight());
				
			}
			else {
				
				g.drawImage(button.getImage(), button.getX(), button.getY(), button.getWidth(), button.getHeight(), null);
				
			}	
			
		}
		
	}
	
}
