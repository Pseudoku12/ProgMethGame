package states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gameprogmeth.game.GameProgMeth;

public class MenuState extends State{
	
	private Texture background;
	private Texture playBtn;
	private Texture exitBtn;
	private Texture scoreBtn;
	private Sound btnSound;
	private Music bgMusic;
	
	public MenuState(GameStateManager gsm) {
		super(gsm);
		background = new Texture("Menu.png");
		playBtn = new Texture("Start.png");
		exitBtn = new Texture("Exit.png");
		scoreBtn = new Texture("HighScore.png");
		btnSound = Gdx.audio.newSound(Gdx.files.internal("Music/Click.mp3"));
		bgMusic = Gdx.audio.newMusic(Gdx.files.internal("Music/Tupelo_Train.mp3"));
		bgMusic.setLooping(true);
		bgMusic.play();
	}

	@Override
	protected void handleInput() {
		if(Gdx.input.justTouched()) {
			
			if(isOnStartBtn()) {
				System.out.println("click");
				btnSound.play();
				gsm.push(new PlayScene(gsm));
				dispose();
			}
			
			else if(isOnExitBtn()) {
				btnSound.play();
				Gdx.app.exit();
			}
		}
		
	}

	@Override
	public void update(float dt) {
		handleInput();
		
	}

	@Override
	public void render(SpriteBatch sb) {
		sb.begin();
		sb.draw(background, 0, 0, GameProgMeth.WIDTH, GameProgMeth.HEIGHT);
		sb.draw(scoreBtn, 10, 10, scoreBtn.getWidth()/2, scoreBtn.getHeight()/2);
		sb.draw(exitBtn, GameProgMeth.WIDTH - 10 - exitBtn.getWidth()/2, 10, exitBtn.getWidth()/2, exitBtn.getHeight()/2);
		sb.draw(playBtn,GameProgMeth.WIDTH/2 - playBtn.getWidth()/2,GameProgMeth.HEIGHT/2 - (1.5f)*playBtn.getHeight());
		sb.end();
	
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
}
