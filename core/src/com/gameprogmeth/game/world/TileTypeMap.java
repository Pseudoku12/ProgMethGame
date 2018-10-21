package com.gameprogmeth.game.world;

import java.util.HashMap;

public class TileTypeMap {

	private static HashMap<Integer,TileType> tileMap;
	public TileTypeMap() { 
		TileType[] type = TileType.values();
		for(TileType tileType : type) {
			TileType temp = TileType.values()[tileType.getId()];
			System.out.println(temp);
			tileMap.put(tileType.getId(),tileType.valueOf(temp.toString()));
		}
	}
	
	public static TileType getTileTypeById(int id) {
		return tileMap.get(id);
	}
}