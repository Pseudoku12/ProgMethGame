package com.gameprogmeth.game.world.custommap;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gameprogmeth.game.world.GameMap;
import com.gameprogmeth.game.world.StoneAndGem;
import com.gameprogmeth.game.world.TileType;

public class CustomGameMap extends GameMap{

	String id;
	String name;
	int[][][] map;
	
	private SpriteBatch batch;
	private TextureRegion[][] tiles;
	private TextureRegion[][] stones;
	
	public CustomGameMap() {

		CustomGameMapData data = CustomGameMapLoader.loadMap("level6", "Begin");

		this.id = data.id;
		this.name = data.name;
		this.map = data.map;
		
		batch = new SpriteBatch();
		tiles = TextureRegion.split(new Texture("GameProgMeth_Tile.png"), TileType.TILE_SIZE, TileType.TILE_SIZE);
		stones = TextureRegion.split(new Texture("StoneandGem.png"), TileType.TILE_SIZE, TileType.TILE_SIZE);
	}

	@Override
	public void render(OrthographicCamera camera) {
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
		
		batch.end();
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		batch.dispose();
		
	}
	
	@Override
	public TileType getTileTypeByLocation(int layer, float x, float y) {
		return getTileTypeByCoordinate(layer, (int)(x / TileType.TILE_SIZE), getHeight() - (int)(y / TileType.TILE_SIZE) - 1);
	}

	@Override
	public TileType getTileTypeByCoordinate(int layer, int col, int row) {
		if(col < 0 || col >= getWidth() || row < 0 || row >= getHeight()) {
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
		if(col < 0 || col >= getWidth() || row < 0 || row >= getHeight()) {
			return null;
		}
//		TileTypeMap tileTypeMap = new TileTypeMap();
//		return tileTypeMap.getTileTypeById(map[layer][getHeight() - row - 1][col]);
		return StoneAndGem.getStoneAndGemById(map[layer][getHeight() - row - 1][col]);
	}

}
