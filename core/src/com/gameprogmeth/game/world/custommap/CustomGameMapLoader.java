package com.gameprogmeth.game.world.custommap;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.gameprogmeth.game.world.StoneAndGem;
import com.gameprogmeth.game.world.TileType;

public class CustomGameMapLoader {
	
	private static Json json = new Json();
	private static final int SIZE = 100;
	
	public static CustomGameMapData loadMap(String id, String name) {
		Gdx.files.local("maps/").file().mkdirs();
		FileHandle file = Gdx.files.local("maps/" + id + ".map");
		if(file.exists()) {
			CustomGameMapData data = json.fromJson(CustomGameMapData.class, file.readString());
			return data;
		}else {
			CustomGameMapData data = generateRandomMap (id, name);
			saveMap(data.id, data.name, data.map);
			return data;
		}
	}
	
	public static void saveMap (String id, String name, int[][][] map) {
		CustomGameMapData data = new CustomGameMapData();
		data.id = id;
		data.name = name;
		data.map = map;
		
		Gdx.files.local("maps/").file().mkdirs();
		FileHandle file = Gdx.files.local("maps/" + id + ".map");
		file.writeString(json.prettyPrint(data), false);
	}
	
	public static CustomGameMapData generateRandomMap (String id, String name) {
		CustomGameMapData mapData = new CustomGameMapData();
		mapData.id = id;
		mapData.name = name;
		mapData.map = new int[2][SIZE][SIZE];
		
		Random random = new Random();
		int originX = random.nextInt(99);
		int originY = random.nextInt(99);
		
		randomBase(mapData, 5000, originX, originY);
		
		
		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {
				if(mapData.map[0][row][col] == 1) {
					int tmp = random.nextInt(100);
					if(0 <= tmp && tmp < 55) mapData.map[0][row][col] = TileType.GROUND_NORMAL.getId();
					if(55 <= tmp && tmp < 75) mapData.map[0][row][col] = TileType.GROUND_ROUGH.getId();
					if(75 <= tmp && tmp < 90) mapData.map[0][row][col] = TileType.GROUND_CIRCLE.getId();
					if(95 <= tmp && tmp <= 100) mapData.map[0][row][col] = TileType.GROUND_HOLE.getId();
					
					tmp = random.nextInt(100);
					if (52 < tmp && tmp <= 58) {
						mapData.map[1][row][col] = StoneAndGem.NORMAL_ROCK1.getId();
					} else if (58 < tmp && tmp <= 64) {
						mapData.map[1][row][col] = StoneAndGem.NORMAL_ROCK2.getId();
					} else if (64 < tmp && tmp <= 70) {
						mapData.map[1][row][col] = StoneAndGem.NORMAL_ROCK3.getId();
					} else if (70 < tmp && tmp <= 76) {
						mapData.map[1][row][col] = StoneAndGem.NORMAL_ROCK4.getId();
					} else if (76 < tmp && tmp <= 82) {
						mapData.map[1][row][col] = StoneAndGem.NORMAL_ROCK5.getId();
					} else if (82 < tmp && tmp <= 88) {
						mapData.map[1][row][col] = StoneAndGem.NORMAL_ROCK6.getId();
					} else if (88 < tmp && tmp <= 94) {
						mapData.map[1][row][col] = StoneAndGem.NORMAL_ROCK7.getId();
					} else if (94 < tmp && tmp <= 100) {
						mapData.map[1][row][col] = StoneAndGem.NORMAL_ROCK8.getId();
					}
				}
				
			}
		}
		
		return mapData;
	}
	
//	public static void randomMap(CustomGameMapData mapData, int num){
//		if(num == 0) return;
//		Random random = new Random();
//		
//		int originX = random.nextInt(99);
//		int originY = random.nextInt(99);
//		randomDungeon(mapData, num, originX, originY);
//	}
	
//	public static void randomDungeon(CustomGameMapData mapData, int num, int x, int y) {
//		if(num == 0) return;
//		mapData.map[0][x][y] = 1;
//		num--;
//		if(x-1 >= 0) randomDungeon(mapData, num, x-1, y);
//		if(y-1 >= 0) randomDungeon(mapData, num, x, y-1);
//		if(x+1 < SIZE) randomDungeon(mapData, num, x+1, y);
//		if(y+1 < SIZE) randomDungeon(mapData, num, x, y+1);
//	}	
		
		
		
		
		
		
		
	public static void randomBase(CustomGameMapData mapData, int num, int x, int y) {
		if(num == 0) {
			return;
		}
		if(x - 2 < 0) {
			randomBase(mapData, num, 2, y);
			return;
		} 
		if(x + 2 >= SIZE) {
			randomBase(mapData, num, SIZE-3, y);
			return;
		}
		if(y - 1 < 0) {
			randomBase(mapData, num, x, 1);
			return;
		}
		if(y + 1 >= SIZE) {
			randomBase(mapData, num, x, SIZE-2);
			return;
		}
		Random random = new Random();
		mapData.map[0][x][y] = 1;
		mapData.map[0][x-1][y] = 1;
		mapData.map[0][x][y-1] = 1;
		mapData.map[0][x+1][y] = 1;
		mapData.map[0][x][y+1] = 1;
		num-=5;
		int d = random.nextInt(3);
		if(d == 0) {
			randomBase(mapData, num, x-1, y);
		}
		else if(d == 1) {
			randomBase(mapData, num, x, y-1);
		}
		else if(d == 2) {
			randomBase(mapData, num, x+1, y);
		}
		else if(d == 3) {
			randomBase(mapData, num, x, y+1);
		}
	}		
	
		
}
