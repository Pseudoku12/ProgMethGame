package com.gameprogmeth.game;

import com.badlogic.gdx.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameProgMeth extends Game {
	
	public static final int WIDTH = 1920;
	public static final int HEIGHT = 1080; 
	
	SpriteBatch batch;
	
	public void create () {
		batch = new SpriteBatch();

		this.setScreen(new PlayScene(this));
	}

	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();
		
	}
	
	public void dispose() {
		batch.dispose();
	}
}
