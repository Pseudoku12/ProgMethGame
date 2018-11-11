package characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.gameprogmeth.game.GameProgMeth;

public class MainCharacter extends Character {
	
	private int stamina;

	public MainCharacter(int x, int y, int speed) {
		animationSpeed = 0.5f;
		renderWidth = 100;
		renderHeight = 100;
		widthPixel = 126;
		heightPixel = 126;

		position = new Vector2(x, y);
		velocity = new Vector2(0, 0);
		this.speed = speed;

		animation = new Animation[8];
		roll = 2;

		TextureRegion[][] rollSpriteSheet = TextureRegion.split(new Texture("Main Character Walk.png"), widthPixel,
				heightPixel);

		animation[0] = new Animation<TextureRegion>(animationSpeed, rollSpriteSheet[0]);
		animation[1] = new Animation<TextureRegion>(animationSpeed, rollSpriteSheet[1]);
		animation[2] = new Animation<TextureRegion>(animationSpeed, rollSpriteSheet[2]);
		animation[3] = new Animation<TextureRegion>(animationSpeed, rollSpriteSheet[3]);
		animation[4] = new Animation<TextureRegion>(animationSpeed, rollSpriteSheet[4]);
		animation[5] = new Animation<TextureRegion>(animationSpeed, rollSpriteSheet[5]);
		animation[6] = new Animation<TextureRegion>(animationSpeed, rollSpriteSheet[6]);
		animation[7] = new Animation<TextureRegion>(animationSpeed, rollSpriteSheet[7]);
		
		stamina = 100;
	}
	
	public int getStamina() {
		return stamina;
	}
	
	public void setStamina(int stamina) {
		this.stamina = stamina;
	}

}
