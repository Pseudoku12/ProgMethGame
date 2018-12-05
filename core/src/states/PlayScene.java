package states;

import java.beans.Customizer;
import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
import characters.Ghost;
import characters.MainCharacter;

public class PlayScene implements Screen {

	private float stateTime;
	private GameProgMeth game;

	private Texture pauseBtn;
	private Texture playBtn;
	private Texture menuBtn;
	private Texture pauseTextBox;
	private Texture pauseBg;
	private Texture storeBtn;
	private Texture backBtn;
	private Texture exchangeBtn;
	private Sound btnSound;

	private TextureRegion[][] healthBar;
	private TextureRegion[][] hammers;

	private boolean isPauseState;
	private boolean isStoreState;

	private int score;
	private int hammerType;
	private String shop;
	private String hammerLabel;
	private String hammerCost;
	private BitmapFont fontShop;
	private BitmapFont fontHammer;

	OrthographicCamera cam;
	CustomGameMap gameMap;

	private ArrayList<String> hammerTypeIndex;

	public PlayScene(GameProgMeth game) {
		this.game = game;

		cam = new OrthographicCamera();
		cam.setToOrtho(false, Gdx.graphics.getWidth() / 4, Gdx.graphics.getHeight() / 4);
		cam.update();

		gameMap = new CustomGameMap(this.game, this.cam);

		pauseBtn = new Texture("button/Pause.png");
		playBtn = new Texture("button/Start.png");
		menuBtn = new Texture("button/Exit.png");
		storeBtn = new Texture("button/Store.png");
		backBtn = new Texture("button/Back.png");
		exchangeBtn = new Texture("button/Exchange.png");
		pauseTextBox = new Texture("resource/TextBox.png");
		pauseBg = new Texture("resource/PauseBg.png");

		btnSound = Gdx.audio.newSound(Gdx.files.internal("music/Click.mp3"));

		isPauseState = false;
		isStoreState = false;

		healthBar = TextureRegion.split(new Texture("character/Stamina_Bar.png"), 122, 33);
		hammers = TextureRegion.split(new Texture("character/Hammer.png"), 13, 14);
		hammerType = 1;

		fontShop = new BitmapFont();
		fontShop.getData().setScale(2f);
		fontHammer = new BitmapFont();
		fontHammer.getData().setScale(1f);

		shop = "Shop";
		hammerLabel = "Copper Hammer";
		hammerCost = "1000";

		hammerTypeIndex = new ArrayList<String>();
		hammerTypeIndex.add("Copper");
		hammerTypeIndex.add("Silver");
		hammerTypeIndex.add("Gold");
		hammerTypeIndex.add("Mystril");
		hammerTypeIndex.add("Cursed");
		hammerTypeIndex.add("Blessed");
		hammerTypeIndex.add("Mythic");
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
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
		storeBtn.dispose();
		backBtn.dispose();

	}

	public void handleInput() {
		if (!isPauseState && !isStoreState) {
			if (Gdx.input.justTouched()) {
				if (isOnPauseBtn()) {
					btnSound.play();
					isPauseState = true;
					gameMap.setPauseCounter(1);
					return;
				}
				if (isOnStoreBtn()) {
					btnSound.play();
					isStoreState = true;
					gameMap.setPauseCounter(1);
					return;
				}
			}

		} else if (isPauseState) {
			if (Gdx.input.justTouched()) {
				if (isOnStartBtn()) {
					btnSound.play();
					isPauseState = false;
				} else if (isOnMenuBtn()) {
					btnSound.play();
//					cam.position.set(new Vector3(0,0,0));
					this.dispose();
//					game.setScreen(new MenuState(game));
					game.setGameOverScene(CustomGameMap.getMainCharacter().getScore());
				}
			}
		} else if (isStoreState) {
			if (Gdx.input.justTouched()) {
				if (isOnBackBtn()) {
					btnSound.play();
					isStoreState = false;
				} else if (isOnExchangeBtn()) {
					btnSound.play();
					if (CustomGameMap.getMainCharacter().getScore() + GameProgMeth.score >= Integer
							.parseInt(hammerCost)) {
						hammerType++;
						CustomGameMap.getMainCharacter().addScore(-Integer.parseInt(hammerCost));
						CustomGameMap.getMainCharacter().setDamage(CustomGameMap.getMainCharacter().getDamage() + 1);
						hammerLabel = hammerTypeIndex.get(hammerType - 1) + " Hammer";
						hammerCost = Integer.toString(Integer.parseInt(hammerCost) * 2);
					}
				}
			}
		}

	}

