package me.Josh123likeme.LORBase;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import me.Josh123likeme.LORBase.BlockHolder.Chunk;
import me.Josh123likeme.LORBase.BlockHolder.Floor;
import me.Josh123likeme.LORBase.BlockHolder.Wall;
import me.Josh123likeme.LORBase.EntityHolder.EntityBase;
import me.Josh123likeme.LORBase.EntityHolder.Player;
import me.Josh123likeme.LORBase.Generators.Generator;
import me.Josh123likeme.LORBase.Generators.SimpleMaze;
import me.Josh123likeme.LORBase.InputListener.ControlHolder.ButtonType;
import me.Josh123likeme.LORBase.Types.Cardinal;
import me.Josh123likeme.LORBase.Types.Vector2D;

public class World {
	
	public Chunk[][] chunks;
	public Player player;
	public WorldData worldData;
	
	public boolean gamePaused;
	
	ArrayList<EntityBase> entities = new ArrayList<EntityBase>();
	
	public World() {
		
		player = new Player(new Vector2D(10, 10), Cardinal.NORTH);
		
		entities.add(player);
		
		worldData = new WorldData();
		
		worldData.CameraPosition = player.getPosition();
		worldData.Zoom = SettingsHolder.zoom;
		
		gamePaused = false;
				
		//labyrinth generation
			
		SimpleMaze generator = Generator.createNewRandomMazeGenerator();
		
		generator.generateMaze(101, 101);
		
		Floor[][] floor = generator.getFloor();
		Wall[][] wall = generator.getWall();
		
		chunks = Chunk.chunkify(floor, wall);
		
	}
	
	public void render(Graphics g) {
		
		FrameData frameData = Main.game.getFrameData();
		
		int blocksOnScreenX = (int) Math.ceil(frameData.Width / (16 * worldData.Zoom));
		int BlocksOnScreenY = (int) Math.ceil(frameData.Height / (16 * worldData.Zoom));
		
		blocksOnScreenX += Chunk.getChunkSize() * 2;
		BlocksOnScreenY += Chunk.getChunkSize() * 2;
		
		int tlx = (int) (Math.floor(worldData.CameraPosition.X) - blocksOnScreenX / 2) / Chunk.getChunkSize();
		int tly = (int) (Math.floor(worldData.CameraPosition.Y) - BlocksOnScreenY / 2) / Chunk.getChunkSize();
		int brx = (int) (Math.ceil(worldData.CameraPosition.X) + blocksOnScreenX / 2) / Chunk.getChunkSize();
		int bry = (int) (Math.ceil(worldData.CameraPosition.Y) + BlocksOnScreenY / 2) / Chunk.getChunkSize();
		
		if (tlx < 0) tlx = 0;
		if (tly < 0) tly = 0;
		if (brx > chunks[0].length - 1) brx = chunks[0].length - 1;
		if (bry > chunks.length - 1) bry = chunks.length - 1;
		
		for (int y = tly; y <= bry; y++) {
			
			for (int x = tlx; x <= brx; x++) {
				
				chunks[y][x].renderChunk(
						(int) (x * 16 * Chunk.getChunkSize() * worldData.Zoom - worldData.CameraPosition.X * 16 * worldData.Zoom) + frameData.Width / 2,
						(int) (y * 16 * Chunk.getChunkSize() * worldData.Zoom - worldData.CameraPosition.Y * 16 * worldData.Zoom) + frameData.Height / 2,
						(int) (16 * Chunk.getChunkSize() * worldData.Zoom + 1), g);
				

			}
			
		}
		
		for (EntityBase entity : entities) {
			
			int degrees = 0;
			
			if (entity.getFacing() == Cardinal.EAST) degrees = 90;
			if (entity.getFacing() == Cardinal.SOUTH) degrees = 180;
			if (entity.getFacing() == Cardinal.WEST) degrees = 270;
			
			BufferedImage texture = ResourceLoader.rotateImageByDegrees(entity.type.getTexture(), degrees);
			
			g.drawImage(texture,
					(int) (entity.getPosition().X * 16 * worldData.Zoom - worldData.CameraPosition.X * 16 * worldData.Zoom) + frameData.Width / 2,
					(int) (entity.getPosition().Y * 16 * worldData.Zoom - worldData.CameraPosition.Y * 16 * worldData.Zoom) + frameData.Height / 2, 
					(int) (16 * worldData.Zoom + 1),
					(int) (16 * worldData.Zoom + 1),
					null);
			
		}
		
	}
	
	
	public void updateInfrequent() {
		
		for (EntityBase entity : entities) {
			
		}
		
	}
	
	public void updateWorldData() {
		
		Game game = Main.game;
		
		worldData.CameraPosition = player.getPosition();
		game.debugInfo.CameraPos = worldData.CameraPosition;
		
		game.debugInfo.Zoom = worldData.Zoom;
		
	}
	
	public void updatePlayer() {
		
		Game game = Main.game;
		
		Vector2D movementVector = new Vector2D(0, 0);
		
		if (game.keyboardWitness.isButtonHeld(ButtonType.MOVE_UP)) movementVector.Y -= 1;
		if (game.keyboardWitness.isButtonHeld(ButtonType.MOVE_LEFT)) movementVector.X -= 1;
		if (game.keyboardWitness.isButtonHeld(ButtonType.MOVE_DOWN)) movementVector.Y += 1;
		if (game.keyboardWitness.isButtonHeld(ButtonType.MOVE_RIGHT)) movementVector.X += 1;

		movementVector.normalise();
		
		movementVector.X = movementVector.X * player.getMovementSpeed() * game.frameData.DeltaFrame;
		movementVector.Y = movementVector.Y * player.getMovementSpeed() * game.frameData.DeltaFrame;
		
		player.moveEntity(new Vector2D(player.getPosition().X + movementVector.X, player.getPosition().Y + movementVector.Y));

		if (movementVector.X == 0 && movementVector.Y < 0) player.setFacing(Cardinal.NORTH);
		if (movementVector.X == 0 && movementVector.Y > 0) player.setFacing(Cardinal.SOUTH);
		if (movementVector.X > 0 && movementVector.Y == 0) player.setFacing(Cardinal.EAST);
		if (movementVector.X < 0 && movementVector.Y == 0) player.setFacing(Cardinal.WEST);
		
		game.debugInfo.PlayerPos = player.getPosition();
		game.debugInfo.PlayerFacing = player.getFacing();
		
		setFloor((int) player.getPosition().X, (int) player.getPosition().Y, Floor.MOGUS); //TODO remove
		
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
