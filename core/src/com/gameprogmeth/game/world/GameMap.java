package com.gameprogmeth.game.world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

public abstract class GameMap {

	public abstract void render(OrthographicCamera camera);
	public abstract void update(float delta);
	public abstract void dispose();
	
	public TileType getTileTypeByLocation(int layer, float x, float y) {
		return this.getTileTypeByCoordinate(layer, (int)(x / TileType.TILE_SIZE) , (int)(y / TileType.TILE_SIZE));
	}
	
	public StoneAndGem getStoneAndGemByLocation(int layer, float x, float y) {
		return this.getStoneAndGemByCoordinate(layer, (int)(x / TileType.TILE_SIZE) , (int)(y / TileType.TILE_SIZE));
	}
	
	public abstract TileType getTileTypeByCoordinate(int layer, int col, int row);
	
	public abstract StoneAndGem getStoneAndGemByCoordinate(int layer, int col, int row);
	
	public abstract int getStoneAndGemHealth(int col, int row);
	
	public abstract int getWidth();
	public abstract int getHeight();
	public abstract int getLayer();
	
	public abstract Vector2 getMainCharacterPosition();
	public void update() {
		
	}
	
	public int changeXToCol(float x) {
		return (int)(x / TileType.TILE_SIZE);
	}
	
	public int changeYToRow(float y) {
		return (int)(y / TileType.TILE_SIZE);
	}
	
}
