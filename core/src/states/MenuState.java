package states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.gameprogmeth.game.GameProgMeth;

public class MenuState implements Screen{
	
	private Texture background;
	private Texture playBtn;
	private Texture exitBtn;
	private Texture scoreBtn;
	private Sound btnSound;
	private Music bgMusic;
	
	private OrthographicCamera cam;
	
	private GameProgMeth game;
	
	public MenuState(GameProgMeth game) {

		this.game = game;
		
		cam = new OrthographicCamera();
		cam.setToOrtho(false, GameProgMeth.WIDTH, GameProgMeth.HEIGHT);
		cam.update();
		
		background = new Texture("Menu.png");
		playBtn = new Texture("Start.png");
		exitBtn = new Texture("Exit.png");
		scoreBtn = new Texture("HighScore.png");
		btnSound = Gdx.audio.newSound(Gdx.files.internal("Music/Click.mp3"));
		bgMusic = Gdx.audio.newMusic(Gdx.files.internal("Music/Tupelo_Train.mp3"));
		bgMusic.setLooping(true);
		bgMusic.play();
	}

	public void handleInput() {
		if(Gdx.input.justTouched()) {
			
			if(isOnStartBtn()) {
				btnSound.play();
				this.dispose();
				game.setPlayScene();
			}
			
			else if(isOnExitBtn()) {
				btnSound.play();
				Gdx.app.exit();
			}
		}
		
	}

	@Override
	public void dispose() {
		background.dispose();
		playBtn.dispose();
		exitBtn.dispose();
		scoreBtn.dispose();
		btnSound.dispose();
		bgMusic.dispose();
	}
	
	public boolean isOnStartBtn() {
		return GameProgMeth.WIDTH/2 - playBtn.getWidth()/2 <= Gdx.input.getX() &&
			   GameProgMeth.WIDTH/2 + playBtn.getWidth()/2 >= Gdx.input.getX() &&
			   GameProgMeth.HEIGHT/2 + (0.5f)*playBtn.getHeight() <= Gdx.input.getY() &&
			   GameProgMeth.HEIGHT/2 + (1.5f)*playBtn.getHeight() >= Gdx.input.getY();
	}
	
	public boolean isOnExitBtn() {
		return GameProgMeth.WIDTH- 10 - exitBtn.getWidth()/2 <= Gdx.input.getX() &&
			   GameProgMeth.WIDTH - 10 >= Gdx.input.getX() &&
			   GameProgMeth.HEIGHT - 10 - exitBtn.getHeight()/2 <= Gdx.input.getY() &&
			   GameProgMeth.HEIGHT - 10 >= Gdx.input.getY();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		handleInput();
		
		cam.update();
		
		game.getBatch().setProjectionMatrix(cam.combined);
		
		game.getBatch().begin();
		game.getBatch().draw(background, 0, 0, GameProgMeth.WIDTH, GameProgMeth.HEIGHT);
		game.getBatch().draw(scoreBtn, 10, 10, scoreBtn.getWidth()/2, scoreBtn.getHeight()/2);
		game.getBatch().draw(exitBtn, GameProgMeth.WIDTH - 10 - exitBtn.getWidth()/2, 10, exitBtn.getWidth()/2, exitBtn.getHeight()/2);
		game.getBatch().draw(playBtn,GameProgMeth.WIDTH/2 - playBtn.getWidth()/2,GameProgMeth.HEIGHT/2 - (1.5f)*playBtn.getHeight());
		game.getBatch().end();
		
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
}
