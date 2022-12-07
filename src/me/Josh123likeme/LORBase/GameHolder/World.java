package me.Josh123likeme.LORBase.GameHolder;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.Josh123likeme.LORBase.FrameData;
import me.Josh123likeme.LORBase.Game;
import me.Josh123likeme.LORBase.Main;
import me.Josh123likeme.LORBase.ResourceLoader;
import me.Josh123likeme.LORBase.SettingsHolder;
import me.Josh123likeme.LORBase.WorldData;
import me.Josh123likeme.LORBase.BlockHolder.Floor;
import me.Josh123likeme.LORBase.BlockHolder.Wall;
import me.Josh123likeme.LORBase.EntityHolder.*;
import me.Josh123likeme.LORBase.Generators.CaveMaze;
import me.Josh123likeme.LORBase.Generators.Generator;
import me.Josh123likeme.LORBase.InputListener.MouseWitness;
import me.Josh123likeme.LORBase.InputListener.ControlHolder.ButtonType;
import me.Josh123likeme.LORBase.ParticleHolder.*;
import me.Josh123likeme.LORBase.Types.Vector2D;
import me.Josh123likeme.LORBase.UI.ProgressBar;

public class World {
	
	private static Random random = new Random();
	
	@SuppressWarnings("unused")
	private Save save;
	
	public Chunk[][] chunks;
	public PLAYER player;
	public WorldData worldData;
	
	private boolean inventoryOpen = false;
	
	private ProgressBar healthBar;
	private ProgressBar manaBar;
	
	ArrayList<Entity> entities = new ArrayList<Entity>();
	ArrayList<Entity> entityBuffer = new ArrayList<Entity>();
	ArrayList<Particle> particles = new ArrayList<Particle>();
	ArrayList<Particle> particleBuffer = new ArrayList<Particle>();
	
	public World(Save save, int level) {
		
		this.save = save;
		this.player = save.player;
		
		worldData = new WorldData();
		
		worldData.CameraPosition = player.getPosition();
		worldData.FocusPlayer = true;
		worldData.Zoom = SettingsHolder.zoom;

		healthBar = new ProgressBar(new Color(121, 13, 17), new Color(205, 24, 31), 
				0.2, 0.85, 0.29, 0.05);
		
		manaBar = new ProgressBar(new Color(24, 30, 121), new Color(45, 54, 200), 
				0.8, 0.85, -0.29, 0.05);
		
		CaveMaze generator = Generator.createNewCaveMaze();
		
		Main.game.debugInfo.addTask("Generating World"); //loading info
		
		chunks = generator.generateMaze(level);
		
		Main.game.debugInfo.removeTask("Generating World"); //loading info
		
		List<Vector2D> spawnLocations = new ArrayList<Vector2D>();
		
		for (int y = 0; y < chunks.length * Chunk.getChunkSize(); y++) {
			
			Main.game.debugInfo.addOrUpdateTask("Finding Spawn Locations", 
					Math.round((double) y / (chunks.length * Chunk.getChunkSize()) * 100) + "%"); //loading info
			
			for (int x = 0; x < chunks[0].length * Chunk.getChunkSize(); x++) {
				
				if (getWall(x, y) == Wall.AIR) spawnLocations.add(new Vector2D(x, y));
				
			}
			
		}
		
		Main.game.debugInfo.removeTask("Finding Spawn Locations"); //loading info
		
		player.setPosition(spawnLocations.get(random.nextInt(spawnLocations.size())));
		
		entities.add(player);
		
		//add zombies
		int numberOfZombies = 100;
		
		for (int i = 0; i < numberOfZombies; i++) {
			
			Main.game.debugInfo.addOrUpdateTask("Spawning Zombies", 
					Math.round((double) i / numberOfZombies * 100) + "%"); //loading info
			
			entities.add(new ZOMBIE(this, spawnLocations.get(random.nextInt(spawnLocations.size())), 0, level));
			
		}
		
		Main.game.debugInfo.removeTask("Spawning Zombies");
		
		for (int y = 0; y < chunks.length * Chunk.getChunkSize(); y++) {
			
			Main.game.debugInfo.addOrUpdateTask("Placing Gargoyles", 
					Math.round((double) y / (chunks.length * Chunk.getChunkSize()) * 100) + "%"); //loading info
			
			for (int x = 0; x < chunks[0].length * Chunk.getChunkSize(); x++) {
				
				if (getWall(x, y) == Wall.STATUE_PODIUM) {
					
					entities.add(new GARGOYLE(this, new Vector2D(x + 0.1, y + 0.1), 0, level));
					
				}
				
			}
			
		}
		
		Main.game.debugInfo.removeTask("Placing Gargoyles");
			
	}
	
