package me.Josh123likeme.LORBase.UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import me.Josh123likeme.LORBase.Main;

public class ProgressBar {

	private Color borderColour;
	private Color barColour;
	
	private double posX;
	private double posY;
	private double width;
	private double height;
	
	public final double borderSize = 0.1d;
	
	public ProgressBar(Color borderColour, Color barColour, double x, double y, double sizeX, double sizeY) {
		
		this.borderColour = borderColour;
		this.barColour = barColour;
		
		this.posX = x;
		this.posY = y;
		this.width = sizeX;
		this.height = sizeY;
		
	}
	
	public void render(Graphics g, double progress, String text) {
		
		int relX = (int) (Main.game.frameData.Width * posX);
		int relY = (int) (Main.game.frameData.Height * posY);
		int relWidth = (int) (Main.game.frameData.Width * width);
		int relHeight = (int) (Main.game.frameData.Height * height);
		
		int borderWidth = (int) (relHeight * borderSize);
		
		g.setColor(borderColour);
		
		g.fillRect(relX, relY, relWidth, relHeight);
		
		if (relWidth >= 0) {
			
			//bar
			g.setColor(Color.black);
			
			g.fillRect(relX + borderWidth, relY + borderWidth, 
					relWidth - 2 * borderWidth, relHeight - 2 * borderWidth);
			
			if (progress >= 0 && progress <= 1) {
				
				g.setColor(barColour);
				
				g.fillRect(relX + borderWidth, relY + borderWidth, 
						(int) ((relWidth - 2 * borderWidth) * progress), relHeight - 2 * borderWidth);
				
			}
			
			//text
			g.setColor(Color.white);
			
			g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, (int) (relHeight * 0.8)));
			
			int textWidth = g.getFontMetrics().stringWidth(text);
			
			Text.writeText(g, text, relX + (relWidth / 2 - textWidth / 2), relY, (int) (relHeight * 0.8));
			
		}
		else {
			
			//bar
			g.setColor(Color.black);
			
			g.fillRect(relX - borderWidth, relY + borderWidth, 
					relWidth + 2 * borderWidth, relHeight - 2 * borderWidth);
			
			if (progress >= 0 && progress <= 1) {
				
				g.setColor(barColour);
				
				g.fillRect(relX - borderWidth, relY + borderWidth, 
						(int) ((relWidth + 2 * borderWidth) * progress), relHeight - 2 * borderWidth);
				
			}
			
			//text
			g.setColor(Color.white);
			
			g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, (int) (relHeight * 0.8)));
			
			int textWidth = g.getFontMetrics().stringWidth(text);
			
			Text.writeText(g, text, relX + (relWidth / 2 - textWidth / 2), relY, (int) (relHeight * 0.8));
			
		}

	}
	
}
