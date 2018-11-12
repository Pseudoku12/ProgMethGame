package states;

import com.badlogic.gdx.Gdx;


import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.gameprogmeth.game.GameProgMeth;

import characters.Character;
import characters.MainCharacter;

public class TestCharacter extends State implements Screen {

	private float stateTime;
	private Character character;
	private Character enemy1;

	public TestCharacter(GameStateManager gsm) {
		super(gsm);
		character = new MainCharacter(100, 100, 300);
//		enemy1 = new Ghost(200, 200, 400, character);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stateTime += delta;
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
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			character.setVelocity(0, character.getSpeed());
			character.setRoll(3);
			;
		} else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			character.setVelocity(-character.getSpeed(), 0);
			character.setRoll(1);
		} else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			character.setVelocity(0, -character.getSpeed());
			character.setRoll(0);
		} else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			character.setVelocity(character.getSpeed(), 0);
			character.setRoll(2);
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && character.getRoll() == 0) {
			character.setVelocity(0, 0);
			character.setRoll(4);
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && character.getRoll() == 1) {
			character.setVelocity(0, 0);
			character.setRoll(5);
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && character.getRoll() == 2) {
			character.setVelocity(0, 0);
			character.setRoll(6);
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && character.getRoll() == 3) {
			character.setVelocity(0, 0);
			character.setRoll(7);
		} else {
			character.setVelocity(0, 0);
		}
	}

	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
		handleInput();
		character.update(dt);
//		enemy1.update(dt);
		stateTime += dt;
	}

	@Override
	public void render(SpriteBatch sb) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		sb.begin();
		sb.draw(character.getAnimation().getKeyFrame(stateTime, true), character.getPosition().x,
				character.getPosition().y, character.getRenderWidth(), character.getRenderHeight());
//		sb.draw(enemy1.getAnimation().getKeyFrame(stateTime, true), enemy1.getPosition().x, enemy1.getPosition().y,
//				enemy1.getRenderWidth(), enemy1.getRenderHeight());
		if (character.getAnimation().isAnimationFinished(stateTime)) {
			switch(character.getRoll()){
				case 4 : character.setRoll(0);
				case 5 : character.setRoll(1);
				case 6 : character.setRoll(2);
				case 7 : character.setRoll(3);
			}
		}
		sb.end();
	}
}
