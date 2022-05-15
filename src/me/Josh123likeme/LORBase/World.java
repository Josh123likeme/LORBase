package me.Josh123likeme.LORBase;

import me.Josh123likeme.LORBase.BlockHolder.*;
import me.Josh123likeme.LORBase.EntityHolder.EntityBase;
import me.Josh123likeme.LORBase.EntityHolder.Player;
import me.Josh123likeme.LORBase.Types.Vector2D;

import java.awt.Graphics;
import java.util.ArrayList;

public class World {
	
	Floor[][] floor = new Floor[101][101];
	Wall[][] walls = new Wall[101][101];
	
	ArrayList<EntityBase> entities = new ArrayList<EntityBase>();
	
	public World(Player player) {
		
		entities.add(player);
		
		//for testing \/
		
		Boolean[][] maze = Generators.generateRandomMaze(101, 101);
		
		for (int y = 0; y < floor.length; y++) {
			
			for (int x = 0; x < floor[0].length; x++) {
				
				if (maze[y][x]) walls[y][x] = Wall.LABYRINTH_WALL;
				
				else floor[y][x] = Floor.LABYRINTH_FLOOR;

			}
			
		}
		
	}
	
	public void render(Graphics g) {
		
		FrameData frameData = Main.game.getFrameData();
		
		Vector2D cameraPos = frameData.CameraPosition;
		
		for (int y = 0; y < floor.length; y++) {
			
			for (int x = 0; x < floor[0].length; x++) {
				
				g.drawImage(ResourceLoader.floorTextures.get(floor[y][x]),
						(int) (frameData.Width / 2 - cameraPos.X - x * 16 * frameData.GuiScale),
						(int) (frameData.Height / 2 - cameraPos.Y - y * 16 * frameData.GuiScale), 
						(int) (16 * frameData.GuiScale + 1),
						(int) (16 * frameData.GuiScale + 1),
						null);

			}
			
		}
		
		for (int y = 0; y < floor.length; y++) {
			
			for (int x = 0; x < floor[0].length; x++) {
				
				g.drawImage(ResourceLoader.wallTextures.get(walls[y][x]),
						(int) (frameData.Width / 2 - cameraPos.X - x * 16 * frameData.GuiScale),
						(int) (frameData.Height / 2 - cameraPos.Y - y * 16 * frameData.GuiScale), 
						(int) (16 * frameData.GuiScale + 1),
						(int) (16 * frameData.GuiScale + 1),
						null);

			}
			
		}

		for (EntityBase entity : entities) {
			
			g.drawImage(ResourceLoader.entityTextures.get(entity.type),
					(int) (frameData.Width / 2 - cameraPos.X - entity.getPosition().X * 16 * frameData.GuiScale),
					(int) (frameData.Height / 2 - cameraPos.Y - entity.getPosition().Y * 16 * frameData.GuiScale), 
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
