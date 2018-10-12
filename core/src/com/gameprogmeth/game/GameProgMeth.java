package com.gameprogmeth.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameProgMeth extends Game {
	
	public static final int WIDTH = 1920;
	public static final int HEIGHT = 1080; 
	
	SpriteBatch batch;

	public void create () {
		batch = new SpriteBatch();
		this.setScreen(new TestCharacter(this));
	}

	public void render () {
		super.render();
	}

}