	public void render(Graphics g) {
		
		FrameData frameData = Main.game.frameData;
		
		g.drawImage(ResourceLoader.getTexture(Floor.MOGUS), 1000, 1000, 500, 500, null);
		
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
		
		for (Particle particle : particles) {
			
			g.drawImage(particle.getTexture(),
					(int) (particle.getPosition().X * 16 * worldData.Zoom - worldData.CameraPosition.X * 16 * worldData.Zoom) + frameData.Width / 2,
					(int) (particle.getPosition().Y * 16 * worldData.Zoom - worldData.CameraPosition.Y * 16 * worldData.Zoom) + frameData.Height / 2, 
					(int) (particle.getSize() * (particle.getTexture().getWidth() / particle.getTexture().getHeight()) * 16 * worldData.Zoom + 1),
					(int) (particle.getSize() * 16 * worldData.Zoom + 1),
					null);
			
		}
		
		if (inventoryOpen) player.getInventory().render(g);
		
		//render stat bars
		healthBar.render(g, player.getHealth() / player.getMaxHealth(),
				Math.round(player.getHealth()) + " / " + Math.round(player.getMaxHealth()));
		manaBar.render(g, player.getMana() / player.getMaxMana(), 
				Math.round(player.getMana()) + " / " + Math.round(player.getMaxMana()));
		
	}
	
	public void updateInfrequent() {
		
	}
	
	public void update() {
		
		//check win
		boolean hasGargoyles = false;
		
		for (Entity entity : entities) {
			
			if (entity instanceof GARGOYLE) {
				
				hasGargoyles = true;
				break;
				
			}
			
		}
		
		if (!hasGargoyles) {
			
			player.kill();
			
		}
		
		//player updates
		updatePlayer();
		
		//attack
		MouseWitness mouseWitness = Main.game.mouseWitness;
		FrameData frameData = Main.game.frameData;
		
		Vector2D mouseWorldPos = new Vector2D();
		
		mouseWorldPos.X = (mouseWitness.getMouseX() - frameData.Width / 2 + worldData.CameraPosition.X * 16 * worldData.Zoom) / (16 * worldData.Zoom);
		mouseWorldPos.Y = (mouseWitness.getMouseY() - frameData.Height / 2 + worldData.CameraPosition.Y * 16 * worldData.Zoom) / (16 * worldData.Zoom);
		
		Main.game.debugInfo.addOrUpdateTask("Mouse world pos: ", mouseWorldPos.X + ", " + mouseWorldPos.Y);
		
		if (mouseWitness.isLeftClicked()) {
			
			for (Entity entity : entities) {
						
				if (entity instanceof PLAYER) continue;
				
				if (entity instanceof IHealthy) {
					
					//check to see if attacking outside of range
					if (player.getPosition().distanceTo(entity.getPosition()) > 5) continue;
					
					if (mouseWorldPos.distanceTo(entity.getPosition()) < 1) {
						
						((IHealthy) entity).damage(10);
						
					}
					
				}
					
			}
			
		}
		
		if (mouseWitness.isRightClicked()) {
			
			player.setPosition(mouseWorldPos);
			
		}
		
		//entity elimination
		List<Entity> entitiesToRemove = new ArrayList<Entity>();
		
		for (Entity entity : entities) {

			if (entity instanceof IHealthy) {
				
				if (((IHealthy) entity).getHealth() <= 0) {
					
					((IHealthy) entity).kill();
					
					entitiesToRemove.add(entity);
					
				}
				
			}
			
			if (entity instanceof ITEM_ENTITY && 
					entity.getPosition().distanceTo(player.getPosition().X, player.getPosition().Y) < 1) {
				
				if (!player.getInventory().isFull() && ((ITEM_ENTITY) entity).canPickup()) {
					
					player.getInventory().addItem(((ITEM_ENTITY) entity).getItem());
					
					entitiesToRemove.add(entity);
					
				}
				
			}		
				
		}
		
		for (Entity entity : entitiesToRemove) {
			
			entities.remove(entity);
			
		}
		
		//particle elimination
		List<Particle> particlesToRemove = new ArrayList<Particle>();
		
		for (Particle particle : particles) {
			
			if (!particle.isAlive()) particlesToRemove.add(particle);
				
		}
		
		for (Particle particle : particlesToRemove) {
			
			particles.remove(particle);
			
		}
		
		//entity updates
		updateEntities();
		
		//ui updates
		if (Main.game.keyboardWitness.isButtonTyped(ButtonType.INVENTORY)) {
			
			inventoryOpen = !inventoryOpen;
			
		}
		
		if (inventoryOpen) player.getInventory().updateInventoryUI();
		
		//buffer pushing
		
		entities.addAll(entityBuffer);
		entityBuffer.clear();
		
		particles.addAll(particleBuffer);
		particleBuffer.clear();
		
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
		
		Vector2D origin = new Vector2D();
		
		double angle = origin.directionTo(movementVector);
		
		player.setFacing(angle);
		
		if (worldData.FocusPlayer) {
			
			worldData.CameraPosition.X = player.getPosition().X;
			worldData.CameraPosition.Y = player.getPosition().Y;
			
		}
	}
	
	private void updateEntities() {
		
		for (Entity entity : entities) {
			
			entity.update(this);
			
		}
		
	}
	
	public void addEntity(Entity entity) {
		
		entityBuffer.add(entity);
		
	}
	
	public void addParticle(Particle particle) {
		
		particleBuffer.add(particle);
		
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
	
	public int getWidth() {
		
		return chunks[0].length * Chunk.getChunkSize();
		
	}
	
	public int getHeight() {
		
		return chunks.length * Chunk.getChunkSize();
		
	}
	
}
