package me.Josh123likeme.LORBase;

import me.Josh123likeme.LORBase.BlockHolder.*;
import me.Josh123likeme.LORBase.EntityHolder.EntityBase;
import me.Josh123likeme.LORBase.EntityHolder.Player;
import me.Josh123likeme.LORBase.Generators.SimpleMaze;
import me.Josh123likeme.LORBase.Generators.*;
import me.Josh123likeme.LORBase.Types.Cardinal;
import me.Josh123likeme.LORBase.Types.Vector2D;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class World {
	
	public Chunk[][] chunks;
	
	ArrayList<EntityBase> entities = new ArrayList<EntityBase>();
	
	public World(Player player) {
		
		entities.add(player);
		
		//labyrinth generation
			
		SimpleMaze generator = Generator.createNewRandomMazeGenerator();
		
		generator.generateMaze(101, 101);
		
		Floor[][] floor = generator.getFloor();
		Wall[][] wall = generator.getWall();
		
		chunks = Chunk.chunkify(floor, wall);
		
	}
	
	public void render(Graphics g) {
		
		FrameData frameData = Main.game.getFrameData();
		
		Vector2D cameraPos = frameData.CameraPosition;
		
		int blocksOnScreenX = (int) Math.ceil(frameData.Width / (16 * frameData.GuiScale));
		int BlocksOnScreenY = (int) Math.ceil(frameData.Height / (16 * frameData.GuiScale));
		
		blocksOnScreenX += Chunk.getChunkSize() * 2;
		BlocksOnScreenY += Chunk.getChunkSize() * 2;
		
		int tlx = (int) (Math.floor(frameData.CameraPosition.X) - blocksOnScreenX / 2) / Chunk.getChunkSize();
		int tly = (int) (Math.floor(frameData.CameraPosition.Y) - BlocksOnScreenY / 2) / Chunk.getChunkSize();
		int brx = (int) (Math.ceil(frameData.CameraPosition.X) + blocksOnScreenX / 2) / Chunk.getChunkSize();
		int bry = (int) (Math.ceil(frameData.CameraPosition.Y) + BlocksOnScreenY / 2) / Chunk.getChunkSize();
		
		if (tlx < 0) tlx = 0;
		if (tly < 0) tly = 0;
		if (brx > chunks[0].length - 1) brx = chunks[0].length - 1;
		if (bry > chunks.length - 1) bry = chunks.length - 1;
		
		for (int y = tly; y <= bry; y++) {
			
			for (int x = tlx; x <= brx; x++) {
				
				chunks[y][x].renderChunk(
						(int) (x * 16 * Chunk.getChunkSize() * frameData.GuiScale - frameData.CameraPosition.X * 16 * frameData.GuiScale) + frameData.Width / 2,
						(int) (y * 16 * Chunk.getChunkSize() * frameData.GuiScale - frameData.CameraPosition.Y * 16 * frameData.GuiScale) + frameData.Height / 2,
						(int) (16 * Chunk.getChunkSize() * frameData.GuiScale + 1), g);
				

			}
			
		}
		
		for (EntityBase entity : entities) {
			
			int degrees = 0;
			
			if (entity.getFacing() == Cardinal.EAST) degrees = 90;
			if (entity.getFacing() == Cardinal.SOUTH) degrees = 180;
			if (entity.getFacing() == Cardinal.WEST) degrees = 270;
			
			BufferedImage texture = ResourceLoader.rotateImageByDegrees(entity.type.getTexture(), degrees);
			
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
	
	public Floor getFloor(int x, int y) {
		
		return chunks[(int) y / 16][(int) x / 16].getFloor(x % 16, y % 16);
		
	}
	
	public Wall getWall(int x, int y) {
		
		return chunks[(int) y / 16][(int) x / 16].getWall(x % 16, y % 16);
		
	}
	
	public void setFloor(int x, int y, Floor floor) {
		
		chunks[(int) y / 16][(int) x / 16].setFloor(x % 16, y % 16, floor);
		
	}
	
	public void setWall(int x, int y, Wall wall) {
		
		chunks[(int) y / 16][(int) x / 16].setWall(x % 16, y % 16, wall);
		
	}
	
}
