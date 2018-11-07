package com.gameprogmeth.game.world;

import java.util.HashMap;

public enum StoneAndGem {
	
	NORMAL_ROCK1(1,true,"Normal_Rock1",2),
	NORMAL_ROCK2(4,true,"Normal_Rock2",5),
	NORMAL_ROCK3(7,true,"Normal_Rock3",8),
	NORMAL_ROCK4(10,true,"Normal_Rock4",11),
	NORMAL_ROCK5(13,true,"Normal_Rock5",14),
	NORMAL_ROCK6(16,true,"Normal_Rock6",17),
	NORMAL_ROCK7(19,true,"Normal_Rock7",20),
	NORMAL_ROCK8(22,true,"Normal_Rock8",23),
	ICE_ROCK1(25,true,"Ice_Rock1",26),
	ICE_ROCK2(28,true,"Ice_Rock2",29),
	ICE_ROCK3(31,true,"Ice_Rock3",32),
	ICE_ROCK4(34,true,"Ice_Rock4",35),
	LAVA_ROCK1(37,true,"Lava_Rock1",38),
	LAVA_ROCK2(40,true,"Lava_Rock2",41),
	COPPER_ROCK(43,true,"Copper_Rock",2),
	SILVER_ROCK(46,true,"Silver_Rock",56),
	GOLD_ROCK(49,true,"Gold_Rock",65),
	IRIDIUM_ROCK(52,true,"Iridium_Rock",23),
	DIAMOND_ROCK(55,true,"Diamond_Rock",56),
	RUBY_ROCK(58,true,"Ruby_Rock",59),
	JADE_ROCK(61,true,"Jade_Rock",62),
	TOPAS_ROCK(64,true,"Topas_Rock",65),
	EMERALD_ROCK(67,true,"Emerald_Rock",68),
	AMETHYST_ROCK(70,true,"Amethyst_Rock",71),
	
	DESTROY_NORMAL_ROCK1(2,true,"Destroy_Normal_Rock1",100),
	DESTROY_NORMAL_ROCK2(5,true,"Destroy_Normal_Rock2",100),
	DESTROY_NORMAL_ROCK3(8,true,"Destroy_Normal_Rock3",100),
	DESTROY_NORMAL_ROCK4(11,true,"Destroy_Normal_Rock4",100),
	DESTROY_NORMAL_ROCK5(14,true,"Destroy_Normal_Rock5",100),
	DESTROY_NORMAL_ROCK6(17,true,"Destroy_Normal_Rock6",100),
	DESTROY_NORMAL_ROCK7(20,true,"Destroy_Normal_Rock7",100),
	DESTROY_NORMAL_ROCK8(23,true,"Destroy_Normal_Rock8",100),
	DESTROY_ICE_ROCK1(26,true,"Destroy_Ice_Rock1",100),
	DESTROY_ICE_ROCK2(29,true,"Destroy_Ice_Rock2",100),
	DESTROY_ICE_ROCK3(32,true,"Destroy_Ice_Rock3",100),
	DESTROY_ICE_ROCK4(35,true,"Destroy_Ice_Rock4",100),
	DESTROY_LAVA_ROCK1(38,true,"Destroy_Lava_Rock1",100),
	DESTROY_LAVA_ROCK2(41,true,"Destroy_Lava_Rock2",100),
	DESTROY_DIAMOND_ROCK(56,true,"Destroy_Diamond_Rock",100),
	DESTROY_RUBY_ROCK(59,true,"Destroy_Ruby_Rock",100),
	DESTROY_JADE_ROCK(62,true,"Destroy_Jade_Rock",100),
	DESTROY_TOPAS_ROCK(65,true,"Destroy_Topas_Rock",100),
	DESTROY_EMERALD_ROCK(68,true,"Destroy_Emerald_Rock",100),
	DESTROY_AMETHYST_ROCK(71,true,"Destroy_Amethyst_Rock",100);
	
	
	public static final int TILE_SIZE = 16;
	
	private int id;
	private String name;
	private boolean collidable;
	private int destroy;
	private static HashMap<Integer,StoneAndGem> stoneMap;
	 
	
	private StoneAndGem(int id, boolean collidable , String name, int destroy) {
		this.id = id;
		this.collidable = collidable;
		this.name = name;
		this.destroy = destroy;
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
	
	public int getDestroy() {
		return destroy;
	}

	public static StoneAndGem getStoneAndGemById(int id) {
		for(StoneAndGem stone : values()) {
			if(stone.id == id) return stone;
		}
		return null;
	}
	
}
