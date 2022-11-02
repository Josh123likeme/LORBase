package me.Josh123likeme.LORBase.UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import me.Josh123likeme.LORBase.Main;
import me.Josh123likeme.LORBase.SettingsHolder;

public class Menu {
	
	private List<Button> buttons = new ArrayList<Button>();
	private List<Element> elements = new ArrayList<Element>();
	
	public void createButton(String id, String text, double x, double y, double sizeX, double sizeY) {
		
		buttons.add(new Button(id, text, null, null, x, y, sizeX, sizeY));
		
	}
	
	public void createButton(String id, BufferedImage image, double x, double y, double sizeX, double sizeY) {
		
		buttons.add(new Button(id, null, image, null, x, y, sizeX, sizeY));
		
	}

	public void createButton(String id, String text, String description, double x, double y, double sizeX, double sizeY) {
	
		buttons.add(new Button(id, text, null, description, x, y, sizeX, sizeY));
	
	}
	
	public void createButton(String id, BufferedImage image, String description, double x, double y, double sizeX, double sizeY) {
	
		buttons.add(new Button(id, null, image, description, x, y, sizeX, sizeY));
	
	}
	
	public void createElement(String id, String text, double x, double y, double sizeX, double sizeY) {
		
		elements.add(new Element(id, text, null, x, y, sizeX, sizeY));
		
	}
	
	public void createElement(String id, BufferedImage image, double x, double y, double sizeX, double sizeY) {
		
		elements.add(new Element(id, null, image, x, y, sizeX, sizeY));
		
	}
	
	public void updateButtonText(String id, String text) {
		
		for (Button button : buttons) {
			
			if (!button.isTextButton()) continue;
			
			if (button.getId().equals(id)) {
				
				button.setText(text);
				
				return;
				
			}
			
		}
		
	}
	
	public void updateButtonImage(String id, BufferedImage image) {
		
		for (Button button : buttons) {
			
			if (button.isTextButton()) continue;
			
			if (button.getId().equals(id)) {
				
				button.setImage(image);
				
				return;
				
			}
			
		}
		
	}
	
	public void updateButtonDescription(String id, String description) {
		
		for (Button button : buttons) {
			
			if (button.getId().equals(id)) {
				
				button.setText(description);
				
				return;
				
			}
			
		}
		
	}
	
	public void updateElementText(String id, String text) {
		
		for (Element element : elements) {
			
			if (!element.isTextElement()) continue;
			
			if (element.getId().equals(id)) {
				
				element.setText(text);
				
				return;
				
			}
			
		}
		
	}
	
	public void updateElementImage(String id, BufferedImage image) {
		
		for (Element element : elements) {
			
			if (element.isTextElement()) continue;
			
			if (element.getId().equals(id)) {
				
				element.setImage(image);
				
				return;
				
			}
			
		}
		
	}
	
