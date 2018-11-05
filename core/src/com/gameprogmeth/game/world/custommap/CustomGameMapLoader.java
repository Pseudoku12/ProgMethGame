package com.gameprogmeth.game.world.custommap;

import java.util.PriorityQueue;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.gameprogmeth.game.world.StoneAndGem;
import com.gameprogmeth.game.world.TileType;

public class CustomGameMapLoader {
	
	private static Json json = new Json();
	private static final int SIZE = 100;
	private static int[][] randomMap, chkMap;
	private static PriorityQueue<Triple> pqMap;
	
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
		randomMap = new int[SIZE][SIZE];
		chkMap = new int[SIZE][SIZE];
		pqMap = new PriorityQueue<Triple>();
		
		CustomGameMapData mapData = new CustomGameMapData();
		mapData.id = id;
		mapData.name = name;
		mapData.map = new int[2][SIZE][SIZE];
		
		Random random = new Random();
		randomValue();
		
		int originX = 50;
		int originY = 50;
		
		findTheWay(originX, originY, 3000);
		
		mapData.map[0] = chkMap;
		
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
		
	public static void randomValue() {
		Random random = new Random();
		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {
				randomMap[row][col] = random.nextInt(1000);
				chkMap[row][col] = 0;
			}
		}
	}
	
	public static void findTheWay(int originX, int originY, int number) {
		if(number == 0) {
			return;
		}
		if(chkMap[originX][originY] == 1) {
			Triple point = pqMap.poll();
			findTheWay(point.coX, point.coY, number);
			return;
		}
		chkMap[originX][originY] = 1;

		if(originX-1 >= 0) {
			int x = originX;
			x--;
			Triple pairCo = new Triple(randomMap[x][originY],x,originY);
//			chkMap[originX-1][originY] = 1;
			pqMap.add(pairCo);	
		}
		if(originX+1 < SIZE) {
			int x = originX;
			x++;
			Triple pairCo = new Triple(randomMap[x][originY],x,originY);
//			chkMap[originX+1][originY] = 1;
			pqMap.add(pairCo);
		}
		if(originY-1 >=0) {
			int y = originY;
			y--;
			Triple pairCo = new Triple(randomMap[originX][y],originX,y);
//			chkMap[originX][originY-1] = 1;
			pqMap.add(pairCo);
		}
		if(originY+1 < SIZE) {
			int y = originY;
			y++;
			Triple pairCo = new Triple(randomMap[originX][y],originX,y);
//			chkMap[originX][originY+1] = 1;
			pqMap.add(pairCo);
			
		}
		Triple point = pqMap.poll();
		findTheWay(point.coX, point.coY, number-1);
		
	}
		
}
