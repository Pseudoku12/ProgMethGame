package com.gameprogmeth.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import states.GameOverScene;
import states.HallOfFameScene;
import states.MenuState;
import states.PlayScene;

public class GameProgMeth extends Game {
	
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720; 
	public static int score = 0;
	public static String name;
	
	private SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setMenuScene();
	}
	
	public void setPlayScene() {
		score = 0;
		setScreen(new PlayScene(this));
	}

	public void setMenuScene() {
		setScreen(new MenuState(this));
	}
	
	public void setGameOverScene(int score) {
		this.score += score;
		setScreen(new GameOverScene(this));
	}
	
	public void setHallOfFameSceneFromMenuScene() {
		setScreen(new HallOfFameScene(this));
	}
	
	public void setHallOfFameSceneFromGameOverScene(String name) {
		this.name = name;
		setScreen(new HallOfFameScene(this, name, score));
	}
	
	public SpriteBatch getBatch() {
		return batch;
	}
	
}
