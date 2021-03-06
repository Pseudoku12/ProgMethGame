package states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.gameprogmeth.game.GameProgMeth;

public class GameOverScene implements Screen {

	private Texture gameOverBg;
	private TextField playerNameText;
	private Texture nextBtn;
	private Sound btnSound;
	private Music bgMusic;
	private OrthographicCamera cam;
	private GameProgMeth game;
	private String playerNameLabel;
	private String playerScore;
	private BitmapFont font;
	private Stage stage;
	private String name;

	public GameOverScene(GameProgMeth game) {
		this.game = game;
		cam = new OrthographicCamera();
		cam.setToOrtho(false, GameProgMeth.WIDTH, GameProgMeth.HEIGHT);
		cam.update();
		playerNameLabel = "Please Enter Your Name";
		playerScore = "Score : " + GameProgMeth.score;
		font = new BitmapFont();
		font.getData().setScale(2f);
		gameOverBg = new Texture("resource/GameOverBg.jpg");
		nextBtn = new Texture("button/Next.png");
		btnSound = Gdx.audio.newSound(Gdx.files.internal("music/Click.mp3"));
		bgMusic = Gdx.audio.newMusic(Gdx.files.internal("music/Happy_Haunts.mp3"));
		bgMusic.setLooping(true);
		bgMusic.play();
		stage = new Stage();
		Skin uiSkin = new Skin(Gdx.files.internal("resource/uiskin.json"));
		playerNameText = new TextField("", uiSkin);
		playerNameText.setPosition(GameProgMeth.WIDTH / 2 + 50, GameProgMeth.HEIGHT / 2 - 130);
		playerNameText.setSize(200, 50);
		stage.addActor(playerNameText);
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		handleInput();
		cam.update();
		game.getBatch().setProjectionMatrix(cam.combined);
		game.getBatch().begin();
		game.getBatch().draw(gameOverBg, 0, 0, GameProgMeth.WIDTH, GameProgMeth.HEIGHT);
		game.getBatch().draw(nextBtn, GameProgMeth.WIDTH - 10 - nextBtn.getWidth(), 10);
		font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		font.draw(game.getBatch(), playerNameLabel, GameProgMeth.WIDTH / 2 - 350, GameProgMeth.HEIGHT / 2 - 100);
		font.draw(game.getBatch(), playerScore, GameProgMeth.WIDTH / 2 - 100, GameProgMeth.HEIGHT / 2 - 200);
		game.getBatch().end();
		stage.act();
		stage.draw();
	}

	public void handleInput() {
		if (Gdx.input.justTouched()) {
			if (isOnNextBtn()) {
				btnSound.play();
				name = (playerNameText.getText().equals("")) ? ("A") : (playerNameText.getText().trim());
				playerNameText.setText("");
				this.dispose();
				game.setHallOfFameSceneFromGameOverScene(name);
			}
		}

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
		gameOverBg.dispose();
		nextBtn.dispose();
		btnSound.dispose();
		bgMusic.dispose();
	}

	public boolean isOnNextBtn() {
		return Gdx.input.getX() >= GameProgMeth.WIDTH - 10 - nextBtn.getWidth()
				&& Gdx.input.getX() <= GameProgMeth.WIDTH - 10
				&& Gdx.input.getY() >= GameProgMeth.HEIGHT - 10 - nextBtn.getHeight()
				&& Gdx.input.getY() <= GameProgMeth.HEIGHT - 10;
	}

}
