package com.gameprogmeth.game.world.custommap;

public class Triple implements Comparable<Triple> {
	public int value;
	public int coX;
	public int coY;
	
	public Triple(int value, int coX, int coY) {
		this.value = value;
		this.coX = coX;
		this.coY = coY;
	}

	@Override
	public int compareTo(Triple arg0) {
		if(value > arg0.value) {
			return 1;
		}
		else if(value == arg0.value) {
			if(coX > arg0.coX) {
				return 1;
			}
			else if(coX == arg0.coX) {
				if(coY > arg0.coY) {
					return 1;
				}
				else if(coY == arg0.coY) {
					return 0;
				}
			}
		}
		return -1;
	}

	@Override
	public String toString() {
		return "Triple [value=" + value + ", coX=" + coX + ", coY=" + coY + "]";
	}
	
	
}
