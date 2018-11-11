package com.gameprogmeth.game.world.custommap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import characters.Character;

public class KeepingMineral {
	
	public final static int SPEED = 1, WIDTHPIXEL = 16, HEIGHTPIXEL = 16; 

	private Character player;
	private int rollRow, rollCol;
	private Vector2 position,velocity;
	private TextureRegion rollSpriteSheet; 
	
	public KeepingMineral(int x, int y, int rollRow, int rollCol, Character player) {


		position = new Vector2(x, y);
		velocity = new Vector2(0, 0);

		this.rollRow = rollRow;
		this.rollCol = rollCol;

<<<<<<< HEAD
		rollSpriteSheet = TextureRegion.split(new Texture("Stone_Gem_Ladder"), WIDTHPIXEL, HEIGHTPIXEL)[rollRow][rollCol];
=======
		rollSpriteSheet = (TextureRegion.split(new Texture("Stone_Gem_Ladder.png"), WIDTHPIXEL, HEIGHTPIXEL))[rollRow][rollCol];
>>>>>>> baa36093d08b481b20648b981cf062dd91b33481

	}

	float dx;
	float dy;
	float ds;
	
	public void update(float dt) {
		dx = player.getPosition().x - position.x;
		dy = player.getPosition().y - position.y;
		ds =(float) Math.pow(Math.pow(dx, 2) + Math.pow(dy, 2), 1/2);
		velocity.x = (dx/ds)*SPEED/1000;
		velocity.y = (dy/ds)*SPEED/1000;
		
		if(dx == 0 && dy == 0) {
			rollRow = 100;
			rollCol = 100;
		}
		
		velocity.scl(dt);
		position.add(velocity.x, velocity.y);
		velocity.scl(1/dt);
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public TextureRegion getRollSpriteSheet() {
		return rollSpriteSheet;
	}

	public void setRollSpriteSheet(TextureRegion rollSpriteSheet) {
		this.rollSpriteSheet = rollSpriteSheet;
	}
	
	
	
}
