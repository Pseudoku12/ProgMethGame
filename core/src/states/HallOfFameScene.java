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

import halloffame.HallOfFameData;
import halloffame.HallOfFameLoader;

public class HallOfFameScene implements Screen{
	
	private GameProgMeth game;
	private OrthographicCamera cam;
	
	private Texture nextBtn;
	private Music bgMusic;
	private Sound btnSound;
	
	private String[] names;
	private String[] scores;
	
	private String header;
	private BitmapFont fontHeader;
	private BitmapFont fontList;
	
	public HallOfFameScene(GameProgMeth game){
		this.game = game;
		
		cam = new OrthographicCamera();
		cam.setToOrtho(false, GameProgMeth.WIDTH, GameProgMeth.HEIGHT);
		cam.update();
		
		HallOfFameData data = HallOfFameLoader.loadHallOfFame();
		names = data.names;
		scores = data.scores;
		
		chkScore();
		
		header = "Hall Of Fame";
		fontHeader = new BitmapFont();
		fontHeader.getData().setScale(5f);
		
		fontList = new BitmapFont();
		fontList.getData().setScale(3f);
		
		nextBtn = new Texture("Next.png");
		btnSound = Gdx.audio.newSound(Gdx.files.internal("Music/Click.mp3"));
		bgMusic = Gdx.audio.newMusic(Gdx.files.internal("Music/Tupelo_Train.mp3"));
		bgMusic.setLooping(true);
		bgMusic.play();
	}
	
	public void chkScore() {
		int order = 5;
		for(int i = 0; i < 5; i++) {
			if(Integer.parseInt(scores[i]) < GameProgMeth.score) {
				order = i;
				break;
			}
		}
		if(order == 5)	return;
		for(int i = 3; i >= order; i--) {
			scores[i+1] = scores[i];
			names[i+1] = names[i];
		}
		scores[order] = Integer.toString(GameProgMeth.score);
		names[order] = GameProgMeth.name;
		
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
		
		fontHeader.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		fontHeader.draw(game.getBatch(), header, GameProgMeth.WIDTH/2 - 220, GameProgMeth.HEIGHT - 100);
		
		fontList.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		fontList.draw(game.getBatch(), names[0], GameProgMeth.WIDTH/2 - 200, GameProgMeth.HEIGHT - 300);
		fontList.draw(game.getBatch(), scores[0], GameProgMeth.WIDTH/2 + 100, GameProgMeth.HEIGHT - 300);
		fontList.draw(game.getBatch(), names[1], GameProgMeth.WIDTH/2 - 200, GameProgMeth.HEIGHT - 350);
		fontList.draw(game.getBatch(), scores[1], GameProgMeth.WIDTH/2 + 100, GameProgMeth.HEIGHT - 350);
		fontList.draw(game.getBatch(), names[2], GameProgMeth.WIDTH/2 - 200, GameProgMeth.HEIGHT - 400);
		fontList.draw(game.getBatch(), scores[2], GameProgMeth.WIDTH/2 + 100, GameProgMeth.HEIGHT - 400);
		fontList.draw(game.getBatch(), names[3], GameProgMeth.WIDTH/2 - 200, GameProgMeth.HEIGHT - 450);
		fontList.draw(game.getBatch(), scores[3], GameProgMeth.WIDTH/2 + 100, GameProgMeth.HEIGHT - 450);
		fontList.draw(game.getBatch(), names[4], GameProgMeth.WIDTH/2 - 200, GameProgMeth.HEIGHT - 500);
		fontList.draw(game.getBatch(), scores[4], GameProgMeth.WIDTH/2 + 100, GameProgMeth.HEIGHT - 500);
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
		nextBtn.dispose();
		bgMusic.dispose();
		btnSound.dispose();
	}
	
}
