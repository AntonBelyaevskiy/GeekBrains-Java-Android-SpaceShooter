package ru.geekbrains.stargame.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.base.Sprite;

public class Button extends Sprite {

    public Button(TextureRegion region, float x, float y, float height) {
        super(region);
        pos.set(x, y);
        setHeightProportion(height);
    }

    @Override
    public void touchDown(Vector2 touch, int pointer) {
        if(isMe(touch)){
            scale = 0.9f;
        }
    }

    @Override
    public void touchUp(Vector2 touch, int pointer) {
            scale = 1f;
    }
}
