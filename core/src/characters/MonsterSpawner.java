package characters;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class MonsterSpawner {
	private ArrayList<Enemy> monsterList;
	private ArrayList<Integer> markForRemoved;
	
	private int spawnRange;
	
	private MainCharacter player;

	public MonsterSpawner(MainCharacter player, int spawnRange) {
		monsterList = new ArrayList<Enemy>();
		this.player = player;
		this.spawnRange = spawnRange;
	}

	public void spawnMonster(int amount) {
		Random rand = new Random();
		Vector2 pos = new Vector2();
		int temp;
		int monster;
		for(int i = 0;i<amount;i++) {
			temp = rand.nextInt(360);
			monster = 0;
			pos.x = (float)(player.getPosition().x + (Math.cos(Math.toRadians(temp)) * spawnRange));
			pos.y = (float)(player.getPosition().y + (Math.sin(Math.toRadians(temp)) * spawnRange));
			if(monster == 0) {
				monsterList.add(new Ghost((int)pos.x, (int)pos.y, player));
			}
		}	
	}

	public void render(SpriteBatch batch, float stateTime) {
		for (Character monster : monsterList) {
			if (monster != null) {
				batch.draw(monster.getAnimation().getKeyFrame(stateTime, true), monster.getPosition().x,
						monster.getPosition().y, monster.getRenderWidth(), monster.getRenderHeight());
			}
		}
	}

	public void update(float dt) {
		markForRemoved = new ArrayList<Integer>();
		for (int i = 0; i < monsterList.size(); i++) {
			if (monsterList.get(i) != null) {
				monsterList.get(i).update(dt);
				if (monsterList.get(i).isDestroyed()) {
					markForRemoved.add(i);
				}
			}
		}
		for (int i = markForRemoved.size() - 1; i >= 0; i--) {
			monsterList.remove(monsterList.get(markForRemoved.get(i)));
		}
	}

	public ArrayList<Enemy> getMonsterList() {
		return monsterList;
	}
	
	public void checkAttack() {
		for (int i = 0;i<monsterList.size();i++) {
			monsterList.get(i).addHP(player.attack(monsterList.get(i)));
		}
	}
}
