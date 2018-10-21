package com.gameprogmeth.game.world;

import java.util.HashMap;

public enum StoneAndGem {
	
	NORMAL_ROCK1(1,true,"Normal_Rock1"),
	NORMAL_ROCK2(4,true,"Normal_Rock2"),
	NORMAL_ROCK3(7,true,"Normal_Rock3"),
	NORMAL_ROCK4(10,true,"Normal_Rock4"),
	NORMAL_ROCK5(13,true,"Normal_Rock5"),
	NORMAL_ROCK6(16,true,"Normal_Rock6"),
	NORMAL_ROCK7(19,true,"Normal_Rock7"),
	NORMAL_ROCK8(22,true,"Normal_Rock8"),
	ICE_ROCK1(25,true,"Ice_Rock1"),
	ICE_ROCK2(28,true,"Ice_Rock2"),
	ICE_ROCK3(31,true,"Ice_Rock3"),
	ICE_ROCK4(34,true,"Ice_Rock4"),
	LAVA_ROCK1(37,true,"Lava_Rock1"),
	LAVA_ROCK2(40,true,"Lava_Rock2"),
	COPPER_ROCK(43,true,"Copper_Rock"),
	SILVER_ROCK(46,true,"Silver_Rock"),
	GOLD_ROCK(49,true,"Gold_Rock"),
	IRIDIUM_ROCK(52,true,"Iridium_Rock"),
	DIAMOND_ROCK(55,true,"Diamond_Rock"),
	RUBY_ROCK(58,true,"Ruby_Rock"),
	JADE_ROCK(61,true,"Jade_Rock"),
	TOPAS_ROCK(64,true,"Topas_Rock"),
	EMERALD_ROCK(67,true,"Emerald_Rock"),
	Amethyst_ROCK(70,true,"Amethyst_Rock");
	
	
	public static final int TILE_SIZE = 16;
	
	private int id;
	private String name;
	private boolean collidable;
	private static HashMap<Integer,StoneAndGem> stoneMap;
	
	private StoneAndGem(int id, boolean collidable ,String name) {
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
	
	public static StoneAndGem getStoneAndGemById(int id) {
		for(StoneAndGem stone : values()) {
			if(stone.id == id) return stone;
		}
		return null;
	}
	
}
