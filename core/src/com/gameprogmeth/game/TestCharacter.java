package com.gameprogmeth.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TestCharacter implements Screen {

	public static final float GHOST_ANIMATION_SPEED = 0.5f;
	public static final float Charater_WIDTH = 120;
	public static final float Charater_HEIGHT = 80;
	public static final int GHOST_WIDTH_PIXEL = 16;
	public static final int GHOST_HEIGHT_PIXEL = 22;
	
	private float x;
	private float y;
	private float stateTime;
	
	private Animation<TextureRegion>[] character;
	private int roll;
	private GameProgMeth game;
	
	public TestCharacter(GameProgMeth game) {
		this.game = game;
		
		x = 100;
		y = 100;
		
		character = new Animation[4];
		roll = 0;
		TextureRegion[][] rollSpriteSheet = TextureRegion.split(new Texture("Ghost.png"), GHOST_WIDTH_PIXEL, GHOST_HEIGHT_PIXEL);
		
		character[roll] = new Animation<TextureRegion>(GHOST_ANIMATION_SPEED, rollSpriteSheet[roll]);
		
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stateTime += delta;
		
		game.batch.begin();
		game.batch.draw(character[roll].getKeyFrame(stateTime, true), x, y, Charater_WIDTH, Charater_HEIGHT);
		game.batch.end();
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
