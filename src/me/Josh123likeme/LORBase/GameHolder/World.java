package me.Josh123likeme.LORBase.GameHolder;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import me.Josh123likeme.LORBase.FrameData;
import me.Josh123likeme.LORBase.Game;
import me.Josh123likeme.LORBase.Main;
import me.Josh123likeme.LORBase.ResourceLoader;
import me.Josh123likeme.LORBase.SettingsHolder;
import me.Josh123likeme.LORBase.WorldData;
import me.Josh123likeme.LORBase.BlockHolder.Chunk;
import me.Josh123likeme.LORBase.BlockHolder.Floor;
import me.Josh123likeme.LORBase.BlockHolder.Wall;
import me.Josh123likeme.LORBase.EntityHolder.Entity;
import me.Josh123likeme.LORBase.EntityHolder.ITEM_ENTITY;
import me.Josh123likeme.LORBase.EntityHolder.PLAYER;
import me.Josh123likeme.LORBase.Generators.Generator;
import me.Josh123likeme.LORBase.Generators.SimpleMaze;
import me.Josh123likeme.LORBase.InputListener.ControlHolder.ButtonType;
import me.Josh123likeme.LORBase.Types.Vector2D;

public class World {
	
	private Save save;
	
	public Chunk[][] chunks;
	public PLAYER player;
	public WorldData worldData;
	
	private boolean inventoryOpen;
	
	ArrayList<Entity> entities = new ArrayList<Entity>();
	
	public World(Save save) {
		
		this.save = save;
		this.player = save.player;
		
		entities.add(player);
		
		inventoryOpen = false;
		
		worldData = new WorldData();
		
		worldData.CameraPosition = player.getPosition();
		worldData.FocusPlayer = true;
		worldData.Zoom = SettingsHolder.zoom;
				
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
		
		for (Entity entity : entities) {
			
			BufferedImage texture = ResourceLoader.rotateImageByDegrees(entity.getTexture(), entity.getFacing());
			
			g.drawImage(texture,
					(int) (entity.getPosition().X * 16 * worldData.Zoom - worldData.CameraPosition.X * 16 * worldData.Zoom) + frameData.Width / 2,
					(int) (entity.getPosition().Y * 16 * worldData.Zoom - worldData.CameraPosition.Y * 16 * worldData.Zoom) + frameData.Height / 2, 
					(int) (entity.getSize() * 16 * worldData.Zoom + 1),
					(int) (entity.getSize() * 16 * worldData.Zoom + 1),
					null);
			
		}
		
		if (inventoryOpen) player.renderInventory(g);
		
	}
	
	public void updateInfrequent() {
		
		for (Entity entity : entities) {
			
			//path find
			
		}
		
	}
	
	public void update() {
		
		updatePlayer();
		
		List<Entity> entitiesToRemove = new ArrayList<Entity>();
		
		for (Entity entity : entities) {
			
			if (entity instanceof ITEM_ENTITY && 
					entity.getDistance(player.getPosition().X, player.getPosition().Y) < 1) {
				
				if (!player.getInventory().isFull() && ((ITEM_ENTITY) entity).canPickup()) {
					
					player.getInventory().addItem(((ITEM_ENTITY) entity).getItem());
					
					entitiesToRemove.add(entity);
					
				}
				
			}		
				
		}
		
		for (Entity entity : entitiesToRemove) {
			
			entities.remove(entity);
			
		}
		
		if (Main.game.keyboardWitness.isButtonTyped(ButtonType.INVENTORY)) {
			
			inventoryOpen = !inventoryOpen;
			
		}
		
		if (inventoryOpen) player.getInventory().updateInventoryUI();
		
	}
	
	
	private void updatePlayer() {
		
		Game game = Main.game;
		
		Vector2D movementVector = new Vector2D(0, 0);
		
		if (game.keyboardWitness.isButtonHeld(ButtonType.MOVE_UP)) movementVector.Y -= 1;
		if (game.keyboardWitness.isButtonHeld(ButtonType.MOVE_LEFT)) movementVector.X -= 1;
		if (game.keyboardWitness.isButtonHeld(ButtonType.MOVE_DOWN)) movementVector.Y += 1;
		if (game.keyboardWitness.isButtonHeld(ButtonType.MOVE_RIGHT)) movementVector.X += 1;

		movementVector.normalise();
		
		movementVector.X = movementVector.X * player.getMovementSpeed() * game.frameData.DeltaFrame;
		movementVector.Y = movementVector.Y * player.getMovementSpeed() * game.frameData.DeltaFrame;
		
		player.moveEntity(new Vector2D(player.getPosition().X + movementVector.X, player.getPosition().Y + movementVector.Y), this);

		if (movementVector.X == 0 && movementVector.Y < 0) player.setFacing(90);
		if (movementVector.X == 0 && movementVector.Y > 0) player.setFacing(270);
		if (movementVector.X > 0 && movementVector.Y == 0) player.setFacing(0);
		if (movementVector.X < 0 && movementVector.Y == 0) player.setFacing(180);
		
		if (worldData.FocusPlayer) {
			
			worldData.CameraPosition.X = player.getPosition().X;
			worldData.CameraPosition.Y = player.getPosition().Y;
			
		}
		
		setFloor((int) player.getPosition().X, (int) player.getPosition().Y, Floor.MOGUS); //TODO for testing texture stitching
		
	}
	
	public void addEntity(Entity entity) {
		
		entities.add(entity);
		
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
