package com.gameprogmeth.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import states.GameStateManager;
import states.MenuState;
import states.PlayScene;
import states.TestCharacter;

public class GameProgMeth extends Game {
	
	public static final int WIDTH = 1920;
	public static final int HEIGHT = 1080; 
	
	private GameStateManager gsm;
	
	SpriteBatch batch;
	
	public void create () {
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		
//		gsm.push(new PlayScene(gsm));
		gsm.push(new MenuState(gsm));
	}

	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
		
	}
	
	public void dispose() {
		batch.dispose();
	}
}
