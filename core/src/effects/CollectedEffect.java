package effects;

import java.awt.Transparency;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import characters.Destroyable;
import characters.MainCharacter;

public class CollectedEffect extends Effect{

	private MainCharacter player;
	private float animationSpeed;
	private float renderWidth;
	private float renderHeight;
	private int widthPixel;
	private int heightPixel;
	private boolean isDestroyed;
	private float stateTime;
	private int roll;

	private Vector2 pos;

	private Animation<TextureRegion>[] animation;

	public CollectedEffect(MainCharacter player) {
		animationSpeed = 0.1f;
		renderWidth = 8;
		renderHeight = 8;
		widthPixel = 8;
		heightPixel = 8;

		TextureRegion[][] rollSpriteSheet = TextureRegion.split(new Texture("effect/collected.png"), widthPixel,
				heightPixel);
		animation = new Animation[1];
		for (int i = 0; i < 1; i++) {
			animation[i] = new Animation<TextureRegion>(animationSpeed, rollSpriteSheet[i]);
		}
		this.player = player;
		this.isDestroyed = false;
		roll = 0;
		pos = new Vector2(0, 0);
	}

	public void render(SpriteBatch batch) {
		batch.draw(animation[roll].getKeyFrame(stateTime, true), pos.x, pos.y, renderWidth, renderHeight);
	}

	public void update(float dt) {
		this.pos = new Vector2(player.getPosition().x + player.getRenderWidth() / 2 - renderWidth / 2,
				player.getPosition().y + player.getRenderHeight() / 2 - renderHeight / 2);
		if (animation[roll].isAnimationFinished(stateTime)) {
			isDestroyed = true;
		}
		stateTime += dt;
	}

	@Override
	public boolean isDestroyed() {
		return isDestroyed;
	}

}
