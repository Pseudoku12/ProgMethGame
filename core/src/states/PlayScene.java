package states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.gameprogmeth.game.GameProgMeth;
import com.gameprogmeth.game.world.GameMap;
import com.gameprogmeth.game.world.StoneAndGem;
import com.gameprogmeth.game.world.custommap.CustomGameMap;

import characters.Character;
import characters.MainCharacter;

public class PlayScene extends State implements Screen {

	private float stateTime;
	private GameProgMeth game;
	private GameStateManager gsm;
	private Character character;
	private MenuState menuState;
	
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
		if (Gdx.input.isTouched()) {
			cam.translate(-Gdx.input.getDeltaX(), Gdx.input.getDeltaY());
			cam.update();
		}
		
		if(Gdx.input.justTouched()) {
			final Vector3 pos = cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
			final StoneAndGem stone = gameMap.getStoneAndGemByLocation(2, pos.x, pos.y);
			
			final int col = gameMap.changeXToCol(pos.x);
			final int row = gameMap.changeYToRow(pos.y);
			
			if(stone != null) {
				
				final CustomGameMap customGameMap = (CustomGameMap)gameMap;
				
				if(stone.getId() == StoneAndGem.LADDER_GROUND.getId()) {
					
					customGameMap.destroyLadder(col, row);
					customGameMap.toNextLevel();
					System.out.println("next level");
					
				}
				else {
					customGameMap.destroyStone(col, row, stone.getDestroy());
					customGameMap.dropValueable(stone, col, row);
					
					Timer.schedule(new Task() {
						public void run() {
							customGameMap.destroyStone(col, row, 100);
						}
					},0.5f);
					
					customGameMap.checkLadder(col, row);
				}

			}

		}
		
	}

	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
		handleInput();
		gameMap.update(dt);
		stateTime += dt;
	}

	@Override
	public void render(SpriteBatch sb) {
		// TODO Auto-generated method stub
		sb.setProjectionMatrix(cam.combined);
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
