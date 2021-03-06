package me.Josh123likeme.LORBase;

import me.Josh123likeme.LORBase.BlockHolder.*;
import me.Josh123likeme.LORBase.EntityHolder.EntityBase;
import me.Josh123likeme.LORBase.EntityHolder.Player;
import me.Josh123likeme.LORBase.Types.Direction;
import me.Josh123likeme.LORBase.Types.Vector2D;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class World {
	
	public Floor[][] floor = new Floor[101][101];
	public Wall[][] wall = new Wall[101][101];
	
	ArrayList<EntityBase> entities = new ArrayList<EntityBase>();
	
	public World(Player player) {
		
		entities.add(player);
		
		//for testing \/
		
		Boolean[][] maze = Generators.generateRandomMaze(101, 101);
		
		for (int y = 0; y < floor.length; y++) {
			
			for (int x = 0; x < floor[0].length; x++) {
				
				if (maze[y][x]) wall[y][x] = Wall.LABYRINTH_WALL;
				
				else floor[y][x] = Floor.LABYRINTH_FLOOR;

			}
			
		}
		
		// /\
		
	}
	
	public void render(Graphics g) {
		
		FrameData frameData = Main.game.getFrameData();
		
		Vector2D cameraPos = frameData.CameraPosition;
		
		int BlocksOnScreenX = (int) Math.ceil(frameData.Width / (16 * frameData.GuiScale));
		int BlocksOnScreenY = (int) Math.ceil(frameData.Height / (16 * frameData.GuiScale));
		
		BlocksOnScreenX++;
		BlocksOnScreenY++;
		
		int tlx = (int) Math.floor(frameData.CameraPosition.X) - BlocksOnScreenX / 2;
		int tly = (int) Math.floor(frameData.CameraPosition.Y) - BlocksOnScreenY / 2;
		int brx = (int) Math.ceil(frameData.CameraPosition.X) + BlocksOnScreenX / 2;
		int bry = (int) Math.ceil(frameData.CameraPosition.Y) + BlocksOnScreenY / 2;
		
		if (tlx < 0) tlx = 0;
		if (tly < 0) tly = 0;
		if (brx > floor[0].length - 1) brx = floor[0].length - 1;
		if (bry > floor.length - 1) bry = floor.length - 1;
		
		for (int y = tly; y < bry; y++) {
			
			for (int x = tlx; x < brx; x++) {
				
				g.drawImage(ResourceLoader.getTexture(floor[y][x]),
						(int) (x * 16 * frameData.GuiScale - frameData.CameraPosition.X * 16 * frameData.GuiScale) + frameData.Width / 2,
						(int) (y * 16 * frameData.GuiScale - frameData.CameraPosition.Y * 16 * frameData.GuiScale) + frameData.Height / 2, 
						(int) (16 * frameData.GuiScale + 1),
						(int) (16 * frameData.GuiScale + 1),
						null);

			}
			
		}
		
		for (int y = tly; y < bry; y++) {
			
			for (int x = tlx; x < brx; x++) {
				
				g.drawImage(ResourceLoader.getTexture(wall[y][x]),
						(int) (x * 16 * frameData.GuiScale - frameData.CameraPosition.X * 16 * frameData.GuiScale) + frameData.Width / 2,
						(int) (y * 16 * frameData.GuiScale - frameData.CameraPosition.Y * 16 * frameData.GuiScale) + frameData.Height / 2, 
						(int) (16 * frameData.GuiScale + 1),
						(int) (16 * frameData.GuiScale + 1),
						null);

			}
			
		}
		
		g.drawImage(ResourceLoader.getTexture(Floor.MOGUS),
				(int) (1 * 16 * frameData.GuiScale - frameData.CameraPosition.X * 16 * frameData.GuiScale) + frameData.Width / 2,
				(int) (1 * 16 * frameData.GuiScale - frameData.CameraPosition.Y * 16 * frameData.GuiScale) + frameData.Height / 2, 
				(int) (16 * frameData.GuiScale + 1),
				(int) (16 * frameData.GuiScale + 1),
				null);
		
		for (EntityBase entity : entities) {
			
			int degrees = 0;
			
			if (entity.getFacing() == Direction.EAST) degrees = 90;
			if (entity.getFacing() == Direction.SOUTH) degrees = 180;
			if (entity.getFacing() == Direction.WEST) degrees = 270;
			
			BufferedImage texture = ResourceLoader.rotateImageByDegrees(ResourceLoader.getTexture(entity.type), degrees);
			
			g.drawImage(texture,
					(int) (entity.getPosition().X * 16 * frameData.GuiScale - frameData.CameraPosition.X * 16 * frameData.GuiScale) + frameData.Width / 2,
					(int) (entity.getPosition().Y * 16 * frameData.GuiScale - frameData.CameraPosition.Y * 16 * frameData.GuiScale) + frameData.Height / 2, 
					(int) (16 * frameData.GuiScale + 1),
					(int) (16 * frameData.GuiScale + 1),
					null);
			
		}
		
	}
	
	public void updateInfrequent() {
		
		for (EntityBase entity : entities) {
			
		}
		
	}
	
}