	public void update(float dt) {
		// TODO Auto-generated method stub
		handleInput();
		if (!isPauseState && !isStoreState) {
			gameMap.update(dt);
			stateTime += dt;
		}

	}

	public void render(SpriteBatch sb) {
		// TODO Auto-generated method stub
		sb.setProjectionMatrix(cam.combined);
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if (!isPauseState && !isStoreState) {
			gameMap.render();
			cam.position.set(gameMap.getMainCharacterPosition().x + 31.5f, gameMap.getMainCharacterPosition().y + 31.5f,
					0);
			cam.update();
			sb.begin();
			sb.draw(healthBar[10 - CustomGameMap.getMainCharacter().getHP() / 10][0],
					gameMap.getMainCharacterPosition().x + GameProgMeth.WIDTH / 8 - 10,
					gameMap.getMainCharacterPosition().y + 31.5f - GameProgMeth.HEIGHT / 8 + 10, 30f, 8f);
			sb.draw(pauseBtn,
					gameMap.getMainCharacterPosition().x + 31.5f + Gdx.graphics.getWidth() / 8 - 10
							- pauseBtn.getWidth() / 8,
					gameMap.getMainCharacterPosition().y + 31.5f + Gdx.graphics.getHeight() / 8 - 10
							- pauseBtn.getHeight() / 8,
					pauseBtn.getWidth() / 8, pauseBtn.getHeight() / 8);
			sb.draw(storeBtn, gameMap.getMainCharacterPosition().x + 31.5f - Gdx.graphics.getWidth() / 8 + 10,
					gameMap.getMainCharacterPosition().y + 31.5f + Gdx.graphics.getHeight() / 8 - 10
							- storeBtn.getHeight() / 8,
					storeBtn.getWidth() / 8, storeBtn.getHeight() / 8);
			sb.end();
		} else if (isPauseState) {
			gameMap.render();
			sb.begin();
			sb.draw(pauseBg, gameMap.getMainCharacterPosition().x + 31.5f - GameProgMeth.WIDTH / 8,
					gameMap.getMainCharacterPosition().y + 31.5f - GameProgMeth.HEIGHT / 8, GameProgMeth.WIDTH / 4,
					GameProgMeth.HEIGHT / 4);
			sb.draw(pauseTextBox, gameMap.getMainCharacterPosition().x + 31.5f - 50f,
					gameMap.getMainCharacterPosition().y + 31.5f - 25f, 100, 50);
			sb.draw(playBtn, gameMap.getMainCharacterPosition().x + 31.5f - 6 - playBtn.getWidth() / 4,
					gameMap.getMainCharacterPosition().y + 31.5f - playBtn.getHeight() / 8, playBtn.getWidth() / 4,
					playBtn.getHeight() / 4);
			sb.draw(menuBtn, gameMap.getMainCharacterPosition().x + 31.5f + 6,
					gameMap.getMainCharacterPosition().y + 31.5f - menuBtn.getHeight() / 8, menuBtn.getWidth() / 4,
					menuBtn.getHeight() / 4);
			sb.end();
		} else if (isStoreState) {
			gameMap.render();
			sb.begin();
			sb.draw(pauseBg, gameMap.getMainCharacterPosition().x + 31.5f - GameProgMeth.WIDTH / 8,
					gameMap.getMainCharacterPosition().y + 31.5f - GameProgMeth.HEIGHT / 8, GameProgMeth.WIDTH / 4,
					GameProgMeth.HEIGHT / 4);
			sb.draw(pauseTextBox, gameMap.getMainCharacterPosition().x + 31.5f - GameProgMeth.WIDTH / 8 + 16,
					gameMap.getMainCharacterPosition().y + 31.5f - GameProgMeth.HEIGHT / 8 + 54,
					(pauseTextBox.getWidth() * 3) / 2, (pauseTextBox.getHeight() * 3) / 2);
			sb.draw(hammers[0][hammerType], gameMap.getMainCharacterPosition().x + 31.5f - 110,
					gameMap.getMainCharacterPosition().y + 31.5f - 20, 39, 42);
			sb.draw(backBtn, gameMap.getMainCharacterPosition().x + 31.5f - GameProgMeth.WIDTH / 8 + 10,
					gameMap.getMainCharacterPosition().y + 31.5f + GameProgMeth.HEIGHT / 8 - 10
							- backBtn.getHeight() / 8,
					backBtn.getWidth() / 8, backBtn.getHeight() / 8);
			sb.draw(exchangeBtn,
					gameMap.getMainCharacterPosition().x + 31.5f + GameProgMeth.WIDTH / 8 - 10
							- exchangeBtn.getWidth() / 8,
					gameMap.getMainCharacterPosition().y + 31.5f - GameProgMeth.HEIGHT / 8 + 10,
					exchangeBtn.getWidth() / 8, exchangeBtn.getHeight() / 8);

			fontShop.setColor(1.0f, 1.0f, 1.0f, 1.0f);
			fontShop.draw(sb, shop, gameMap.getMainCharacterPosition().x + 31.5f - 30,
					gameMap.getMainCharacterPosition().y + 31.5f + 70);

			fontHammer.setColor(1.0f, 1.0f, 1.0f, 1.0f);
			fontHammer.draw(sb, hammerLabel, gameMap.getMainCharacterPosition().x + 31.5f - 60,
					gameMap.getMainCharacterPosition().y + 31.5f + 5);
			fontHammer.draw(sb, hammerCost, gameMap.getMainCharacterPosition().x + 31.5f + 70,
					gameMap.getMainCharacterPosition().y + 31.5f + 5);
			sb.end();
		}

	}

