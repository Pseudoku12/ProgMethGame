package com.gameprogmeth.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gameprogmeth.game.world.MapNormal;
import com.gameprogmeth.game.world.TiledGameMap;

public class GameProgMeth extends Game {
	
	public static final int WIDTH = 1920;
	public static final int HEIGHT = 1080; 
	
	SpriteBatch batch;
	OrthographicCamera cam;
	MapNormal gameMap;
	
	public void create () {
		batch = new SpriteBatch();
		
		cam = new OrthographicCamera();
		cam.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.update();
		//this.setScreen(new TestCharacter(this));
	
		gameMap = new TiledGameMap();
	}

	public void render () {
		//super.render();
		
		gameMap.render(cam);
	}
	
	public void dispose() {
		batch.dispose();
	}
}
