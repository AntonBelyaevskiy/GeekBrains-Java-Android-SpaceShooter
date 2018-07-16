package ru.geekbrains.stargame.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

import ru.geekbrains.stargame.base.ActionListener;
import ru.geekbrains.stargame.base.Base2DScreen;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.math.Rnd;
import ru.geekbrains.stargame.pools.BulletPool;
import ru.geekbrains.stargame.pools.EnemyPool;
import ru.geekbrains.stargame.pools.ExplosionPool;
import ru.geekbrains.stargame.sprite.Background;
import ru.geekbrains.stargame.sprite.Bullet;
import ru.geekbrains.stargame.sprite.ButtonNewGame;
import ru.geekbrains.stargame.sprite.Enemy;
import ru.geekbrains.stargame.sprite.Explosion;
import ru.geekbrains.stargame.sprite.GameOver;
import ru.geekbrains.stargame.sprite.MainShip;
import ru.geekbrains.stargame.sprite.Star;
import ru.geekbrains.stargame.utils.EnemiesEmitter;

public class GameScreen extends Base2DScreen implements ActionListener {

    private enum State {GAME, GAME_OVER}

    private State state;

    private static final int STARS_NUM = 60;

    private Background background1;
    private Background background2;

    private Texture bg;

    private TextureAtlas atlas;
    private List<Star> stars;

    private MainShip mainShip;

    private BulletPool bulletPool;
    private EnemyPool enemyPool;
    private ExplosionPool explosionPool;

    private EnemiesEmitter enemiesEmitter;

    private Music backgroundMusic;

    private Sound explosionSound;
    private Sound mainShipBulletSound;
    private Sound enemyShipBulletSound;

    private ButtonNewGame buttonNewGame;
    private static final float PRESS_SCALE = 0.9f;
    private static final float BUTTON_HEIGHT = 0.05f;

    private GameOver gameOver;

    GameScreen(Game game) {
        super(game);
        state = State.GAME;
    }

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        background1 = new Background(new TextureRegion(bg), true);
        background2 = new Background(new TextureRegion(bg), false);

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/fon.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.play();

        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));

        mainShipBulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/piu.wav"));
        enemyShipBulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/ph.wav"));

        atlas = new TextureAtlas("textures/mainAtlas.tpack");

        gameOver = new GameOver(atlas.findRegion("message_game_over"), 0.1f);

        buttonNewGame = new ButtonNewGame(atlas, this, PRESS_SCALE);
        buttonNewGame.setHeightProportion(BUTTON_HEIGHT);

        TextureRegion starRegion = atlas.findRegion("star");
        stars = new ArrayList<Star>();
        for (int i = 0; i < STARS_NUM; i++) {
            stars.add(new Star(starRegion, Rnd.nextFloat(-0.005f, 0.005f), Rnd.nextFloat(-0.5f, -0.1f), Rnd.nextFloat(0.0008f, 0.009f)));
        }
        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlas, explosionSound);
        mainShip = new MainShip(atlas, bulletPool, explosionPool, mainShipBulletSound);
        enemyPool = new EnemyPool(bulletPool, worldBounds, explosionPool, mainShip, enemyShipBulletSound);
        enemiesEmitter = new EnemiesEmitter(worldBounds, enemyPool, atlas);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollision();
        deleteAllDestroyed();
        draw();
    }

    public void update(float delta) {
        switch (state) {
            case GAME:
                background1.update(delta);
                background2.update(delta);
                for (Star star : stars) {
                    star.update(delta);
                }
                mainShip.update(delta);
                bulletPool.updateActiveSprites(delta);
                enemyPool.updateActiveSprites(delta);
                explosionPool.updateActiveSprites(delta);
                enemiesEmitter.generateEnemies(delta);
                if(mainShip.isDestroyed()){
                    state = State.GAME_OVER;
                    backgroundMusic.stop();
                }
                break;
            case GAME_OVER:
                bulletPool.updateActiveSprites(delta);
                explosionPool.updateActiveSprites(delta);
                break;
        }
    }

    private void draw() {

        switch (state) {
            case GAME:
                Gdx.gl.glClearColor(0, 0, 0, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                batch.begin();
                background1.draw(batch);
                background2.draw(batch);
                for (Star star : stars) {
                    star.draw(batch);
                }
                mainShip.draw(batch);
                bulletPool.drawActiveSprites(batch);
                enemyPool.drawActiveSprites(batch);
                explosionPool.drawActiveSprites(batch);
                batch.end();
                break;
            case GAME_OVER:
                Gdx.gl.glClearColor(0, 0, 0, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                batch.begin();

                background1.draw(batch);
                background2.draw(batch);
                for (Star star : stars) {
                    star.draw(batch);
                }
                enemyPool.drawActiveSprites(batch);
                bulletPool.drawActiveSprites(batch);
                explosionPool.drawActiveSprites(batch);

                gameOver.draw(batch);
                buttonNewGame.draw(batch);
                batch.end();
                break;
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background1.resize(worldBounds);
        background2.resize(worldBounds);

        for (Star star : stars) {
            star.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
        buttonNewGame.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        enemyPool.dispose();
        explosionPool.dispose();
        backgroundMusic.dispose();
        explosionSound.dispose();
        mainShipBulletSound.dispose();
        enemyShipBulletSound.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        mainShip.keyUp(keycode);
        return false;
    }

    @Override
    public void touchDown(Vector2 touch, int pointer) {
        mainShip.touchDown(touch, pointer);
        buttonNewGame.touchDown(touch, pointer);
    }

    @Override
    public void touchUp(Vector2 touch, int pointer) {
        mainShip.touchUp(touch, pointer);
        buttonNewGame.touchUp(touch, pointer);
    }

    private void checkCollision() {
        List<Enemy> enemyList = enemyPool.getActiveObjects();
        for (Enemy enemy : enemyList) {
            if (enemy.isDestroyed()) {
                continue;
            }

            float minDist = enemy.getHalfWidth() + mainShip.getHalfWidth();
            if (enemy.pos.dst2(mainShip.pos) < minDist * minDist) {
                enemy.destroy();
                enemy.boom();
                mainShip.damage(enemy.getBoomDamagePower());
                return;
            }
        }

        List<Bullet> bulletList = bulletPool.getActiveObjects();

        for (Bullet bullet : bulletList) {
            if (bullet.isDestroyed() || bullet.getOwner() == mainShip) {
                continue;
            }
            if (mainShip.isBulletCollision(bullet)) {
                mainShip.damage(bullet.getDamage());
                bullet.destroy();
            }
        }

        for (Enemy enemy : enemyList) {
            if (enemy.isDestroyed()) {
                continue;
            }
            for (Bullet bullet : bulletList) {
                if (bullet.getOwner() != mainShip || bullet.isDestroyed()) {
                    continue;
                }
                if (enemy.isBulletCollision(bullet)) {
                    enemy.damage(bullet.getDamage());
                    bullet.destroy();
                }
            }
        }
    }

    private void deleteAllDestroyed() {
        bulletPool.freeAllDestroyedActiveSprites();
        enemyPool.freeAllDestroyedActiveSprites();
        explosionPool.freeAllDestroyedActiveSprites();
    }


    @Override
    public void actionPerformed(Object src) {
        if (src == buttonNewGame) {
            game.setScreen(new GameScreen(game));
        } else {
            throw new RuntimeException("Unknown src");
        }
    }
}