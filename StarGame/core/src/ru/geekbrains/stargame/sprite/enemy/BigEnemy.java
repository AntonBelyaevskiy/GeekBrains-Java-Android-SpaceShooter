package ru.geekbrains.stargame.sprite.enemy;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.stargame.base.EnemyShip;

public class BigEnemy extends EnemyShip {
    public BigEnemy(TextureAtlas atlas) {
        super(atlas.findRegion("enemy1"), 1, 2, 2);
        vx = 0f;
        vy = -0.1f;
        v.set(vx, vy);
        SHIP_HEIGHT = 0.2f;
        setHeightProportion(SHIP_HEIGHT);
    }
}
