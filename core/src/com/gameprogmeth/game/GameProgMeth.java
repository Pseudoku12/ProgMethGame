package com.gameprogmeth.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import states.GameOverScene;
import states.MenuState;
import states.PlayScene;

public class GameProgMeth extends Game {
	
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720; 
	public static int score = 0;
	
	private SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
//		setMenuScene();
		setGameOverScene(100);
	}

	@Override
	public void render () {
		super.render();
	}
	
	public void setPlayScene() {
		score = 0;
		setScreen(new PlayScene(this));
	}

	public void setMenuScene() {
		setScreen(new MenuState(this));
	}
	
	public void setGameOverScene(int score) {
		this.score = score;
		setScreen(new GameOverScene(this));
	}
	
	public SpriteBatch getBatch() {
		return batch;
	}
	
}
