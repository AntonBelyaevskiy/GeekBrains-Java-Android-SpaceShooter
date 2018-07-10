package ru.geekbrains.stargame.base;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.math.Rnd;

public abstract class EnemyShip extends Sprite implements Pool.Poolable {

    protected float SHIP_HEIGHT;

    protected Vector2 v = new Vector2();
    protected Rect worldBounds;

    protected float posX;
    protected float posY;

    protected float vx;
    protected float vy;

    protected boolean active;

    public EnemyShip(TextureRegion region, int rows, int columns, int frames) {
        super(region, rows, columns, frames);
    }


    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        posX = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
        posY = worldBounds.getTop() + getHeight();
        pos.set(posX, posY);
    }

    @Override
    public void update(float delta) {
        if(active){
            pos.mulAdd(v, delta);
            checkAndHandleBounds();
        }
    }


    private void checkAndHandleBounds() {
        if (getTop() < worldBounds.getBottom()) {

            posX = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
            pos.set(posX, posY);
            active = false;
            setBottom(worldBounds.getTop());
        }
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setup() {
        posX = Rnd.nextFloat(worldBounds.getLeft() + getWidth(), worldBounds.getRight() - getWidth());
        pos.set(posX, posY);
        active = true;
    }

    @Override
    public void reset() {
        active = false;
        pos.set(0, 0);
    }

    public void destroy() {
        active = false;
    }
}