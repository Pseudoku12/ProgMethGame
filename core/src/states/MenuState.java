package states;

import com.badlogic.gdx.Gdx;
<<<<<<< Updated upstream
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
=======
>>>>>>> Stashed changes
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.gameprogmeth.game.GameProgMeth;

public class MenuState extends State{
	
	private Texture background;
	private Texture playBtn;
	private Texture exitBtn;
	private Texture scoreBtn;
<<<<<<< Updated upstream
	private Sound btnSound;
	private Music bgMusic;
=======
>>>>>>> Stashed changes
	private int playWidth, playHeight;
	
	public MenuState(GameStateManager gsm) {
		super(gsm);
		background = new Texture("Menu.png");
		playBtn = new Texture("Start.png");
		exitBtn = new Texture("Exit.png");
		scoreBtn = new Texture("HighScore.png");
<<<<<<< Updated upstream
		btnSound = Gdx.audio.newSound(Gdx.files.internal("Click.mp3"));
		bgMusic = Gdx.audio.newMusic(Gdx.files.internal("Tupelo_Train.mp3"));
		bgMusic.setLooping(true);
		bgMusic.play();
=======
>>>>>>> Stashed changes
		
		playWidth = playBtn.getWidth();
		playHeight = playBtn.getHeight();
	}

	@Override
	protected void handleInput() {
		if(Gdx.input.justTouched()) {
			
			if(GameProgMeth.WIDTH/2 - playWidth <= Gdx.input.getX() && 
			   Gdx.input.getX() <= GameProgMeth.WIDTH/2 + playWidth &&
			   Gdx.input.getY() <= GameProgMeth.HEIGHT/2 + 3*playHeight &&
			   Gdx.input.getY() >= GameProgMeth.HEIGHT/2 + playHeight) {
<<<<<<< Updated upstream
				btnSound.play();
=======
>>>>>>> Stashed changes
				gsm.set(new PlayScene(gsm));
				dispose();
			}
			
			else if(GameProgMeth.WIDTH - 10 - exitBtn.getWidth() <= Gdx.input.getX() &&
					GameProgMeth.WIDTH - 10 >= Gdx.input.getX() &&
					GameProgMeth.HEIGHT - 10 - exitBtn.getHeight() <= Gdx.input.getY() &&
					GameProgMeth.HEIGHT - 10 >= Gdx.input.getY()) {
<<<<<<< Updated upstream
				btnSound.play();
=======
>>>>>>> Stashed changes
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
		sb.draw(background, 0, 0);
		sb.draw(playBtn, GameProgMeth.WIDTH/2 - playWidth , GameProgMeth.HEIGHT/2 - 3*playHeight, 2*playWidth, 2*playHeight);
		sb.draw(scoreBtn, 10, 10);
		sb.draw(exitBtn, GameProgMeth.WIDTH - 10 - exitBtn.getWidth(), 10);
		sb.end();
	
	}

	@Override
	public void dispose() {
		background.dispose();
		playBtn.dispose();
		exitBtn.dispose();
		scoreBtn.dispose();
<<<<<<< Updated upstream
		btnSound.dispose();
		bgMusic.dispose();
=======
		
>>>>>>> Stashed changes
	}
	
	
}
