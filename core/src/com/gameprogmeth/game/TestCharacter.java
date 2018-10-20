package com.gameprogmeth.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
	public static final int GHOST_HEIGHT_PIXEL = 24;
	
	private float x;
	private float y;
	private float stateTime;
	private float speed;
	
	private Animation<TextureRegion>[] character;
	private int roll;
	private GameProgMeth game;
	
	public TestCharacter(GameProgMeth game) {
		this.game = game;
		
		x = 100;
		y = 100;
		speed = 5;
		
		character = new Animation[4];
		roll = 2;
		TextureRegion[][] rollSpriteSheet = TextureRegion.split(new Texture("Ghost.png"), GHOST_WIDTH_PIXEL, GHOST_HEIGHT_PIXEL);
		
		character[0] = new Animation<TextureRegion>(GHOST_ANIMATION_SPEED, rollSpriteSheet[0]);
		character[1] = new Animation<TextureRegion>(GHOST_ANIMATION_SPEED, rollSpriteSheet[1]);
		character[2] = new Animation<TextureRegion>(GHOST_ANIMATION_SPEED, rollSpriteSheet[2]);
		character[3] = new Animation<TextureRegion>(GHOST_ANIMATION_SPEED, rollSpriteSheet[3]);
		
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
		if(Gdx.input.isKeyPressed(Input.Keys.W)) {
			y += speed;
			roll = 2;
		} else if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			x -= speed;
			roll = 3;
		} else if(Gdx.input.isKeyPressed(Input.Keys.S)) {
			y -= speed;
			roll = 0;
		} else if(Gdx.input.isKeyPressed(Input.Keys.D)) {
			x += speed;
			roll = 1;
		}
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
