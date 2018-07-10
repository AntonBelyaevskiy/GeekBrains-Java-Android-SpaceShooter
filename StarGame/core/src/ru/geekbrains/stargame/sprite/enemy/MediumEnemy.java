package ru.geekbrains.stargame.sprite.enemy;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.stargame.base.EnemyShip;

public class MediumEnemy extends EnemyShip {
    public MediumEnemy(TextureAtlas atlas) {
        super(atlas.findRegion("enemy1"), 1, 2, 2);
        vx = 0f;
        vy = -0.2f;
        v.set(vx, vy);
        SHIP_HEIGHT = 0.15f;
        setHeightProportion(SHIP_HEIGHT);
    }
}
