package ru.geekbrains.stargame.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ru.geekbrains.stargame.base.Base2DScreen;
import ru.geekbrains.stargame.base.EnemyShip;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.math.Rnd;
import ru.geekbrains.stargame.sprite.Background;
import ru.geekbrains.stargame.sprite.MainShip;
import ru.geekbrains.stargame.sprite.Star;
import ru.geekbrains.stargame.sprite.enemy.BigEnemy;
import ru.geekbrains.stargame.sprite.enemy.EnemyEmitter;
import ru.geekbrains.stargame.sprite.enemy.MediumEnemy;
import ru.geekbrains.stargame.sprite.enemy.SmallEnemy;

public class GameScreen extends Base2DScreen {

    public static final int STARS_NUM = 60;

    private Background background;
    private Texture bg;

    private TextureAtlas atlas;
    private List<Star> stars;

    private MainShip mainShip;

    private float generateInterval = 4f;
    private float generateTimer;

    Random random;

//    private EnemyEmitter enemyEmitter;

    private List<EnemyShip> enemies;

    public GameScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        background = new Background(new TextureRegion(bg));

        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        TextureRegion starRegion = atlas.findRegion("star");
        stars = new ArrayList<Star>();
        for (int i = 0; i < STARS_NUM; i++) {
            stars.add(new Star(starRegion, Rnd.nextFloat(-0.005f, 0.005f), Rnd.nextFloat(-0.5f, -0.1f), Rnd.nextFloat(0.0008f, 0.009f)));
        }
        mainShip = new MainShip(atlas);
//        enemyEmitter = EnemyEmitter.getInstance();

        enemies = new ArrayList<EnemyShip>();
        for (int i = 0; i < 6; i++) {
            enemies.add(new SmallEnemy(atlas));
        }
        for (int i = 0; i < 3; i++) {
            enemies.add(new MediumEnemy(atlas));
        }
        for (int i = 0; i < 1; i++) {
            enemies.add(new BigEnemy(atlas));
        }
        random = new Random();
        enemies.get(random.nextInt(10)).setActive(true);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
//        enemyEmitter.render(batch);
        update(delta);
        checkCollision();
        deleteAllDectroy();
        draw();
    }

    public void update(float delta) {
        for(Star star : stars){
            star.update(delta);
        }

        mainShip.update(delta);
        generateTimer += delta;
        if (generateInterval <= generateTimer) {
            generateTimer = 0f;
            enemies.get(random.nextInt(10)).setActive(true);
        }

        for(EnemyShip enemy : enemies){
            enemy.update(delta);
        }
    }

    public void draw() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        background.draw(batch);
        for(Star star : stars){
            star.draw(batch);
        }
        mainShip.draw(batch);
//        enemyEmitter.render(batch);
        for(EnemyShip enemy : enemies){
            enemy.draw(batch);
        }

        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for(Star star : stars){
            star.resize(worldBounds);
        }
        for(EnemyShip enemy : enemies){
            enemy.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
//        enemyEmitter.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
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
        super.touchDown(touch, pointer);
        mainShip.touchDown(touch, pointer);
    }

    @Override
    public void touchUp(Vector2 touch, int pointer) {
        super.touchDown(touch, pointer);
        mainShip.touchUp(touch, pointer);
    }

    @Override
    public void mouseMoved(Vector2 touch) {
        super.mouseMoved(touch);
        mainShip.mouseMoved(touch);
    }

    public void checkCollision(){
    }

    public void deleteAllDectroy(){
    }
}
