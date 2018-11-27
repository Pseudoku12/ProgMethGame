package states;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.gameprogmeth.game.GameProgMeth;
import com.gameprogmeth.game.world.GameMap;
import com.gameprogmeth.game.world.StoneAndGem;
import com.gameprogmeth.game.world.custommap.CustomGameMap;

import characters.Character;
import characters.MainCharacter;

public class PlayScene implements Screen {

	private float stateTime;
	private GameProgMeth game;
	
	private Texture pauseBtn;
	private Texture playBtn;
	private Texture menuBtn;
	private Texture pauseTextBox;
	private Texture pauseBg;
	private Sound  btnSound;
	private Music bgMusic;
	
	private TextureRegion[][] staminaBar;
	
	private boolean isPlayState;
	
	OrthographicCamera cam;
	GameMap gameMap;

	public PlayScene(GameProgMeth game) {
		this.game = game;

		cam = new OrthographicCamera();
		cam.setToOrtho(false, Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 4);
		cam.update();

		gameMap = new CustomGameMap(this.game);

		pauseBtn = new Texture("Pause.png"); 
		playBtn = new Texture("Start.png");
		menuBtn = new Texture("Exit.png");
		pauseTextBox = new Texture("textBox.png");
		pauseBg = new Texture("PauseBg.png");
		
		btnSound = Gdx.audio.newSound(Gdx.files.internal("Music/Click.mp3"));
		bgMusic = Gdx.audio.newMusic(Gdx.files.internal("Music/Under_Cover.mp3"));
		bgMusic.setLooping(true);
		bgMusic.play();
		isPlayState = true;
		
		staminaBar = TextureRegion.split(new Texture("Stamina_Bar.png"), 122, 33);

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		if(CustomGameMap.ghost.isPlayerDead()) {
			dispose();
			game.setGameOverScene(CustomGameMap.mainCharacter.getScore());
		}
		render(game.getBatch());
		update(delta);
	}

	@Override
	public void resize(int width, int height) {
		
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
		gameMap.dispose();
		playBtn.dispose();
		menuBtn.dispose();
		pauseTextBox.dispose();
		pauseBg.dispose();
		pauseBtn.dispose();
		btnSound.dispose();
		bgMusic.dispose();
	}

	protected void handleInput() {
		if(isPlayState) {
			if (Gdx.input.justTouched()) {
				if (isOnPauseBtn()) {
					btnSound.play();
					isPlayState = false;
					return;
				}

					}

				}
		else {
			if(Gdx.input.justTouched()) {
				if(isOnStartBtn()) {
					btnSound.play();
					isPlayState = true;
				}
				else if(isOnMenuBtn()) {
					btnSound.play();
					cam.position.set(new Vector3(0,0,0));
					this.dispose();
					game.setScreen(new MenuState(game));
				}
			}
		}
				
	}
	
	public void update(float dt) {
		// TODO Auto-generated method stub
			handleInput();
			if(isPlayState) {
				gameMap.update(dt);
				stateTime += dt;
			}
			
	}

	public void render(SpriteBatch sb) {
		// TODO Auto-generated method stub
		sb.setProjectionMatrix(cam.combined);
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if(isPlayState) {
			gameMap.render(cam);
			cam.position.set(gameMap.getMainCharacterPosition().x + 31.5f,gameMap.getMainCharacterPosition().y + 31.5f, 0);
			cam.update();
			sb.begin();
			sb.draw(staminaBar[10 - CustomGameMap.getMainCharacter().getStamina()/10][0], gameMap.getMainCharacterPosition().x + GameProgMeth.WIDTH/8 - 10, gameMap.getMainCharacterPosition().y + 31.5f - GameProgMeth.HEIGHT/8 + 10, 30f, 8f);
			sb.draw(pauseBtn, gameMap.getMainCharacterPosition().x + 31.5f + Gdx.graphics.getWidth()/8 - 10 - pauseBtn.getWidth()/8, gameMap.getMainCharacterPosition().y + 31.5f + Gdx.graphics.getHeight()/8 - 10 - pauseBtn.getHeight()/8, pauseBtn.getWidth()/8, pauseBtn.getHeight()/8);
			sb.end();
		}
		else {
			gameMap.render(cam);
			sb.begin();
			sb.draw(pauseBg, gameMap.getMainCharacterPosition().x + 31.5f - GameProgMeth.WIDTH/8, gameMap.getMainCharacterPosition().y + 31.5f - GameProgMeth.HEIGHT/8, GameProgMeth.WIDTH/4, GameProgMeth.HEIGHT/4);
			sb.draw(pauseTextBox, gameMap.getMainCharacterPosition().x + 31.5f - 50f, gameMap.getMainCharacterPosition().y + 31.5f - 25f, 100, 50);
			sb.draw(playBtn, gameMap.getMainCharacterPosition().x + 31.5f - 6 - playBtn.getWidth()/4, gameMap.getMainCharacterPosition().y + 31.5f - playBtn.getHeight()/8, playBtn.getWidth()/4, playBtn.getHeight()/4);
			sb.draw(menuBtn, gameMap.getMainCharacterPosition().x + 31.5f + 6, gameMap.getMainCharacterPosition().y + 31.5f - menuBtn.getHeight()/8, menuBtn.getWidth()/4, menuBtn.getHeight()/4);
			sb.end();
		}
		
	}
	
	public boolean isOnPauseBtn() {
		return 	GameProgMeth.WIDTH - 40 - pauseBtn.getWidth()/2 <= Gdx.input.getX() &&
				GameProgMeth.WIDTH - 40 >= Gdx.input.getX() &&
				40 <= Gdx.input.getY() &&
				40 + pauseBtn.getHeight()/2 >= Gdx.input.getY();
	}
	
	public boolean isOnStartBtn() {
		return 	GameProgMeth.WIDTH/2 - 24 - playBtn.getWidth() <= Gdx.input.getX() &&
				GameProgMeth.WIDTH/2 - 24 >= Gdx.input.getX() &&
				GameProgMeth.HEIGHT/2 - playBtn.getHeight()/2 <= Gdx.input.getY() &&
				GameProgMeth.HEIGHT/2 + playBtn.getHeight()/2 >= Gdx.input.getY();
	}

	public boolean isOnMenuBtn() {
		return 	GameProgMeth.WIDTH/2 + 24 <= Gdx.input.getX() &&
				GameProgMeth.WIDTH/2 + 24 + menuBtn.getWidth() >= Gdx.input.getX() &&
				GameProgMeth.HEIGHT/2 - menuBtn.getHeight()/2 <= Gdx.input.getY() &&
				GameProgMeth.HEIGHT/2 + menuBtn.getHeight()/2 >= Gdx.input.getY();
	}
}
