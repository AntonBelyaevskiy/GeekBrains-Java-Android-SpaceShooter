package ru.geekbrains.stargame.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.stargame.base.ActionListener;
import ru.geekbrains.stargame.base.ScaledTouchUpButton;
import ru.geekbrains.stargame.math.Rect;

public class ButtonNewGame extends ScaledTouchUpButton {

    private static final float HEIGHT = 0.1f;
    private static final float BOTTOM_MARGIN = 0.07f;
    private static final float PRESS_SCALE = 0.9f;

    public ButtonNewGame(TextureAtlas atlas, ActionListener actionListener, Rect worldBounds) {
        super(atlas.findRegion("new_game"), actionListener, PRESS_SCALE);
        setHeightProportion(HEIGHT);
        setBottom(worldBounds.getBottom() + BOTTOM_MARGIN);
    }
}