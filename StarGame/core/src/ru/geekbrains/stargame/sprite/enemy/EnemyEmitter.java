package ru.geekbrains.stargame.sprite.enemy;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

import ru.geekbrains.stargame.base.EnemyShip;
import ru.geekbrains.stargame.math.Rect;

public class EnemyEmitter {

    TextureRegion texture;
    private TextureAtlas atlas;

    private static final EnemyEmitter instance = new EnemyEmitter();

    public static EnemyEmitter getInstance() {
        return instance;
    }

    public EnemyEmitter() {
        this.atlas = new TextureAtlas("textures/mainAtlas.tpack");
        texture = atlas.findRegion("enemy0");
    }

    final Array<EnemyShip> activeEnemy = new Array<EnemyShip>();
    final Pool<EnemyShip> enemyShipPool = new Pool<EnemyShip>(10,100) {
        @Override
        protected EnemyShip newObject() {
            return new SmallEnemy(atlas);
        }
    };

    public void reset() {
        activeEnemy.clear();
        enemyShipPool.clear();
    }

    public void update(float delta) {
        EnemyShip enemy;
        int length = activeEnemy.size;
        for (int i = length - 1; i >= 0; --i) {
            enemy = activeEnemy.get(i);
            enemy.update(delta);
            if (!enemy.isActive()) {
                activeEnemy.removeIndex(i);
                enemyShipPool.free(enemy);
            }
        }
    }

    public void render(SpriteBatch batch) {
        EnemyShip enemy;
        int length = activeEnemy.size;
        for (int i = length - 1; i >= 0; --i) {
            enemy = activeEnemy.get(i);
            batch.draw(texture, enemy.pos.x, enemy.pos.y);
        }
    }

    public void setupEnemy() {
        EnemyShip item = enemyShipPool.obtain();
        item.setup();
        activeEnemy.add(item);
    }

    public void resize(Rect worldBounds) {
        EnemyShip enemy;
        int length = activeEnemy.size;
        for (int i = length - 1; i >= 0; --i) {
            enemy = activeEnemy.get(i);
            enemy.resize(worldBounds);
        }
    }
}