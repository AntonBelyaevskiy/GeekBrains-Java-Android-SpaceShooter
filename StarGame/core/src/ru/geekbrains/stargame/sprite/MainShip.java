package ru.geekbrains.stargame.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.base.Ship;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.pools.BulletPool;

public class MainShip extends Ship {

    private Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/piu.wav"));

    public static final float SHIP_HEIGHT = 0.15f;
    public static final float BOTTOM_MARGIN = 0.04f;
    private static final int INVALID_POINTER = -1;


    private Vector2 v0 = new Vector2(0.5f, 0f);

    private boolean pressLeft;
    private boolean pressRight;


    private int leftPointer = INVALID_POINTER;
    private int rightPointer = INVALID_POINTER;

    private boolean autoMode; //автоматический режим стрельбы включается кнопкой Q

    public MainShip(TextureAtlas atlas, BulletPool bulletPool) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        setHeightProportion(SHIP_HEIGHT);
        this.bulletPool = bulletPool;
        this.bulletRegion = atlas.findRegion("bulletMainShip");
        this.bulletHeight = 0.013f;
        this.bulletV.set(0, 0.8f);
        this.bulletDamage = 1;
        this.reloadInterval = 0.2f;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom() + BOTTOM_MARGIN);
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        reloadTimer += delta;
        if(autoMode){
            if(reloadTimer >= reloadInterval){
                reloadTimer = 0f;
                shoot();
            }
        }
        checkAndHandleBounds();
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
            case Input.Keys.W:
            case Input.Keys.UP:
                shoot();
                break;
            case Input.Keys.Q:
                if(autoMode){
                    autoMode = false;
                    break;
                }
                autoMode = true;
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
        if (touch.x < worldBounds.pos.x) {
            if (leftPointer != INVALID_POINTER) {
                return;
            }
            leftPointer = pointer;
            moveLeft();
        } else {
            if (rightPointer != INVALID_POINTER) {
                return;
            }
            rightPointer = pointer;
            moveRight();
        }
    }

    @Override
    public void touchUp(Vector2 touch, int pointer) {
        if (pointer == leftPointer) {
            leftPointer = INVALID_POINTER;
            if (rightPointer != INVALID_POINTER) {
                moveRight();
            } else {
                stop();
            }
        } else if (pointer == rightPointer) {
            rightPointer = INVALID_POINTER;
            if (leftPointer != INVALID_POINTER) {
                moveLeft();
            } else {
                stop();
            }
        }
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
        if (getRight() > worldBounds.getRight()) {
            setRight(worldBounds.getRight());
            stop();
        }
        if (getLeft() < worldBounds.getLeft()) {
            setLeft(worldBounds.getLeft());
            stop();
        }
    }

    @Override
    protected void shoot() {
        super.shoot();
        sound.play();
    }
}
