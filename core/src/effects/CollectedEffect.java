package effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import characters.MainCharacter;

public class CollectedEffect extends Effect {

	private MainCharacter player;
	private float animationSpeed;
	private float renderWidth;
	private float renderHeight;
	private int widthPixel;
	private int heightPixel;
	private boolean isDestroyed;
	private float stateTime;
	private Sound pickUpSound;
	private Vector2 pos;
	private Animation<TextureRegion> animation;

	public CollectedEffect(MainCharacter player) {
		animationSpeed = 0.1f;
		renderWidth = 8;
		renderHeight = 8;
		widthPixel = 8;
		heightPixel = 8;
		TextureRegion[][] rollSpriteSheet = TextureRegion.split(new Texture("effect/collected.png"), widthPixel,
				heightPixel);
		animation = new Animation<TextureRegion>(animationSpeed, rollSpriteSheet[0]);
		this.player = player;
		this.isDestroyed = false;
		pos = new Vector2(0, 0);
		pickUpSound = Gdx.audio.newSound(Gdx.files.internal("music/PickUpSound.mp3"));
		pickUpSound.play();
	}

	public void render(SpriteBatch batch) {
		batch.draw(animation.getKeyFrame(stateTime, true), pos.x, pos.y, renderWidth, renderHeight);
	}

	public void update(float dt) {
		this.pos = new Vector2(player.getPosition().x + player.getRenderWidth() / 2 - renderWidth / 2,
				player.getPosition().y + player.getRenderHeight() / 2 - renderHeight / 2);
		if (animation.isAnimationFinished(stateTime)) {
			isDestroyed = true;
		}
		stateTime += dt;
	}

	@Override
	public boolean isDestroyed() {
		return isDestroyed;
	}

}
