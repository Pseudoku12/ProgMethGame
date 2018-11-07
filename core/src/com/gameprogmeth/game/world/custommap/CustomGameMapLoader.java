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
		randomMap = new int[SIZE/4][SIZE/4];
		chkMap = new int[SIZE/4][SIZE/4];
		pqMap = new PriorityQueue<Triple>();
		
		CustomGameMapData mapData = new CustomGameMapData();
		mapData.id = id;
		mapData.name = name;
		mapData.map = new int[2][SIZE][SIZE];
		
		Random random = new Random();

		randomValue();
		
		int originX = 12;
		int originY = 12;
		
		findTheWay(originX, originY, 30);
		
//		checkHole();
//		addSide(200);
		
		for (int row = 0; row < SIZE/4; row++) {
			for (int col = 0; col < SIZE/4; col++) {
				if(chkMap[row][col] == 1) {
					for(int i = 0; i < 16; i++) {
						int rowT = (int)(i/4) + 2*row;
						int colT = (int)(i%4) + 2*col;
						
						if(mapData.map[0][rowT][colT] != 0) continue;
						
						int a = random.nextInt(100);
						if(a <= 45)			mapData.map[0][rowT][colT] = TileType.ICE_NORMAL.getId();
						else if(a <= 97)	mapData.map[0][rowT][colT] = TileType.ICE_ROUGH.getId();
						else if(a <= 99)	mapData.map[0][rowT][colT] = TileType.ICE_CIRCLE.getId();
						else if(a <= 100)	mapData.map[0][rowT][colT] = TileType.ICE_HOLE.getId();
						
						a = random.nextInt(100);
						if(a <= 5) 			mapData.map[1][rowT][colT] = StoneAndGem.ICE_ROCK1.getId();
						else if(a <= 10) 	mapData.map[1][rowT][colT] = StoneAndGem.ICE_ROCK2.getId();
						else if(a <= 15) 	mapData.map[1][rowT][colT] = StoneAndGem.ICE_ROCK3.getId();
						else if(a <= 20) 	mapData.map[1][rowT][colT] = StoneAndGem.ICE_ROCK4.getId();
						else if(a <= 21) 	mapData.map[1][rowT][colT] = StoneAndGem.COPPER_ROCK.getId();
						else if(a <= 22)	mapData.map[1][rowT][colT] = StoneAndGem.SILVER_ROCK.getId();
						else				mapData.map[1][rowT][colT] = 100;
						
//						Algorithm for generate Ice
//						int a = random.nextInt(100);
//						if(a <= 45)			mapData.map[0][rowT][colT] = TileType.ICE_NORMAL.getId();
//						else if(a <= 97)	mapData.map[0][rowT][colT] = TileType.ICE_ROUGH.getId();
//						else if(a <= 99)	mapData.map[0][rowT][colT] = TileType.ICE_CIRCLE.getId();
//						else if(a <= 100)	mapData.map[0][rowT][colT] = TileType.ICE_HOLE.getId();
//						
//						a = random.nextInt(100);
//						if(a <= 5) 			mapData.map[1][rowT][colT] = StoneAndGem.ICE_ROCK1.getId();
//						else if(a <= 10) 	mapData.map[1][rowT][colT] = StoneAndGem.ICE_ROCK2.getId();
//						else if(a <= 15) 	mapData.map[1][rowT][colT] = StoneAndGem.ICE_ROCK3.getId();
//						else if(a <= 20) 	mapData.map[1][rowT][colT] = StoneAndGem.ICE_ROCK4.getId();
//						else if(a <= 21) 	mapData.map[1][rowT][colT] = StoneAndGem.COPPER_ROCK.getId();
//						else if(a <= 22)	mapData.map[1][rowT][colT] = StoneAndGem.SILVER_ROCK.getId();
//						else				mapData.map[1][rowT][colT] = 100;
						
						
//						Algorithm for generate UnderGround
//						int a = random.nextInt(100);
//						if(a <= 14)			mapData.map[0][rowT][colT] = TileType.ROCK_GROUND_BIGNORMAL.getId();
//						else if(a <= 28)	mapData.map[0][rowT][colT] = TileType.ROCK_GROUND_ONEROCK.getId();
//						else if(a <= 42)	mapData.map[0][rowT][colT] = TileType.ROCK_GROUND_TWOROCK.getId();
//						else if(a <= 56)	mapData.map[0][rowT][colT] = TileType.ROCK_GROUND_THREEROCK.getId();
//						else if(a <= 72)	mapData.map[0][rowT][colT] = TileType.ROCK_GROUND_NORMAL.getId();
//						else if(a <= 86)	mapData.map[0][rowT][colT] = TileType.ROCK_GROUND_CIRCLE.getId();
//						else if(a <= 100)	mapData.map[0][rowT][colT] = TileType.ROCK_GROUND_BIGCIRCLE.getId();
//						
//						a = random.nextInt(100);
//						if(a <= 2) 			mapData.map[1][rowT][colT] = StoneAndGem.NORMAL_ROCK1.getId();
//						else if(a <= 4) 	mapData.map[1][rowT][colT] = StoneAndGem.NORMAL_ROCK2.getId();
//						else if(a <= 7) 	mapData.map[1][rowT][colT] = StoneAndGem.NORMAL_ROCK3.getId();
//						else if(a <= 10) 	mapData.map[1][rowT][colT] = StoneAndGem.NORMAL_ROCK4.getId();
//						else if(a <= 12) 	mapData.map[1][rowT][colT] = StoneAndGem.NORMAL_ROCK5.getId();
//						else if(a <= 15) 	mapData.map[1][rowT][colT] = StoneAndGem.NORMAL_ROCK6.getId();
//						else if(a <= 18) 	mapData.map[1][rowT][colT] = StoneAndGem.NORMAL_ROCK7.getId();
//						else if(a <= 21) 	mapData.map[1][rowT][colT] = StoneAndGem.NORMAL_ROCK8.getId();
//						else if(a <= 26) 	mapData.map[1][rowT][colT] = StoneAndGem.COPPER_ROCK.getId();
//						else if(a <= 27)	mapData.map[1][rowT][colT] = StoneAndGem.SILVER_ROCK.getId();
//						else				mapData.map[1][rowT][colT] = 100;
						
						
//						Algorithm for generate Ground
//						int a = random.nextInt(100);
//						if(a <= 45)			mapData.map[0][rowT][colT] = TileType.GROUND_NORMAL.getId();
//						else if(a <= 97)	mapData.map[0][rowT][colT] = TileType.GROUND_ROUGH.getId();
//						else if(a <= 99)	mapData.map[0][rowT][colT] = TileType.GROUND_CIRCLE.getId();
//						else if(a <= 100)	mapData.map[0][rowT][colT] = TileType.GROUND_HOLE.getId();
//						
//						a = random.nextInt(100);
//						if(a <= 3) 			mapData.map[1][rowT][colT] = StoneAndGem.NORMAL_ROCK1.getId();
//						else if(a <= 6) 	mapData.map[1][rowT][colT] = StoneAndGem.NORMAL_ROCK2.getId();
//						else if(a <= 9) 	mapData.map[1][rowT][colT] = StoneAndGem.NORMAL_ROCK3.getId();
//						else if(a <= 12) 	mapData.map[1][rowT][colT] = StoneAndGem.NORMAL_ROCK4.getId();
//						else if(a <= 15) 	mapData.map[1][rowT][colT] = StoneAndGem.NORMAL_ROCK5.getId();
//						else if(a <= 18) 	mapData.map[1][rowT][colT] = StoneAndGem.NORMAL_ROCK6.getId();
//						else if(a <= 21) 	mapData.map[1][rowT][colT] = StoneAndGem.NORMAL_ROCK7.getId();
//						else if(a <= 24) 	mapData.map[1][rowT][colT] = StoneAndGem.NORMAL_ROCK8.getId();
//						else if(a <= 27) 	mapData.map[1][rowT][colT] = StoneAndGem.COPPER_ROCK.getId();
//						else				mapData.map[1][rowT][colT] = 100;
					}
					
				}
			}
		}
		
		return mapData;
	}

