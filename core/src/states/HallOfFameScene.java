package states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Json;
import com.gameprogmeth.game.GameProgMeth;

public class HallOfFameScene implements Screen{
	
	private GameProgMeth game;
	private OrthographicCamera cam;
	
	private Texture nextBtn;
	private Music bgMusic;
	private Sound btnSound;
	
	private String[] names;
	private int[] scores;
	
	private String header;
	private BitmapFont fontHeader;
	
	public HallOfFameScene(GameProgMeth game){
		this.game = game;
		
		cam = new OrthographicCamera();
		cam.setToOrtho(false, GameProgMeth.WIDTH, GameProgMeth.HEIGHT);
		cam.update();
		
		header = "Hall Of Fame";
		fontHeader = new BitmapFont();
		fontHeader.getData().setScale(5f);
		
		nextBtn = new Texture("Next.png");
		btnSound = Gdx.audio.newSound(Gdx.files.internal("Music/Click.mp3"));
		bgMusic = Gdx.audio.newMusic(Gdx.files.internal("Music/Tupelo_Train.mp3"));
		bgMusic.setLooping(true);
		bgMusic.play();
	}
	
	public void handleInput() {
		if(Gdx.input.justTouched()) {
			if(isOnNextBtn()) {
				btnSound.play();
				this.dispose();
				game.setMenuScene();
			}
		}
	}

	public boolean isOnNextBtn() {
		return 	GameProgMeth.WIDTH- 10 - nextBtn.getWidth() <= Gdx.input.getX() &&
				GameProgMeth.WIDTH - 10 >= Gdx.input.getX() &&
				GameProgMeth.HEIGHT - 10 - nextBtn.getHeight() <= Gdx.input.getY() &&
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
		game.getBatch().draw(nextBtn, GameProgMeth.WIDTH - 10 - nextBtn.getWidth(), 10, nextBtn.getWidth(), nextBtn.getHeight());
		game.getBatch().end();
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
