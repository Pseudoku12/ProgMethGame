package states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gameprogmeth.game.GameProgMeth;
import com.gameprogmeth.game.world.GameMap;
import com.gameprogmeth.game.world.custommap.CustomGameMap;

import characters.Character;
import characters.MainCharacter;

public class PlayScene extends State implements Screen {

	private float stateTime;
	private GameProgMeth game;
	private GameStateManager gsm;
	private Character character;

	OrthographicCamera cam;
	GameMap gameMap;

	public PlayScene(GameStateManager gsm) {
		super(gsm);
		this.game = game;

		cam = new OrthographicCamera();
		cam.setToOrtho(false, Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 4);
		cam.update();

//		gameMap = new TiledGameMap();
		gameMap = new CustomGameMap();

		character = new MainCharacter(100, 100, 300);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (Gdx.input.isTouched()) {
			cam.translate(Gdx.input.getDeltaX(), Gdx.input.getDeltaY());
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

	@Override
	protected void handleInput() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
//		handleInput();
		gameMap.update(dt);
		stateTime += dt;
	}

	@Override
	public void render(SpriteBatch sb) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (Gdx.input.isTouched()) {
			cam.translate(-Gdx.input.getDeltaX(), Gdx.input.getDeltaY());
			cam.update();
		}
		gameMap.render(cam);

//		sb.begin();
//		sb.draw(character.getAnimation().getKeyFrame(stateTime, true), character.getPosition().x,
//				character.getPosition().y, character.getRenderWidth(), character.getRenderHeight());
//			sb.draw(enemy1.getAnimation().getKeyFrame(stateTime, true), enemy1.getPosition().x, enemy1.getPosition().y,
//					enemy1.getRenderWidth(), enemy1.getRenderHeight());
//		if (character.getAnimation().isAnimationFinished(stateTime)) {
//			switch (character.getRoll()) {
//			case 4:
//				character.setRoll(0);
//			case 5:
//				character.setRoll(1);
//			case 6:
//				character.setRoll(2);
//			case 7:
//				character.setRoll(3);
//			}
//		}
//		sb.end();
	}
}
