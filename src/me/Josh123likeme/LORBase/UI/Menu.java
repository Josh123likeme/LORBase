package me.Josh123likeme.LORBase.UI;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Menu {
	
	private List<Button> buttons = new ArrayList<Button>();
	
	public void createButton(String id, String text, int x, int y, int sizeX, int sizeY) {
		
		buttons.add(new Button(id, text, null, null, x, y, sizeX, sizeY));
		
	}
	
	public void createButton(String id, BufferedImage image, int x, int y, int sizeX, int sizeY) {
		
		buttons.add(new Button(id, null, image, null, x, y, sizeX, sizeY));
		
	}

	public void createButton(String id, String text, String description, int x, int y, int sizeX, int sizeY) {
	
		buttons.add(new Button(id, text, null, description, x, y, sizeX, sizeY));
	
	}
	
	public void createButton(String id, BufferedImage image, String description, int x, int y, int sizeX, int sizeY) {
	
		buttons.add(new Button(id, null, image, description, x, y, sizeX, sizeY));
	
	}
	
	public void render(Graphics g) {
		
		for (Button button : buttons) {
			
			if (button.isTextButton()) {
				
				g.setColor(new Color(146, 146, 146));
				
				g.fillRect(button.getX(), button.getY(), button.getWidth(), button.getHeight());
				
				g.setColor(new Color(162, 162, 162));
				
				g.fillRect(button.getX() + (int) (button.getWidth() * button.borderSize),
					button.getY() + (int) (button.getWidth() * button.borderSize),
					button.getWidth() - (int) (2 * button.getWidth() * button.borderSize),
					button.getHeight() - (int) (2 * button.getWidth() * button.borderSize));

				int stringWidth = g.getFontMetrics().stringWidth(button.getText());
				int stringHeight = g.getFont().getSize();
				
				int textHeight = (int) (button.getHeight() * (double) stringHeight / stringWidth);
				
				textHeight *= 0.8;
				
				g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, textHeight)); 
				
				g.setColor(Color.black);
				
				g.drawString(button.getText(),
						(int) (button.getX() + button.getWidth() * 0.5 - g.getFontMetrics().stringWidth(button.getText()) * 0.5),
						(int) (button.getY() + button.getHeight() * 0.5 + textHeight * 0.5));
				
			}
			else {
				
				g.drawImage(button.getImage(), button.getX(), button.getY(), button.getWidth(), button.getHeight(), null);
				
			}	
			
		}
		
	}
	
	public String getButton(int x, int y) {
		
		for (Button button : buttons) {
			
			if (x < button.getX()) continue;
			if (y < button.getY()) continue;
			if (x > button.getX() + button.getWidth()) continue;
			if (y > button.getY() + button.getHeight()) continue;
			
			return button.getId();
			
		}
		
		return null;
		
	}
	
}
