package ru.geekbrains.stargame.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.utils.Regions;

public class Sprite extends Rect {

    protected float angle;
    protected float scale = 1f;
    protected TextureRegion[] regions;
    protected int frame;
    private boolean isDestroyed;

    public Sprite() {

    }

    public Sprite(TextureRegion region) {
        if (region == null) {
            throw new NullPointerException();
        }
        regions = new TextureRegion[1];
        regions[0] = region;
    }

    public Sprite(TextureRegion region, int rows, int columns, int frames) {
        if (region == null) {
            throw new NullPointerException();
        }
        regions = Regions.split(region, rows, columns, frames);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(
                regions[frame],
                getLeft(), getBottom(),
                halfWidth, halfHeight,
                getWidth(), getHeight(),
                scale, scale,
                angle
        );
    }

    public void setHeightProportion(float hight) {
        setHeight(hight);
        float aspect = regions[frame].getRegionWidth() / (float) regions[frame].getRegionHeight();
        setWidth(hight * aspect);
    }

    public void resize(Rect worldBounds) {

    }

    public void touchDown(Vector2 touch, int pointer) {

    }

    public void touchUp(Vector2 touch, int pointer) {

    }

    public void update(float delta) {

    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void destroy(){
        this.isDestroyed = true;
    }

    public void flushDestrioy(){
        this.isDestroyed = false;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }
}
