package ru.geekbrains.stargame.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

import ru.geekbrains.stargame.base.Base2DScreen;
import ru.geekbrains.stargame.base.Sprite;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.math.Rnd;
import ru.geekbrains.stargame.sprite.Background;
import ru.geekbrains.stargame.sprite.Button;
import ru.geekbrains.stargame.sprite.Star;

/**
 * Экран меню
 */

public class MenuScreen extends Base2DScreen {

    public static final int STARS_NUM = 256;
    private Background background;
    private Texture bg;

    private TextureAtlas atlas;
    private List<Star> stars;

    private Button play;
    private Button exit;

    public MenuScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        background = new Background(new TextureRegion(bg));

        atlas = new TextureAtlas("textures/menuAtlas.tpack");
        TextureRegion starRegion = atlas.findRegion("star");
        stars = new ArrayList<Star>();
        for (int i = 0; i < STARS_NUM; i++) {
            stars.add(new Star(starRegion, Rnd.nextFloat(-0.005f, 0.005f), Rnd.nextFloat(-0.5f, -0.1f), Rnd.nextFloat(0.0008f, 0.009f)));
        }

        TextureRegion playBtnRegion = atlas.findRegion("btPlay");
        play = new Button(playBtnRegion, -0.25f,-0.35f,0.2f);
        TextureRegion exitBtnRegion = atlas.findRegion("btExit");
        exit = new Button(exitBtnRegion, 0.25f,-0.35f,0.2f);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    public void update(float delta) {
        for(Star star : stars){
            star.update(delta);
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
        play.draw(batch);
        exit.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        super.dispose();
    }


    @Override
    public void touchDown(Vector2 touch, int pointer) {
        play.touchDown(touch, pointer);
        exit.touchDown(touch, pointer);

    }

    @Override
    public void touchUp(Vector2 touch, int pointer) {
        play.touchUp(touch, pointer);
        exit.touchUp(touch, pointer);

    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for(Star star : stars){
            star.resize(worldBounds);
        }
    }
}

