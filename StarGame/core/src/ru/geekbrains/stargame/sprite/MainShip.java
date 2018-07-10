package ru.geekbrains.stargame.sprite;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.base.ActionListener;
import ru.geekbrains.stargame.base.Sprite;
import ru.geekbrains.stargame.math.Rect;

public class MainShip extends Sprite {

    public static final float SHIP_HEIGHT = 0.15f;
    public static final float BOTTOM_MARGIN = 0.05f;

    private Vector2 v = new Vector2();
    private Vector2 stop = new Vector2();
    private Vector2 v0 = new Vector2(0.5f, 0f);

    private boolean pressLeft;
    private boolean pressRight;
    private boolean mouseControl;

    private Rect worldBounds;

    public MainShip(TextureAtlas atlas) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        setHeightProportion(SHIP_HEIGHT);
    }

    @Override
    public void resize(Rect worldBounds) {
        setBottom(worldBounds.getBottom() + BOTTOM_MARGIN);
        this.worldBounds = worldBounds;
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        checkAndHandleBounds();
        if (mouseControl)
            checkStopOnCursor();
    }


    public void keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressLeft = true;
                moveLeft();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressRight = true;
                moveRight();
                break;
        }
    }

    public void keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressLeft = false;
                if (pressRight) {
                    moveRight();
                } else {
                    stop();
                }
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressRight = false;
                if (pressLeft) {
                    moveLeft();
                } else {
                    stop();
                }
                break;
        }
    }


    @Override
    public void touchDown(Vector2 touch, int pointer) {
        mouseControl = true;
        if (touch.y > getBottom() && touch.y < getTop()) {
            stop.set(touch);
            if (touch.x > getRight()) {
                pressRight = true;
                pressLeft = false;
                moveRight();
            }
            if (touch.x < getLeft()) {
                pressLeft = true;
                pressRight = false;
                moveLeft();
            }
        }
    }

    @Override
    public void touchUp(Vector2 touch, int pointer) {
        mouseControl = false;
        stop();
    }

    private void moveLeft() {
        v.set(v0).rotate(180);
    }

    private void moveRight() {
        v.set(v0);
    }

    private void stop() {
        v.setZero();
    }

    private void checkAndHandleBounds() {
        if (getRight() >= worldBounds.getRight()) setRight(worldBounds.getRight());
        if (getLeft() <= worldBounds.getLeft()) setLeft(worldBounds.getLeft());
    }

    private void checkStopOnCursor() {
        if (pressRight)
            if (pos.x >= stop.x) stop();
        if (pressLeft)
            if (pos.x <= stop.x) stop();
    }


    public void mouseMoved(Vector2 touch) {
//        if (touch.y > getBottom() && touch.y < getTop()) {
//            stop.set(touch);
//            if (touch.x > getRight()) {
//                pressRight = true;
//                pressLeft = false;
//                moveRight();
//            }
//            if (touch.x < getLeft()) {
//                pressLeft = true;
//                pressRight = false;
//                moveLeft();
//            }
//        }
    }
}
