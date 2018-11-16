package characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.gameprogmeth.game.GameProgMeth;

public class Item extends Character {

	private MainCharacter player;
	private Sprite sprite;
	private TextureRegion[][] rollSpriteSheet;
	private int item, rollRow, rollCol;
	private boolean isDestroyed;

	public Item(int x, int y, int speed,int item,int rollRow,int rollCol, MainCharacter player) {
		animationSpeed = 0.5f;
		renderWidth = 16;
		renderHeight = 16;
		widthPixel = 16;
		heightPixel = 16;

		position = new Vector2(x, y);
		velocity = new Vector2(0, 0);
		this.speed = speed;
		
		this.rollRow = rollRow;
		this.rollCol = rollCol;

		rollSpriteSheet = TextureRegion.split(new Texture("Stone_Gem_Ladder.png"), widthPixel, heightPixel);

		
		this.player = player;
		this.item = item;
		this.isDestroyed = false;
	}

	double dx;
	double dy;
	double ds;
	@Override
	public void update(float dt) {
		dx = player.getPosition().x - position.x+27;
		dy = player.getPosition().y - position.y+27;
		ds = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
		velocity.x = (float)(dx/ds)*speed;
		velocity.y = (float)(dy/ds)*speed;
		velocity.scl(dt);
		position.add(velocity.x, velocity.y);
		velocity.scl(1/dt);
		if(ds < 10) {
			player.addScore(10);
			isDestroyed = true;
		}
	}
	
	public TextureRegion getTexture() {
		return rollSpriteSheet[item][2];
	}
	
	public boolean isDestroyed() {
		return isDestroyed;
	}
}
