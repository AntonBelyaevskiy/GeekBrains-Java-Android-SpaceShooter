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

import ru.geekbrains.stargame.base.ActionListener;
import ru.geekbrains.stargame.base.Base2DScreen;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.math.Rnd;
import ru.geekbrains.stargame.sprite.Background;
import ru.geekbrains.stargame.sprite.ButtonExit;
import ru.geekbrains.stargame.sprite.ButtonPlay;
import ru.geekbrains.stargame.sprite.Star;

/**
 * Экран меню
 */

public class MenuScreen extends Base2DScreen implements ActionListener{

    public static final int STARS_NUM = 30;

    public static final float PRESS_SCALE = 0.9f;
    public static final float BUTTON_HEIGHT = 0.15f;

    private Background background;
    private Texture bg;

    private Texture sg;

    private TextureAtlas atlas;
    private List<Star> stars;

    private ButtonExit buttonExit;
    private ButtonPlay buttonPlay;


    public MenuScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        background = new Background(new TextureRegion(bg));

        sg = new Texture("textures/stargame.png");

        atlas = new TextureAtlas("textures/menuAtlas.tpack");
        TextureRegion starRegion = atlas.findRegion("star");
        stars = new ArrayList<Star>();
        for (int i = 0; i < STARS_NUM; i++) {
            stars.add(new Star(starRegion, Rnd.nextFloat(-0.005f, 0.005f), Rnd.nextFloat(-0.5f, -0.1f), Rnd.nextFloat(0.0008f, 0.009f)));
        }

        buttonExit = new ButtonExit(atlas, this, PRESS_SCALE);
        buttonExit.setHeightProportion(BUTTON_HEIGHT);

        buttonPlay = new ButtonPlay(atlas, this, PRESS_SCALE);
        buttonPlay.setHeightProportion(BUTTON_HEIGHT);

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
        buttonExit.draw(batch);
        buttonPlay.draw(batch);
        batch.draw(sg, -0.3f,-0.2f,0.6f,0.6f);
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
        super.touchDown(touch, pointer);
        buttonExit.touchDown(touch, pointer);
        buttonPlay.touchDown(touch, pointer);
    }

    @Override
    public void touchUp(Vector2 touch, int pointer) {
        super.touchDown(touch, pointer);
        buttonExit.touchUp(touch, pointer);
        buttonPlay.touchUp(touch, pointer);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for(Star star : stars){
            star.resize(worldBounds);
        }
        buttonExit.resize(worldBounds);
        buttonPlay.resize(worldBounds);
    }

    @Override
    public void actionPerformed(Object src) {
        if(src == buttonExit){
            Gdx.app.exit();
        }else if(src == buttonPlay){
            game.setScreen(new GameScreen(game));
        }else{
            throw new RuntimeException("Unknown src");
        }
    }
}

