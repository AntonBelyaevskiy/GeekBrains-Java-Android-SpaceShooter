package ru.geekbrains.stargame.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.base.Base2DScreen;

/**
 * Экран меню
 */

public class MenuScreen extends Base2DScreen {
    public static final int MOUSE_SPEED = 60;
    public static final int CAT_SPEED = 10;
    private SpriteBatch batch;
    private Texture img;
    private Texture cat;
    private Vector2 posMouse;
    private Vector2 posNewMouse;
    private Vector2 posCat;


    public MenuScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        batch = new SpriteBatch();
        img = new Texture("img/mouse.png");
        cat = new Texture("img/cat.png");
        posMouse = new Vector2(0, 0);
        posCat = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        posNewMouse = new Vector2(0, 0);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //мышка бежит на клик курсора
        int dxMouse = (int) ((posNewMouse.x- posMouse.x)* MOUSE_SPEED / Gdx.graphics.getWidth());
        int dyMouse = (int) ((posNewMouse.y- posMouse.y)* MOUSE_SPEED / Gdx.graphics.getHeight());
        posMouse.x += dxMouse;
        posMouse.y += dyMouse;

        //кошка бегает за мышкой
        int dxCat = (int) ((posMouse.x-posCat.x-25)* CAT_SPEED / Gdx.graphics.getWidth());
        int dyCat = (int) ((posMouse.y-posCat.y-30)* CAT_SPEED / Gdx.graphics.getHeight());
        posCat.x += dxCat;
        posCat.y += dyCat;

        batch.begin();
        batch.draw(img, posMouse.x, posMouse.y, 50, 50);
        batch.draw(cat, posCat.x, posCat.y, 100, 100);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        super.dispose();
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        posNewMouse.set(screenX-25, Gdx.graphics.getHeight() - screenY-30);
        return true;
    }
}

