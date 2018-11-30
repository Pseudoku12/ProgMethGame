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
	private static int[][] randomMap, chkMap, startAndLadderMap;
	private static PriorityQueue<Triple> pqMap;
	private static int numberOfStone, numberOfGround;
	
	public static CustomGameMapData loadMap(String id, String name) {
		Gdx.files.local("maps/").file().mkdirs();
		FileHandle file = Gdx.files.local("maps/" + id + ".map");
		try {
			if(file.exists()) {
				CustomGameMapData data = json.fromJson(CustomGameMapData.class, file.readString());
				return data;
			}else {
				CustomGameMapData data = generateRandomMap (id, name);
				saveMap(data.id, data.name, data.map);
				return data;
			}
		}
		catch(Exception e) {
			System.out.println("Alert : System has error!!!");
			return new CustomGameMapData();
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
		startAndLadderMap = new int[SIZE][SIZE];
		numberOfStone = 0;
		numberOfGround = 0;
		
		CustomGameMapData mapData = new CustomGameMapData();
		mapData.id = id;
		mapData.name = name;
		mapData.map = new int[3][SIZE][SIZE];
		
		Random random = new Random();

		randomValue();
		
		int originX = 12;
		int originY = 12;
		
		findTheWay(originX, originY, 30);
		
		int level = Integer.parseInt(id.substring(5));
		
		if(level <= 5) {
			mapData.map =  generateGroundMap(mapData.map, random);
		}
		else if(level <= 10) {
			mapData.map =  generateUnderGroundMap(mapData.map, random);
		}
		else if(level <= 15) {
			mapData.map =  generateIceMap(mapData.map, random);
		}
		else if(level <= 20) {
			mapData.map =  generateUnderIceMap(mapData.map, random);
		}
		else if(level <= 25) {
			mapData.map =  generateLavaMap(mapData.map, random);
		}
		else {
			mapData.map =  generateUnderLavaMap(mapData.map, random);
		}
		
		findStartAndLadderPoint(random, mapData.map[2]);
		mapData.map[0] = startAndLadderMap;
		
		return mapData;
	}

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
	
	public static void findStartAndLadderPoint(Random random, int[][] map) {
		int ladder = numberOfStone/4;		//ladder = 1
		int start = numberOfGround/4;		//start = 2
		System.out.println(ladder + " " + numberOfStone);
		System.out.println(start + " " + numberOfGround);
		
		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {
				if(map[row][col] != 100 && map[row][col] != 0 && ladder > -1) {
					if(ladder == 0) {
						startAndLadderMap[row][col] = 1;
//						System.out.println("ladder : " + row + " " + col);
					}
					ladder--;
					
				}
				else if(map[row][col] == 100 && start > -1) {
					if(start == 0) {
						startAndLadderMap[row][col] = 2;
						System.out.println("start : " + row + " " + col);
					}
					start--;
					
				}
				else startAndLadderMap[row][col] = 0;
			}
		}
	}
	
	public static int[][][] generateGroundMap(int[][][] map, Random random){
		numberOfGround = 0;
		numberOfStone = 0;
		for (int row = 0; row < SIZE/4; row++) {
			for (int col = 0; col < SIZE/4; col++) {
				if(chkMap[row][col] == 1) {
					for(int i = 0; i < 16; i++) {
						int rowT = (int)(i/4) + 2*row;
						int colT = (int)(i%4) + 2*col;
						
						if(map[0][rowT][colT] != 0) continue;
						
						int a = random.nextInt(100);
						if(a <= 45)			map[1][rowT][colT] = TileType.GROUND_NORMAL.getId();
						else if(a <= 97)	map[1][rowT][colT] = TileType.GROUND_ROUGH.getId();
						else if(a <= 99)	map[1][rowT][colT] = TileType.GROUND_CIRCLE.getId();
						else if(a <= 100)	map[1][rowT][colT] = TileType.GROUND_HOLE.getId();
						
						a = random.nextInt(100);
						if(a <= 3) 			map[2][rowT][colT] = StoneAndGem.NORMAL_ROCK1.getId();
						else if(a <= 6) 	map[2][rowT][colT] = StoneAndGem.NORMAL_ROCK2.getId();
						else if(a <= 9) 	map[2][rowT][colT] = StoneAndGem.NORMAL_ROCK3.getId();
						else if(a <= 12) 	map[2][rowT][colT] = StoneAndGem.NORMAL_ROCK4.getId();
						else if(a <= 15) 	map[2][rowT][colT] = StoneAndGem.NORMAL_ROCK5.getId();
						else if(a <= 18) 	map[2][rowT][colT] = StoneAndGem.NORMAL_ROCK6.getId();
						else if(a <= 21) 	map[2][rowT][colT] = StoneAndGem.NORMAL_ROCK7.getId();
						else if(a <= 24) 	map[2][rowT][colT] = StoneAndGem.NORMAL_ROCK8.getId();
						else if(a <= 27) 	map[2][rowT][colT] = StoneAndGem.COPPER_ROCK.getId();
						else				map[2][rowT][colT] = 100;
						
						if(map[2][rowT][colT] != 100)		numberOfStone++;
						else				numberOfGround++;

					}
					
				}
			}
		}
		return map;
	}
	
	public static int[][][] generateUnderGroundMap(int[][][] map, Random random) {
		numberOfGround = 0;
		numberOfStone = 0;
		for (int row = 0; row < SIZE/4; row++) {
			for (int col = 0; col < SIZE/4; col++) {
				if(chkMap[row][col] == 1) {
					for(int i = 0; i < 16; i++) {
						int rowT = (int)(i/4) + 2*row;
						int colT = (int)(i%4) + 2*col;
						
						if(map[0][rowT][colT] != 0) continue;
						
						int a = random.nextInt(100);
						if(a <= 14)			map[1][rowT][colT] = TileType.ROCK_GROUND_BIGNORMAL.getId();
						else if(a <= 28)	map[1][rowT][colT] = TileType.ROCK_GROUND_ONEROCK.getId();
						else if(a <= 42)	map[1][rowT][colT] = TileType.ROCK_GROUND_TWOROCK.getId();
						else if(a <= 56)	map[1][rowT][colT] = TileType.ROCK_GROUND_THREEROCK.getId();
						else if(a <= 72)	map[1][rowT][colT] = TileType.ROCK_GROUND_NORMAL.getId();
						else if(a <= 86)	map[1][rowT][colT] = TileType.ROCK_GROUND_CIRCLE.getId();
						else if(a <= 100)	map[1][rowT][colT] = TileType.ROCK_GROUND_BIGCIRCLE.getId();
						
						a = random.nextInt(100);
						if(a <= 2) 			map[2][rowT][colT] = StoneAndGem.NORMAL_ROCK1.getId();
						else if(a <= 4) 	map[2][rowT][colT] = StoneAndGem.NORMAL_ROCK2.getId();
						else if(a <= 7) 	map[2][rowT][colT] = StoneAndGem.NORMAL_ROCK3.getId();
						else if(a <= 10) 	map[2][rowT][colT] = StoneAndGem.NORMAL_ROCK4.getId();
						else if(a <= 12) 	map[2][rowT][colT] = StoneAndGem.NORMAL_ROCK5.getId();
						else if(a <= 15) 	map[2][rowT][colT] = StoneAndGem.NORMAL_ROCK6.getId();
						else if(a <= 18) 	map[2][rowT][colT] = StoneAndGem.NORMAL_ROCK7.getId();
						else if(a <= 21) 	map[2][rowT][colT] = StoneAndGem.NORMAL_ROCK8.getId();
						else if(a <= 26) 	map[2][rowT][colT] = StoneAndGem.COPPER_ROCK.getId();
						else if(a <= 27)	map[2][rowT][colT] = StoneAndGem.SILVER_ROCK.getId();
						else				map[2][rowT][colT] = 100;
						
						if(map[2][rowT][colT] != 100)		numberOfStone++;
						else				numberOfGround++;

					}
					
				}
			}
		}
		return map;
	}
	

	public static int[][][] generateIceMap(int[][][] map, Random random) {
		numberOfGround = 0;
		numberOfStone = 0;
		for (int row = 0; row < SIZE/4; row++) {
			for (int col = 0; col < SIZE/4; col++) {
				if(chkMap[row][col] == 1) {
					for(int i = 0; i < 16; i++) {
						int rowT = (int)(i/4) + 2*row;
						int colT = (int)(i%4) + 2*col;
						
						if(map[0][rowT][colT] != 0) continue;
						
						int a = random.nextInt(100);
						if(a <= 45)			map[1][rowT][colT] = TileType.ICE_NORMAL.getId();
						else if(a <= 97)	map[1][rowT][colT] = TileType.ICE_ROUGH.getId();
						else if(a <= 99)	map[1][rowT][colT] = TileType.ICE_CIRCLE.getId();
						else if(a <= 100)	map[1][rowT][colT] = TileType.ICE_HOLE.getId();
						
						a = random.nextInt(100);
						if(a <= 5) 			map[2][rowT][colT] = StoneAndGem.ICE_ROCK1.getId();
						else if(a <= 10) 	map[2][rowT][colT] = StoneAndGem.ICE_ROCK2.getId();
						else if(a <= 15) 	map[2][rowT][colT] = StoneAndGem.ICE_ROCK3.getId();
						else if(a <= 20) 	map[2][rowT][colT] = StoneAndGem.ICE_ROCK4.getId();
						else if(a <= 21) 	map[2][rowT][colT] = StoneAndGem.COPPER_ROCK.getId();
						else if(a <= 22)	map[2][rowT][colT] = StoneAndGem.SILVER_ROCK.getId();
						else				map[2][rowT][colT] = 100;
						
						if(map[2][rowT][colT] != 100)		numberOfStone++;
						else				numberOfGround++;

					}
					
				}
			}
		}
		return map;
		
	}
	

	public static int[][][] generateUnderIceMap(int[][][] map, Random random) {
		numberOfGround = 0;
		numberOfStone = 0;
		for (int row = 0; row < SIZE/4; row++) {
			for (int col = 0; col < SIZE/4; col++) {
				if(chkMap[row][col] == 1) {
					for(int i = 0; i < 16; i++) {
						int rowT = (int)(i/4) + 2*row;
						int colT = (int)(i%4) + 2*col;
						
						if(map[0][rowT][colT] != 0) continue;
						
						int a = random.nextInt(100);
						if(a <= 14)			map[1][rowT][colT] = TileType.ROCK_ICE_BIGNORMAL.getId();
						else if(a <= 28)	map[1][rowT][colT] = TileType.ROCK_ICE_ONEROCK.getId();
						else if(a <= 42)	map[1][rowT][colT] = TileType.ROCK_ICE_TWOROCK.getId();
						else if(a <= 56)	map[1][rowT][colT] = TileType.ROCK_ICE_THREEROCK.getId();
						else if(a <= 72)	map[1][rowT][colT] = TileType.ROCK_ICE_NORMAL.getId();
						else if(a <= 86)	map[1][rowT][colT] = TileType.ROCK_ICE_CIRCLE.getId();
						else if(a <= 100)	map[1][rowT][colT] = TileType.ROCK_ICE_BIGCIRCLE.getId();
						
						a = random.nextInt(100);
						if(a <= 5) 			map[2][rowT][colT] = StoneAndGem.ICE_ROCK1.getId();
						else if(a <= 10) 	map[2][rowT][colT] = StoneAndGem.ICE_ROCK2.getId();
						else if(a <= 15) 	map[2][rowT][colT] = StoneAndGem.ICE_ROCK3.getId();
						else if(a <= 20) 	map[2][rowT][colT] = StoneAndGem.ICE_ROCK4.getId();
						else if(a <= 21) 	map[2][rowT][colT] = StoneAndGem.COPPER_ROCK.getId();
						else if(a <= 22)	map[2][rowT][colT] = StoneAndGem.SILVER_ROCK.getId();
						else if(a <= 23)	map[2][rowT][colT] = StoneAndGem.GOLD_ROCK.getId();
						else				map[2][rowT][colT] = 100;
						
						if(map[2][rowT][colT] != 100)		numberOfStone++;
						else				numberOfGround++;

					}
					
				}
			}
		}
		return map;

	}
	
	public static int[][][] generateLavaMap(int[][][] map, Random random) {
		numberOfGround = 0;
		numberOfStone = 0;
		for (int row = 0; row < SIZE/4; row++) {
			for (int col = 0; col < SIZE/4; col++) {
				if(chkMap[row][col] == 1) {
					for(int i = 0; i < 16; i++) {
						int rowT = (int)(i/4) + 2*row;
						int colT = (int)(i%4) + 2*col;
						
						if(map[0][rowT][colT] != 0) continue;
						
						int a = random.nextInt(100);
						if(a <= 45)			map[1][rowT][colT] = TileType.VOLCANO_NORMAL.getId();
						else if(a <= 97)	map[1][rowT][colT] = TileType.VOLCANO_ROUGH.getId();
						else if(a <= 99)	map[1][rowT][colT] = TileType.VOLCANO_CIRCLE.getId();
						else if(a <= 100)	map[1][rowT][colT] = TileType.VOLCANO_HOLE.getId();
						
						a = random.nextInt(100);
						if(a <= 2) 			map[2][rowT][colT] = StoneAndGem.LAVA_ROCK1.getId();
						else if(a <= 4) 	map[2][rowT][colT] = StoneAndGem.LAVA_ROCK2.getId();
						else if(a <= 7) 	map[2][rowT][colT] = StoneAndGem.NORMAL_ROCK3.getId();
						else if(a <= 10) 	map[2][rowT][colT] = StoneAndGem.NORMAL_ROCK4.getId();
						else if(a <= 12) 	map[2][rowT][colT] = StoneAndGem.NORMAL_ROCK5.getId();
						else if(a <= 15) 	map[2][rowT][colT] = StoneAndGem.NORMAL_ROCK6.getId();
						else if(a <= 18) 	map[2][rowT][colT] = StoneAndGem.NORMAL_ROCK7.getId();
						else if(a <= 21) 	map[2][rowT][colT] = StoneAndGem.NORMAL_ROCK8.getId();
						else if(a <= 26) 	map[2][rowT][colT] = StoneAndGem.SILVER_ROCK.getId();
						else if(a <= 27)	map[2][rowT][colT] = StoneAndGem.GOLD_ROCK.getId();
						else				map[2][rowT][colT] = 100;
						
						if(map[2][rowT][colT] != 100)		numberOfStone++;
						else				numberOfGround++;

					}
					
				}
			}
		}
		return map;
		
	}

	public static int[][][] generateUnderLavaMap(int[][][] map, Random random) {
		numberOfGround = 0;
		numberOfStone = 0;
		for (int row = 0; row < SIZE/4; row++) {
			for (int col = 0; col < SIZE/4; col++) {
				if(chkMap[row][col] == 1) {
					for(int i = 0; i < 16; i++) {
						int rowT = (int)(i/4) + 2*row;
						int colT = (int)(i%4) + 2*col;
						
						if(map[0][rowT][colT] != 0) continue;
						
						int a = random.nextInt(100);
						if(a <= 14)			map[1][rowT][colT] = TileType.ROCK_VOLCANO_BIGNORMAL.getId();
						else if(a <= 28)	map[1][rowT][colT] = TileType.ROCK_VOLCANO_ONEROCK.getId();
						else if(a <= 42)	map[1][rowT][colT] = TileType.ROCK_VOLCANO_TWOROCK.getId();
						else if(a <= 56)	map[1][rowT][colT] = TileType.ROCK_VOLCANO_THREEROCK.getId();
						else if(a <= 72)	map[1][rowT][colT] = TileType.ROCK_VOLCANO_NORMAL.getId();
						else if(a <= 86)	map[1][rowT][colT] = TileType.ROCK_VOLCANO_CIRCLE.getId();
						else if(a <= 100)	map[1][rowT][colT] = TileType.ROCK_VOLCANO_BIGCIRCLE.getId();
						
						a = random.nextInt(100);
						if(a <= 2) 			map[2][rowT][colT] = StoneAndGem.LAVA_ROCK1.getId();
						else if(a <= 4) 	map[2][rowT][colT] = StoneAndGem.LAVA_ROCK2.getId();
						else if(a <= 7) 	map[2][rowT][colT] = StoneAndGem.NORMAL_ROCK3.getId();
						else if(a <= 10) 	map[2][rowT][colT] = StoneAndGem.NORMAL_ROCK4.getId();
						else if(a <= 12) 	map[2][rowT][colT] = StoneAndGem.NORMAL_ROCK5.getId();
						else if(a <= 15) 	map[2][rowT][colT] = StoneAndGem.NORMAL_ROCK6.getId();
						else if(a <= 18) 	map[2][rowT][colT] = StoneAndGem.NORMAL_ROCK7.getId();
						else if(a <= 21) 	map[2][rowT][colT] = StoneAndGem.NORMAL_ROCK8.getId();
						else if(a <= 26) 	map[2][rowT][colT] = StoneAndGem.SILVER_ROCK.getId();
						else if(a <= 27)	map[2][rowT][colT] = StoneAndGem.GOLD_ROCK.getId();
						else if(a <= 28)	map[2][rowT][colT] = StoneAndGem.IRIDIUM_ROCK.getId();
						else				map[2][rowT][colT] = 100;
						
						if(map[2][rowT][colT] != 100)		numberOfStone++;
						else				numberOfGround++;

					}
					
				}
			}
		}
		return map;

	}

}