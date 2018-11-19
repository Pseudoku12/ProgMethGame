package com.gameprogmeth.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import states.MenuState;

public class GameProgMeth extends Game {
	
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720; 
	
	private SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new MenuState(this));
	}

	@Override
	public void render () {
		super.render();
	}

	public SpriteBatch getBatch() {
		return batch;
	}
	
}