//		
//	public static void addSide(int num) {
//		for(int row = 1; row < SIZE - 1; row++) {
//			for(int col = 1; col < SIZE - 1; col++) {
//				if(num == 0) {
//					return ;
//				}
//				if(chkMap[row - 1][col] == 1 && chkMap[row][col] == 0) {
//					chkMap[row][col] = 1;
//					num--;
//				}
//				
//			}
//		}
//		
//	}

//	public static void checkHole() {
//		for(int row = 1; row < SIZE - 1; row++) {
//			for(int col = 1; col < SIZE - 1; col++) {
//				if(((chkMap[row + 1][col] == 1 && chkMap[row][col - 1] == 1 && chkMap[row][col + 1] == 1) || 
//				   (chkMap[row - 1][col] == 1 && chkMap[row][col - 1] == 1 && chkMap[row][col + 1] == 1) ||
//				   (chkMap[row + 1][col] == 1 && chkMap[row - 1][col] == 1 && chkMap[row][col + 1] == 1) ||
//				   (chkMap[row + 1][col] == 1 && chkMap[row - 1][col] == 1 && chkMap[row][col - 1] == 1)) && 
//				    chkMap[row][col] == 0) {
//					chkMap[row][col] = 1;
//				}
//				
//			}
//		}
//		
//	}

	public static void randomValue() {
		Random random = new Random();
		for (int row = 0; row < SIZE/4; row++) {
			for (int col = 0; col < SIZE/4; col++) {
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
