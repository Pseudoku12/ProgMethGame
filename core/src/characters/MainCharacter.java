package characters;

import java.util.ArrayList;

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
	private int score;
	public boolean isBlockedLeft, isBlockedRight, isBlockedUp, isBlockedDown;
	public Animation<TextureRegion>[] idleAnimation;
	
	public MainCharacter(int x, int y, int speed) {
		animationSpeed = 0.25f;
		renderWidth = 63;
		renderHeight = 63;
		widthPixel = 126;
		heightPixel = 126;

		position = new Vector2(x, y);
		velocity = new Vector2(0, 0);
		this.speed = speed;

		animation = new Animation[8];
		idleAnimation = new Animation[4];
		roll = 2;

		TextureRegion[][] rollSpriteSheet = TextureRegion.split(new Texture("character/Main Character Walk.png"), widthPixel,
				heightPixel);

		for(int i = 0;i<8;i++) {
			animation[i] = new Animation<TextureRegion>(animationSpeed, rollSpriteSheet[i]);
		}

		TextureRegion[][] IdleSpriteSheet = new TextureRegion[4][1]; 
		for(int i = 0;i<4;i++) {
			IdleSpriteSheet[i][0] = rollSpriteSheet[i][1];
		}
		
		for(int i = 0;i<4;i++) {
			idleAnimation[i] = new Animation<TextureRegion>(0, IdleSpriteSheet[i]);
		}
				
		stamina = 100;
		score = 0;
	}

	public int getStamina() {
		return stamina;
	}

	public void setStamina(int stamina) {
		this.stamina = stamina;
	}

	public void addScore(int score) {
		this.score += score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getScore() {
		return score;
	}
	
	@Override
	public void update(float dt) {
		velocity.scl(dt);
		
		if(isBlockedLeft && velocity.x < 0) {
			velocity.x = 0;
		}
		if(isBlockedRight && velocity.x >= 0) {
			velocity.x = 0;
		}
		if(isBlockedUp && velocity.y >= 0) {
			velocity.y = 0;
		}
		if(isBlockedDown && velocity.y < 0) {
			velocity.y = 0;
		}
		position.add(velocity.x, velocity.y);
		if(position.x < 0) {
			position.x = 0;
		}
		if(position.y < 0) {
			position.y = 0;
		}
		
		velocity.scl(1/dt);
	}
	
	public Animation<TextureRegion> getIdleAnimation() {
		return idleAnimation[roll];
	}
}
