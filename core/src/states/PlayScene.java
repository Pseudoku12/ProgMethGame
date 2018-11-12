package states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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
	
	private Texture pauseBtn;
	private Sound  btnSound;
	private Music bgMusic;
	
	
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
		pauseBtn = new Texture("Pause.png"); 
		btnSound = Gdx.audio.newSound(Gdx.files.internal("Music/Click.mp3"));
		bgMusic = Gdx.audio.newMusic(Gdx.files.internal("Music/Under_Cover.mp3"));
		bgMusic.setLooping(true);
		bgMusic.play();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		cam.position.set(gameMap.getMainCharacterPosition().x, gameMap.getMainCharacterPosition().y, 0);
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
		pauseBtn.dispose();
		btnSound.dispose();
		bgMusic.dispose();

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

		cam.position.set(gameMap.getMainCharacterPosition().x+50, gameMap.getMainCharacterPosition().y+50, 0);
		cam.update();
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

}
