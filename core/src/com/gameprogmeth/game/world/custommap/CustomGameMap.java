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

import characters.Character;
import characters.MainCharacter;

public class CustomGameMap extends GameMap {

	private Character character;
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

		character = new MainCharacter(100, 100, 300);

	}

	@Override
	public void render(OrthographicCamera camera) {
		cam = camera;
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		for(int layer = 0; layer < getLayer(); layer++) {
			for(int row = 0; row < getHeight(); row++) {
				for(int col = 0; col < getWidth(); col++) {
					if(layer == 0) {
						TileType type = this.getTileTypeByCoordinate(layer, col, row);
						if(type != null) {
							batch.draw(tiles[(type.getId() - 1) / 7][(type.getId() - 1) % 7], col * TileType.TILE_SIZE, row * TileType.TILE_SIZE);
						}
					}
					else if(layer == 1) {
						StoneAndGem stone = this.getStoneAndGemByCoordinate(layer, col, row);
						if(stone != null) {
							batch.draw(stones[(stone.getId() - 1) / 3][(stone.getId() - 1) % 3], col * TileType.TILE_SIZE, row * TileType.TILE_SIZE);
						}
					}
				}
			}
		}
		batch.draw(character.getAnimation().getKeyFrame(attackAnimationTime, true), character.getPosition().x,
				character.getPosition().y, character.getRenderWidth(), character.getRenderHeight());
		if (character.getRoll() < 8 && character.getRoll() > 3
				&& character.getAnimation().isAnimationFinished(attackAnimationTime)) {
			System.out.println(character.getRoll());
			int temp = character.getRoll();
			switch (temp) {
			case 4:
				character.setRoll(0);
				break;
			case 5:
				character.setRoll(1);
				break;
			case 6:
				character.setRoll(2);
				break;
			case 7:
				character.setRoll(3);
				break;
			default:
				character.setRoll(3);
				break;
			}
		}
		batch.end();
	}

	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
		handleInput();
		character.update(dt);
		stateTime += dt;
		attackAnimationTime += dt;
	}

	protected void handleInput() {
		// TODO Auto-generated method stub
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			character.setVelocity(0, character.getSpeed());
			character.setRoll(3);
		} else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			character.setVelocity(-character.getSpeed(), 0);
			character.setRoll(1);
		} else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			character.setVelocity(0, -character.getSpeed());
			character.setRoll(0);
		} else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			character.setVelocity(character.getSpeed(), 0);
			character.setRoll(2);
		} else {
			character.setVelocity(0, 0);
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && character.getRoll() == 0) {
			character.setVelocity(0, 0);
			character.setRoll(4);
			attackAnimationTime = 0;
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && character.getRoll() == 1) {
			character.setVelocity(0, 0);
			character.setRoll(5);
			attackAnimationTime = 0;
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && character.getRoll() == 2) {
			character.setVelocity(0, 0);
			character.setRoll(6);
			attackAnimationTime = 0;
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && character.getRoll() == 3) {
			character.setVelocity(0, 0);
			character.setRoll(7);
			attackAnimationTime = 0;
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

	public Character getMainCharacter() {
		return character;
	}

	public int changeXToCol(float x) {
		return (int)(x / TileType.TILE_SIZE);
	}
	
	public int changeYToRow(float y) {
		return (int)(y / TileType.TILE_SIZE);
	}
	
	public void setValueToMap(int layer, int col, int row, int val) {
		map[1][getHeight() - row - 1][col] = val;
//		System.out.println("Stone Destroy!!!");
//		System.out.println(map[1][getHeight() - row - 1][col]);
	}
}
