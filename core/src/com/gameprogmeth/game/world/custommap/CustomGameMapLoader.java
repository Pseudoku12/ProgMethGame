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
		
		findTheWay(originX, originY, 100);
		
		
		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {
				if(chkMap[row][col] == 1) {
					int a = random.nextInt(100);
					if(a <= 45)			mapData.map[0][row][col] = TileType.GROUND_NORMAL.getId();
					else if(a <= 90)	mapData.map[0][row][col] = TileType.GROUND_ROUGH.getId();
					else if(a <= 98)	mapData.map[0][row][col] = TileType.GROUND_CIRCLE.getId();
					else if(a <= 100)	mapData.map[0][row][col] = TileType.GROUND_HOLE.getId();
					
					a = random.nextInt(100);
					if(a <= 7) 			mapData.map[1][row][col] = StoneAndGem.NORMAL_ROCK1.getId();
					else if(a <= 14) 	mapData.map[1][row][col] = StoneAndGem.NORMAL_ROCK2.getId();
					else if(a <= 21) 	mapData.map[1][row][col] = StoneAndGem.NORMAL_ROCK3.getId();
					else if(a <= 28) 	mapData.map[1][row][col] = StoneAndGem.NORMAL_ROCK4.getId();
					else if(a <= 35) 	mapData.map[1][row][col] = StoneAndGem.NORMAL_ROCK5.getId();
					else if(a <= 42) 	mapData.map[1][row][col] = StoneAndGem.NORMAL_ROCK6.getId();
					else if(a <= 45) 	mapData.map[1][row][col] = StoneAndGem.NORMAL_ROCK7.getId();
					else if(a <= 48) 	mapData.map[1][row][col] = StoneAndGem.NORMAL_ROCK8.getId();
					else if(a <= 50) 	mapData.map[1][row][col] = StoneAndGem.COPPER_ROCK.getId();
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
			pqMap.add(pairCo);	
		}
		if(originX+1 < SIZE) {
			int x = originX;
			x++;
			Triple pairCo = new Triple(randomMap[x][originY],x,originY);
			pqMap.add(pairCo);
		}
		if(originY-1 >=0) {
			int y = originY;
			y--;
			Triple pairCo = new Triple(randomMap[originX][y],originX,y);
			pqMap.add(pairCo);
		}
		if(originY+1 < SIZE) {
			int y = originY;
			y++;
			Triple pairCo = new Triple(randomMap[originX][y],originX,y);
			pqMap.add(pairCo);
			
		}
		Triple point = pqMap.poll();
		findTheWay(point.coX, point.coY, number-1);
		
	}		
}
