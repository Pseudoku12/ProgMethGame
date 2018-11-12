package com.gameprogmeth.game.world.custommap;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.gameprogmeth.game.world.GameMap;
import com.gameprogmeth.game.world.StoneAndGem;
import com.gameprogmeth.game.world.TileType;

import characters.Item;
import characters.MainCharacter;

public class CustomGameMap extends GameMap {

	private MainCharacter mainCharacter;
	private ArrayList<Item> itemList;

	private float stateTime;
	private float attackAnimationTime;
	private int level;
	private int rowStart, colStart, levelToNewName, rowDrop, colDrop, typeDrop;
	private boolean isDropValue;
	private KeepingMineral keep;

	String id;
	String name;
	int[][][] map;

	private SpriteBatch batch;
	private TextureRegion[][] tiles;
	private TextureRegion[][] stones;

	private OrthographicCamera cam;

	public CustomGameMap() {
		isDropValue = false;
		level = 1;
		levelToNewName = 5;
		getNameMap();
		CustomGameMapData data = CustomGameMapLoader.loadMap("level" + level, name);

		this.id = data.id;
		this.map = data.map;

		batch = new SpriteBatch();
		tiles = TextureRegion.split(new Texture("GameProgMeth_Tile.png"), TileType.TILE_SIZE, TileType.TILE_SIZE);
		stones = TextureRegion.split(new Texture("Stone_Gem_Ladder.png"), TileType.TILE_SIZE, TileType.TILE_SIZE);

		findStartPoint();

		mainCharacter = new MainCharacter(colStart * 16, rowStart * 16, 50);
		itemList = new ArrayList<Item>();
	}

	@Override
	public void render(OrthographicCamera camera) {
		cam = camera;
		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		for (int layer = 1; layer < getLayer(); layer++) {
			for (int row = 0; row < getHeight(); row++) {
				for (int col = 0; col < getWidth(); col++) {
					if (layer == 1) {
						TileType type = this.getTileTypeByCoordinate(layer, col, row);
						if (type != null) {
							batch.draw(tiles[(type.getId() - 1) / 7][(type.getId() - 1) % 7], col * TileType.TILE_SIZE,
									row * TileType.TILE_SIZE);
						}
					} else if (layer == 2) {
						StoneAndGem stone = this.getStoneAndGemByCoordinate(layer, col, row);
						if (stone != null) {
							batch.draw(stones[(stone.getId() - 1) / 3][(stone.getId() - 1) % 3],
									col * TileType.TILE_SIZE, row * TileType.TILE_SIZE);
						}
					}
				}
			}
		}

//		if(isDropValue) {
//			System.out.println("typeDrop : " + typeDrop);
//			System.out.println((typeDrop - 1) / 3 + " " + (typeDrop - 1) % 3);
//			batch.draw(keep.getRollSpriteSheet(),keep.getPosition().x, keep.getPosition().y);
//
//		}

		batch.draw(mainCharacter.getAnimation().getKeyFrame(attackAnimationTime, true), mainCharacter.getPosition().x,
				mainCharacter.getPosition().y, mainCharacter.getRenderWidth(), mainCharacter.getRenderHeight());

		for (Item item : itemList) {
			if (item != null) {
				batch.draw(item.getTexture(), item.getPosition().x, item.getPosition().y, item.getRenderWidth(),
						item.getRenderHeight());
			}
		}

		if (mainCharacter.getRoll() < 8 && mainCharacter.getRoll() > 3
				&& mainCharacter.getAnimation().isAnimationFinished(attackAnimationTime)) {
			int temp = mainCharacter.getRoll();
			switch (temp) {
			case 4:
				mainCharacter.setRoll(0);
				break;
			case 5:
				mainCharacter.setRoll(1);
				break;
			case 6:
				mainCharacter.setRoll(2);
				break;
			case 7:
				mainCharacter.setRoll(3);
				break;
			default:
				mainCharacter.setRoll(3);
				break;
			}
		}
		batch.end();
	}

