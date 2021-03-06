package spawners;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import characters.Bat;
import characters.Character;
import characters.Enemy;
import characters.Ghost;
import characters.MainCharacter;
import characters.Serpent;

public class MonsterSpawner {
	private ArrayList<Enemy> monsterList;
	private ArrayList<Integer> markForRemoved;
	private int spawnRange;
	private MainCharacter player;
	private int level;
	private ItemSpawner itemSpawner;

	public MonsterSpawner(MainCharacter player, int spawnRange, int level, ItemSpawner itemSpawner) {
		monsterList = new ArrayList<Enemy>();
		this.player = player;
		this.spawnRange = spawnRange;
		this.level = level;
		this.itemSpawner = itemSpawner;
	}

	public void spawnMonster(int amount) {
		Random rand = new Random();
		Vector2 pos = new Vector2();
		int temp;
		int monster;
		for (int i = 0; i < amount; i++) {
			temp = rand.nextInt(360);
			monster = rand.nextInt(3);
			pos.x = (float) (player.getPosition().x + (Math.cos(Math.toRadians(temp)) * spawnRange));
			pos.y = (float) (player.getPosition().y + (Math.sin(Math.toRadians(temp)) * spawnRange));
			if (monster == 0) {
				monsterList.add(new Ghost((int) pos.x, (int) pos.y, player, (level / 5) * 3 + 3));
			} else if (monster == 1) {
				monsterList.add(new Serpent((int) pos.x, (int) pos.y, player, (level / 5) + 1));
			} else if (monster == 2) {
				monsterList.add(new Bat((int) pos.x, (int) pos.y, player, (level / 5) * 2 + 2));
			}
		}
	}

	public void render(SpriteBatch batch) {
		for (Character monster : monsterList) {
			if (monster != null) {
				if (monster instanceof Serpent) {
					batch.draw(monster.getAnimation().getKeyFrame(monster.getStateTime(), true),
							monster.getPosition().x, monster.getPosition().y, monster.getRenderWidth() / 2,
							monster.getRenderHeight() / 2, monster.getRenderWidth(), monster.getRenderHeight(), 1, 1,
							(float) monster.getAngle() - 90);
				} else if (monster instanceof Ghost) {
					batch.draw(monster.getAnimation().getKeyFrame(monster.getStateTime(), true),
							monster.getPosition().x, monster.getPosition().y, monster.getRenderWidth(),
							monster.getRenderHeight());
				} else if (monster instanceof Bat) {
					batch.draw(monster.getAnimation().getKeyFrame(monster.getStateTime(), true),
							monster.getPosition().x, monster.getPosition().y, monster.getRenderWidth(),
							monster.getRenderHeight());
				}
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
			itemSpawner.dropValueable((int) monsterList.get(markForRemoved.get(i)).getPosition().x,
					(int) monsterList.get(markForRemoved.get(i)).getPosition().y);
			monsterList.remove(monsterList.get(markForRemoved.get(i)));
		}
	}

	public ArrayList<Enemy> getMonsterList() {
		return monsterList;
	}

	public void checkAttack() {
		for (int i = 0; i < monsterList.size(); i++) {
			monsterList.get(i).addHP(player.attack(monsterList.get(i)));
		}
	}
}