	public boolean isOnPauseBtn() {
		return GameProgMeth.WIDTH - 40 - pauseBtn.getWidth() / 2 <= Gdx.input.getX()
				&& GameProgMeth.WIDTH - 40 >= Gdx.input.getX() && 40 <= Gdx.input.getY()
				&& 40 + pauseBtn.getHeight() / 2 >= Gdx.input.getY();
	}

	public boolean isOnStartBtn() {
		return GameProgMeth.WIDTH / 2 - 24 - playBtn.getWidth() <= Gdx.input.getX()
				&& GameProgMeth.WIDTH / 2 - 24 >= Gdx.input.getX()
				&& GameProgMeth.HEIGHT / 2 - playBtn.getHeight() / 2 <= Gdx.input.getY()
				&& GameProgMeth.HEIGHT / 2 + playBtn.getHeight() / 2 >= Gdx.input.getY();
	}

	public boolean isOnMenuBtn() {
		return GameProgMeth.WIDTH / 2 + 24 <= Gdx.input.getX()
				&& GameProgMeth.WIDTH / 2 + 24 + menuBtn.getWidth() >= Gdx.input.getX()
				&& GameProgMeth.HEIGHT / 2 - menuBtn.getHeight() / 2 <= Gdx.input.getY()
				&& GameProgMeth.HEIGHT / 2 + menuBtn.getHeight() / 2 >= Gdx.input.getY();
	}

	public boolean isOnStoreBtn() {
		return 40 <= Gdx.input.getX() && 40 + storeBtn.getWidth() / 2 >= Gdx.input.getX() && 40 <= Gdx.input.getY()
				&& 40 + storeBtn.getHeight() / 2 >= Gdx.input.getY();
	}

	public boolean isOnBackBtn() {
		return 40 <= Gdx.input.getX() && 40 + backBtn.getWidth() / 2 >= Gdx.input.getX() && 40 <= Gdx.input.getY()
				&& 40 + backBtn.getHeight() / 2 >= Gdx.input.getY();
	}

	public boolean isOnExchangeBtn() {
		return GameProgMeth.WIDTH - 40 - exchangeBtn.getWidth() / 2 <= Gdx.input.getX()
				&& GameProgMeth.WIDTH - 40 >= Gdx.input.getX()
				&& GameProgMeth.HEIGHT - 40 - exchangeBtn.getHeight() / 2 <= Gdx.input.getY()
				&& GameProgMeth.HEIGHT - 40 >= Gdx.input.getY();
	}
}
