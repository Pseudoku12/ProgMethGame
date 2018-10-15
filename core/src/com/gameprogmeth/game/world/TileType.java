package com.gameprogmeth.game.world;

import java.util.HashMap;

public enum TileType {
	
	GROUND_NORMAL(1, false, "Ground_Normal"),
	GROUND_CIRCLE(2, false, "Ground_Circle"),
	GROUND_ROUGH(3, false, "Ground_Rough"),
	GROUND_HOLE(4, false, "Ground_Hole"),
	GROUND_LADDER(5, false, "Ground_Ladder"),
	ROCK_GROUND_CIRCLE(6, false, "Rock_Ground_Circle"),
	ROCK_GROUND_NORMAL(7, false, "Rock_Ground_Normal"),
	ROCK_GROUND_TWOROCK(8, false, "Rock_Ground_TwoRock"),
	ROCK_GROUND_BIGNORMAL(9, false, "Rock_Ground_BigNormal"),
	ROCK_GROUND_THREEROCK(10, false, "Rock_Ground_ThreeRock"),
	ROCK_GROUND_ONEROCK(11, false, "Rock_Ground_OneRock"),
	ROCK_GROUND_BIGCIRCLE(12, false, "Rock_Ground_BigCircle"),
	
	ICE_NORMAL(51, false, "Ice_Normal"),
	ICE_CIRCLE(52, false, "Ice_Circle"),
	ICE_ROUGH(53, false, "Ice_Rough"),
	ICE_HOLE(54, false, "Ice_Hole"),
	ICE_LADDER(55, false, "Ice_Ladder"),
	ROCK_ICE_CIRCLE(56, false, "Rock_Ice_Circle"),
	ROCK_ICE_NORMAL(57, false, "Rock_Ice_Normal"),
	ROCK_ICE_TWOROCK(58, false, "Rock_Ice_TwoRock"),
	ROCK_ICE_BIGNORMAL(59, false, "Rock_Ice_BigNormal"),
	ROCK_ICE_THREEROCK(60, false, "Rock_Ice_ThreeRock"),
	ROCK_ICE_ONEROCK(61, false, "Rock_Ice_OneRock"),
	ROCK_ICE_BIGCIRCLE(62, false, "Rock_Ice_BigCircle"),
	
	VOLCANO_NORMAL(101, false, "Volcano_Normal"),
	VOLCANO_CIRCLE(102, false, "Volcano_Circle"),
	VOLCANO_ROUGH(103, false, "Volcano_Rough"),
	VOLCANO_HOLE(104, false, "Volcano_Hole"),
	VOLCANO_LADDER(105, false, "Volcano_Ladder"),
	ROCK_VOLCANO_CIRCLE(106, false, "Rock_Volcano_Circle"),
	ROCK_VOLCANO_NORMAL(107, false, "Rock_Volcano_Normal"),
	ROCK_VOLCANO_TWOROCK(108, false, "Rock_Volcano_TwoRock"),
	ROCK_VOLCANO_BIGNORMAL(109, false, "Rock_Volcano_BigNormal"),
	ROCK_VOLCANO_THREEROCK(110, false, "Rock_Volcano_ThreeRock"),
	ROCK_VOLCANO_ONEROCK(111, false, "Rock_Volcano_OneRock"),
	ROCK_VOLCANO_BIGCIRCLE(112, false, "Rock_Volcano_BigCircle");

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
