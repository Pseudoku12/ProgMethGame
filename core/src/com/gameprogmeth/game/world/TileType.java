package com.gameprogmeth.game.world;

import java.util.HashMap;

public enum TileType {
	
	GROUND_NORMAL(1, false, "Ground_Normal"),
	GROUND_CIRCLE(2, false, "Ground_Circle"),
	GROUND_ROUGH(3, false, "Ground_Rough"),
	GROUND_HOLE(4, false, "Ground_Hole"),
	GROUND_LADDER(5, false, "Ground_Ladder"),
	ROCK_GROUND_CIRCLE(8, false, "Rock_Ground_Circle"),
	ROCK_GROUND_NORMAL(9, false, "Rock_Ground_Normal"),
	ROCK_GROUND_TWOROCK(10, false, "Rock_Ground_TwoRock"),
	ROCK_GROUND_BIGNORMAL(11, false, "Rock_Ground_BigNormal"),
	ROCK_GROUND_THREEROCK(12, false, "Rock_Ground_ThreeRock"),
	ROCK_GROUND_ONEROCK(13, false, "Rock_Ground_OneRock"),
	ROCK_GROUND_BIGCIRCLE(14, false, "Rock_Ground_BigCircle"),
	
	ICE_NORMAL(15, false, "Ice_Normal"),
	ICE_CIRCLE(16, false, "Ice_Circle"),
	ICE_ROUGH(17, false, "Ice_Rough"),
	ICE_HOLE(18, false, "Ice_Hole"),
	ICE_LADDER(19, false, "Ice_Ladder"),
	ROCK_ICE_CIRCLE(22, false, "Rock_Ice_Circle"),
	ROCK_ICE_NORMAL(23, false, "Rock_Ice_Normal"),
	ROCK_ICE_TWOROCK(24, false, "Rock_Ice_TwoRock"),
	ROCK_ICE_BIGNORMAL(25, false, "Rock_Ice_BigNormal"),
	ROCK_ICE_THREEROCK(26, false, "Rock_Ice_ThreeRock"),
	ROCK_ICE_ONEROCK(27, false, "Rock_Ice_OneRock"),
	ROCK_ICE_BIGCIRCLE(28, false, "Rock_Ice_BigCircle"),
	
	VOLCANO_NORMAL(29, false, "Volcano_Normal"),
	VOLCANO_CIRCLE(30, false, "Volcano_Circle"),
	VOLCANO_ROUGH(31, false, "Volcano_Rough"),
	VOLCANO_HOLE(32, false, "Volcano_Hole"),
	VOLCANO_LADDER(33, false, "Volcano_Ladder"),
	ROCK_VOLCANO_CIRCLE(36, false, "Rock_Volcano_Circle"),
	ROCK_VOLCANO_NORMAL(37, false, "Rock_Volcano_Normal"),
	ROCK_VOLCANO_TWOROCK(38, false, "Rock_Volcano_TwoRock"),
	ROCK_VOLCANO_BIGNORMAL(39, false, "Rock_Volcano_BigNormal"),
	ROCK_VOLCANO_THREEROCK(40, false, "Rock_Volcano_ThreeRock"),
	ROCK_VOLCANO_ONEROCK(41, false, "Rock_Volcano_OneRock"),
	ROCK_VOLCANO_BIGCIRCLE(42, false, "Rock_Volcano_BigCircle");

	public static final int TILE_SIZE = 16;
	
	private int id;
	private String name;
	private boolean collidable;
	private static HashMap<Integer,TileType> tileMap;
	
	private TileType(int id, boolean collidable ,String name) {
		this.id = id;
		this.collidable = collidable;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public boolean isCollidable() {
		return collidable;
	}
	
	static {
		for(TileType tileType : TileType.values()) {
			tileMap.put(tileType.getId(), tileType);
		}
	}
	
	public static TileType getTileTypeById(int id) {
		return tileMap.get(id);
	}
}
