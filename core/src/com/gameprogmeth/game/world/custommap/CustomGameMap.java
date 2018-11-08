package com.gameprogmeth.game.world.custommap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gameprogmeth.game.world.GameMap;
import com.gameprogmeth.game.world.StoneAndGem;
import com.gameprogmeth.game.world.TileType;

import characters.MainCharacter;

public class CustomGameMap extends GameMap {

	private MainCharacter mainCharacter;
	private float stateTime;
	private float attackAnimationTime;

	String id;
	String name;
	int[][][] map;

	private SpriteBatch batch;
	private TextureRegion[][] tiles;
	private TextureRegion[][] stones;

	private OrthographicCamera cam;

	public CustomGameMap() {

		CustomGameMapData data = CustomGameMapLoader.loadMap("level1", "Begin");

		this.id = data.id;
		this.name = data.name;
		this.map = data.map;

		batch = new SpriteBatch();
		tiles = TextureRegion.split(new Texture("GameProgMeth_Tile.png"), TileType.TILE_SIZE, TileType.TILE_SIZE);
		stones = TextureRegion.split(new Texture("StoneandGem.png"), TileType.TILE_SIZE, TileType.TILE_SIZE);

		mainCharacter = new MainCharacter(100, 100, 300);

	}

	@Override
	public void render(OrthographicCamera camera) {
		cam = camera;
		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		for (int layer = 0; layer < getLayer(); layer++) {
			for (int row = 0; row < getHeight(); row++) {
				for (int col = 0; col < getWidth(); col++) {
					if (layer == 0) {
						TileType type = this.getTileTypeByCoordinate(layer, col, row);
						if (type != null) {
							batch.draw(tiles[(type.getId() - 1) / 7][(type.getId() - 1) % 7], col * TileType.TILE_SIZE,
									row * TileType.TILE_SIZE);
						}
					} else if (layer == 1) {
						StoneAndGem stone = this.getStoneAndGemByCoordinate(layer, col, row);
						if (stone != null) {
							batch.draw(stones[(stone.getId() - 1) / 3][(stone.getId() - 1) % 3],
									col * TileType.TILE_SIZE, row * TileType.TILE_SIZE);
						}
					}
				}
			}
		}
		batch.draw(mainCharacter.getAnimation().getKeyFrame(attackAnimationTime, true), mainCharacter.getPosition().x,
				mainCharacter.getPosition().y, mainCharacter.getRenderWidth(), mainCharacter.getRenderHeight());
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
		// TODO Auto-generated method stub
		handleInput();
		mainCharacter.update(dt);
		stateTime += dt;
		attackAnimationTime += dt;
	}

	protected void handleInput() {
		// TODO Auto-generated method stub
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			mainCharacter.setVelocity(0, mainCharacter.getSpeed());
			mainCharacter.setRoll(3);
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
		if (Gdx.input.justTouched() && mainCharacter.getStamina() > 0) {
			mainCharacter.setVelocity(0, 0);
			attackAnimationTime = 0;
			if(mainCharacter.getRoll() == 0) {
				mainCharacter.setRoll(4);
			}
			else if(mainCharacter.getRoll() == 1) {
				mainCharacter.setRoll(5);
			}
			else if(mainCharacter.getRoll() == 2) {
				mainCharacter.setRoll(6);
			}
			else if(mainCharacter.getRoll() == 3) {
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

	public int changeXToCol(float x) {
		return (int) (x / TileType.TILE_SIZE);
	}

	public int changeYToRow(float y) {
		return (int) (y / TileType.TILE_SIZE);
	}

	public void setValueToMap(int layer, int col, int row, int val) {
		map[1][getHeight() - row - 1][col] = val;
//		System.out.println("Stone Destroy!!!");
//		System.out.println(map[1][getHeight() - row - 1][col]);
	}
}
