package ru.geekbrains.stargame.sprite.enemy;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.stargame.base.EnemyShip;
import ru.geekbrains.stargame.math.Rnd;


public class SmallEnemy extends EnemyShip {

    public SmallEnemy(TextureAtlas atlas) {
        super(atlas.findRegion("enemy0"), 1, 2, 2);
        vx = 0f;
        vy = -0.3f;
        v.set(vx, vy);
        SHIP_HEIGHT = 0.1f;
        setHeightProportion(SHIP_HEIGHT);
    }
}
