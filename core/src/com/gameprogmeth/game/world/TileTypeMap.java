package com.gameprogmeth.game.world;

import java.util.HashMap;

public class TileTypeMap {

	private static HashMap<Integer,TileType> tileMap;
	public TileTypeMap() { 
		TileType[] type = TileType.values();	
	}
	
	public static TileType getTileTypeById(int id) {
		return tileMap.get(id);
	}
}
