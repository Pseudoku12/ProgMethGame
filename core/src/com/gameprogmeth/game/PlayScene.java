package com.gameprogmeth.game;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.gameprogmeth.game.world.GameMap;
import com.gameprogmeth.game.world.StoneAndGem;
import com.gameprogmeth.game.world.custommap.CustomGameMap;

import states.GameStateManager;
import states.State;

public class PlayScene extends State implements Screen {
	
	OrthographicCamera cam;
	GameMap gameMap;
	
	public PlayScene(GameStateManager gsm) {
		super(gsm);
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

	@Override
	protected void handleInput() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
		gameMap.update();
	}

	@Override
	public void render(SpriteBatch sb) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		if(Gdx.input.isTouched()) {
			cam.translate(Gdx.input.getDeltaX(),Gdx.input.getDeltaY());
			cam.update();
		}
		if(Gdx.input.justTouched()) {
			final Vector3 pos = cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
			StoneAndGem stone = gameMap.getStoneAndGemByLocation(1, pos.x, pos.y);
			
			
			
			if(stone != null) {
				int destroy = stone.getDestroy();
				((CustomGameMap)gameMap).setValueToMap(1,((CustomGameMap)gameMap).changeXToCol(pos.x), ((CustomGameMap)gameMap).changeYToRow(pos.y), destroy);
//				System.out.println(stone.getDestroy());
				Timer.schedule(new Task() {
					public void run() {
						((CustomGameMap)gameMap).setValueToMap(1,((CustomGameMap)gameMap).changeXToCol(pos.x), ((CustomGameMap)gameMap).changeYToRow(pos.y), 100);
					}
				},1);
			}
			
		}
		
		gameMap.render(cam);
		
		
	}

}