	@Override
	public void update(float dt) {
		handleInput();
		mainCharacter.update(dt);
		ArrayList<Integer> markForRemoved = new ArrayList<Integer>();
		for (int i = 0; i < itemList.size(); i++) {
			if (itemList.get(i) != null) {
				itemList.get(i).update(dt);
				if (itemList.get(i).isDestroyed()) {
					markForRemoved.add(i);
				}
			}
		}
		for (int i = markForRemoved.size() - 1; i >= 0; i--) {
			itemList.remove(itemList.get(markForRemoved.get(i)));
			System.out.println(mainCharacter.getScore());
		}

//		if(isDropValue)	{
//			keep.update(dt);
//		}
		stateTime += dt;
		attackAnimationTime += dt;

	}

	protected void handleInput() {
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			mainCharacter.setRoll(3);
			int temp = map[2][(int) ((getHeight() - 2) - (mainCharacter.getPosition().y / 16))][(int) ((getWidth() - 2)
					- (mainCharacter.getPosition().x / 16))];
			if (temp == 0 || temp == 100) {
				System.out.println((int) ((getHeight() - 2) - (mainCharacter.getPosition().y / 16)));
				mainCharacter.setVelocity(0, mainCharacter.getSpeed());
			}
		} else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			mainCharacter.setVelocity(-mainCharacter.getSpeed(), 0);
			mainCharacter.setRoll(1);
		} else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			mainCharacter.setVelocity(0, -mainCharacter.getSpeed());
			mainCharacter.setRoll(0);
		} else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			mainCharacter.setVelocity(mainCharacter.getSpeed(), 0);
			mainCharacter.setRoll(2);
		} else {
			mainCharacter.setVelocity(0, 0);
		}
		if (Gdx.input.justTouched() && mainCharacter.getStamina() > 0
				&& mainCharacter.getAnimation().isAnimationFinished(attackAnimationTime)) {
			mainCharacter.setVelocity(0, 0);
			attackAnimationTime = 0;
			if (mainCharacter.getRoll() == 0) {
				mainCharacter.setRoll(4);
			} else if (mainCharacter.getRoll() == 1) {
				mainCharacter.setRoll(5);
			} else if (mainCharacter.getRoll() == 2) {
				mainCharacter.setRoll(6);
			} else if (mainCharacter.getRoll() == 3) {
				mainCharacter.setRoll(7);
			}
			mainCharacter.setStamina(mainCharacter.getStamina() - 1);
			System.out.println(mainCharacter.getStamina());
		}
	}

	@Override
	public void dispose() {
		batch.dispose();

	}

	@Override
	public TileType getTileTypeByLocation(int layer, float x, float y) {
		return getTileTypeByCoordinate(layer, (int) (x / TileType.TILE_SIZE),
				getHeight() - (int) (y / TileType.TILE_SIZE) - 1);
	}

	@Override
	public TileType getTileTypeByCoordinate(int layer, int col, int row) {
		if (col < 0 || col >= getWidth() || row < 0 || row >= getHeight()) {
			return null;
		}
//		TileTypeMap tileTypeMap = new TileTypeMap();
//		return tileTypeMap.getTileTypeById(map[layer][getHeight() - row - 1][col]);
		return TileType.getTileTypeById(map[layer][getHeight() - row - 1][col]);
	}

	@Override
	public int getWidth() {
		return map[0][0].length;
	}

	@Override
	public int getHeight() {
		return map[0].length;
	}

	@Override
	public int getLayer() {
		return map.length;
	}

	@Override
	public StoneAndGem getStoneAndGemByCoordinate(int layer, int col, int row) {
		// TODO Auto-generated method stub
		if (col < 0 || col >= getWidth() || row < 0 || row >= getHeight()) {
			return null;
		}
//		TileTypeMap tileTypeMap = new TileTypeMap();
//		return tileTypeMap.getTileTypeById(map[layer][getHeight() - row - 1][col]);
		return StoneAndGem.getStoneAndGemById(map[layer][getHeight() - row - 1][col]);
	}

	public MainCharacter getMainmainCharacter() {
		return mainCharacter;
	}

	public void destroyStone(int col, int row, int val) {
		if (map[2][getHeight() - row - 1][col] == StoneAndGem.LADDER_GROUND.getId()
				|| map[2][getHeight() - row - 1][col] == StoneAndGem.LADDER_ICE.getId()
				|| map[2][getHeight() - row - 1][col] == StoneAndGem.LADDER_LAVA.getId()) {
			return;
		}
		map[2][getHeight() - row - 1][col] = val;
//		System.out.println("Stone Destroy!!!");
//		System.out.println(map[2][getHeight() - row - 1][col]);
	}

	public void checkLadder(int col, int row) {
		if (map[0][getHeight() - row - 1][col] == 1) {
			if (name.equals("Ground") || name.equals("UnderGround")) {
				map[2][getHeight() - row - 1][col] = StoneAndGem.LADDER_GROUND.getId();
				System.out.println("Ladder here");
			} else if (name.equals("Ice") || name.equals("UnderIce"))
				map[2][getHeight() - row - 1][col] = StoneAndGem.LADDER_ICE.getId();
			else if (name.equals("Lava") || name.equals("UnderLava"))
				map[2][getHeight() - row - 1][col] = StoneAndGem.LADDER_LAVA.getId();

		} else
			System.out.println("Ladder not here");
	}

	public void findStartPoint() {
		int state = 0;
		for (int row = 0; row < getWidth(); row++) {
			for (int col = 0; col < getHeight(); col++) {
				if (map[0][getHeight() - row - 1][col] == 2) {
					this.rowStart = row;
					this.colStart = col;
					state = 1;
					break;
				}
			}
			if (state == 1) {
				break;
			}
		}
	}

	public void toNextLevel() {

		level += 1;
		getNameMap();
		CustomGameMapData newdata = CustomGameMapLoader.loadMap("level" + level, name);

		this.id = newdata.id;
		this.map = newdata.map;

		findStartPoint();

		mainCharacter = new MainCharacter(colStart * 16, rowStart * 16, 200);

	}

	public void getNameMap() {
		if (level <= levelToNewName)
			name = "Ground";
		else if (level <= levelToNewName * 2)
			name = "UnderGround";
		else if (level <= levelToNewName * 3)
			name = "Ice";
		else if (level <= levelToNewName * 4)
			name = "UnderIce";
		else if (level <= levelToNewName * 5)
			name = "Lava";
		else if (level <= levelToNewName * 6)
			name = "UnderLava";
	}

	public Vector2 getMainCharacterPosition() {
		return mainCharacter.getPosition();
	}

	public void destroyLadder(int col, int row) {
		System.out.println(map[2][getHeight() - row - 1][col]);
		map[2][getHeight() - row - 1][col] = 0;
		System.out.println(map[2][getHeight() - row - 1][col]);
	}

	public void dropValueable(StoneAndGem stone, int col, int rol) {
		typeDrop = 0;
		int id = stone.getId();
		Random random = new Random();
		if (id < StoneAndGem.COPPER_ROCK.getId()) {
			int canDrop = random.nextInt(50);
			if (canDrop == 0)
				typeDrop = StoneAndGem.MINERAL_RAINBOW.getId();
			else if (canDrop <= 5)
				typeDrop = StoneAndGem.MINERAL_BLADE.getId();
			else if (canDrop <= 10)
				typeDrop = StoneAndGem.MINERAL_BOOK.getId();
			else if (canDrop <= 15)
				typeDrop = StoneAndGem.MINERAL_GEAR1.getId();
			else if (canDrop <= 20)
				typeDrop = StoneAndGem.MINERAL_GEAR2.getId();
			else if (canDrop <= 25)
				typeDrop = StoneAndGem.MINERAL_MASK.getId();
			else if (canDrop <= 30)
				typeDrop = StoneAndGem.MINERAL_PAGE.getId();
			else if (canDrop <= 35)
				typeDrop = StoneAndGem.MINERAL_RING.getId();
			else if (canDrop <= 40)
				typeDrop = StoneAndGem.MINERAL_SPOON.getId();
			else if (canDrop <= 45)
				typeDrop = StoneAndGem.MINERAL_STONESLAB.getId();
			else {
				isDropValue = false;
				return;
			}
		} else {
			typeDrop = id + 2;
		}
		rowDrop = rol;
		colDrop = col;
		itemList.add(new Item(colDrop * 16, rowDrop * 16, 100, (typeDrop - 1) / 3, (int) ((typeDrop - 1) / 3),
				(int) ((typeDrop - 1) % 3), mainCharacter));
		isDropValue = true;

	}
}
