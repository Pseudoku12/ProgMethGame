package com.gameprogmeth.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.gameprogmeth.game.world.GameMap;
import com.gameprogmeth.game.world.custommap.CustomGameMap;

public class PlayScene implements Screen {

	private GameProgMeth game;
	
	OrthographicCamera cam;
	GameMap gameMap;
	
	public PlayScene(GameProgMeth game) {
		this.game = game;
		
		cam = new OrthographicCamera();
		cam.setToOrtho(false, Gdx.graphics.getWidth()/4, Gdx.graphics.getHeight()/4);
		cam.update();
		
//		gameMap = new TiledGameMap();
		gameMap = new CustomGameMap();
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if(Gdx.input.isTouched()) {
			cam.translate(Gdx.input.getDeltaX(),Gdx.input.getDeltaY());
			cam.update();
		}
		gameMap.render(cam);
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
