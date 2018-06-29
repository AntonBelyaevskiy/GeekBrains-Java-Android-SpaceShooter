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

    private Texture img;

    public MenuScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        img = new Texture("img/android.png");
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(img,-21f,-21f,42f,42f);
        batch.end();
    }

    @Override
    public void dispose() {
        img.dispose();
        super.dispose();
    }

    @Override
    public void touchDown(Vector2 touch, int pointer) {
        super.touchDown(touch, pointer);
    }

    @Override
    public void touchUp(Vector2 touch, int pointer) {
        super.touchUp(touch, pointer);
    }
}