	public void render(Graphics g) {
		
		for (Button button : buttons) {
			
			int relX = (int) (Main.game.frameData.Width * button.getX());
			int relY = (int) (Main.game.frameData.Height * button.getY());
			int relWidth = (int) (Main.game.frameData.Width * button.getWidth());
			int relHeight = (int) (Main.game.frameData.Height * button.getHeight());
			
			if (button.isTextButton()) {
				
				g.setColor(new Color(146, 146, 146));
				
				g.fillRect(relX, relY, relWidth, relHeight);
				
				g.setColor(new Color(162, 162, 162));
				
				g.fillRect(relX + (int) (relWidth * button.borderSize),
					relY + (int) (relHeight * button.borderSize),
					relWidth - (int) (2 * relWidth * button.borderSize),
					relHeight - (int) (2 * relHeight * button.borderSize));

				int stringWidth = g.getFontMetrics().stringWidth(button.getText());
				int stringHeight = g.getFont().getSize();
				
				int textHeight = (int) ((double) stringHeight * (double) relWidth / stringWidth);
				
				textHeight *= 0.8;
				
				g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, textHeight)); 
				
				g.setColor(Color.black);
				
				g.drawString(button.getText(),
						(int) (relX + relWidth * 0.5 - g.getFontMetrics().stringWidth(button.getText()) * 0.5),
						(int) (relY + relHeight * 0.5 + textHeight * 0.5));
				
			}
			else {
				
				g.drawImage(button.getImage(), relX, relY, relWidth, relHeight, null);
				
			}	
			
		}
		
		for (Element element : elements) {
			
			int relX = (int) (Main.game.frameData.Width * element.getX());
			int relY = (int) (Main.game.frameData.Height * element.getY());
			int relWidth = (int) (Main.game.frameData.Width * element.getWidth());
			int relHeight = (int) (Main.game.frameData.Height * element.getHeight());
			
			if (element.isTextElement()) {

				g.setColor(new Color(146, 146, 146));
				
				g.fillRect(relX, relY, relWidth, relHeight);
				
				int stringWidth = g.getFontMetrics().stringWidth(element.getText());
				int stringHeight = g.getFont().getSize();
				
				int textHeight = (int) ((double) stringHeight * (double) relWidth / stringWidth);
				
				textHeight *= 0.9;
				
				g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, textHeight)); 
				
				g.setColor(Color.black);
				
				g.drawString(element.getText(),
						(int) (relX + relWidth * 0.5 - g.getFontMetrics().stringWidth(element.getText()) * 0.5),
						(int) (relY + relHeight * 0.5 + textHeight * 0.5));
				
			}
			else {
				
				g.drawImage(element.getImage(), relX, relY, relWidth, relHeight, null);
				
			}	
			
		}
		
		Button button = getButton(getButtonAt(Main.game.mouseWitness.getMouseX(), Main.game.mouseWitness.getMouseY()));
		
		if (button != null && button.hasDescription()) {
			
			int relX = (int) (Main.game.frameData.Width * button.getX());
			int relY = (int) (Main.game.frameData.Height * button.getY());
			int relWidth = (int) (Main.game.frameData.Width * button.getWidth());
			int relHeight = (int) (Main.game.frameData.Height * button.getHeight());
			
			String description = button.getDescription();
			
			int textHeight = (int) (SettingsHolder.descriptionSize * Main.game.frameData.Height);
			
			g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, textHeight)); 
			
			g.setColor(Color.black);
			
			List<String> lines = new ArrayList<String>();
			
			lines.add(new String());
			
			int currentLine = 0;
			
			for (String word : description.split(" ")) {
				
				if (g.getFontMetrics().stringWidth(lines.get(currentLine) + word + " ") < relWidth * 0.8) {
					
					lines.set(currentLine, lines.get(currentLine) + word + " ");
					
				}
				else {
					
					lines.add(word + " ");
					
					currentLine++;
					
				}
				
			}
			
			g.setColor(new Color(146, 146, 146));
			
			g.fillRect(relX, relY, relWidth, relHeight);
			
			g.setColor(Color.black);
			
			for (int i = 0; i < lines.size(); i++) {
				
				g.drawString(lines.get(i), 
						(int) (relX + relWidth * 0.1), 
						(int) (relY + relHeight / 2 + textHeight * (i - lines.size() / 2)));
				
			}
			
		}
		
	}
	
	public String getButtonAt(int x, int y) {
		
		for (Button button : buttons) {
			
			if (x < button.getX() * Main.game.frameData.Width) continue;
			if (y < button.getY() * Main.game.frameData.Height) continue;
			if (x > button.getX() * Main.game.frameData.Width + button.getWidth() * Main.game.frameData.Width) continue;
			if (y > button.getY() * Main.game.frameData.Height + button.getHeight() * Main.game.frameData.Height) continue;
			
			return button.getId();
			
		}
		
		return null;
		
	}
	
	private Button getButton(String id) {
		
		for (Button button : buttons) {
			
			if (button.getId().equals(id)) return button;
			
		}
		
		return null;
		
	}
	
}
