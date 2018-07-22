package ru.geekbrains.stargame.utils;

import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.math.Rnd;
import ru.geekbrains.stargame.pools.AsteroidPool;
import ru.geekbrains.stargame.sprite.Asteroid;

public class AsteroidEmitter {

    private float generateInterval = 4f;
    private float generateTimer;
    private Rect worldBounds;

    private Vector2 v0;

    private AsteroidPool asteroidPool;

    public AsteroidEmitter(AsteroidPool asteroidPool, Rect worldBounds) {
        this.asteroidPool = asteroidPool;
        this.worldBounds = worldBounds;
    }

    public void generateAsteroid(float delta) {
        v0 = new Vector2(0f, Rnd.nextFloat(-1.3f, -0.4f));
        generateTimer += delta;
        if (generateTimer >= generateInterval) {
            generateTimer = 0f;
            Asteroid asteroid = asteroidPool.obtain();
            asteroid.setBottom(worldBounds.getTop());
            asteroid.pos.x = Rnd.nextFloat(
                    worldBounds.getLeft() + asteroid.getHalfWidth(),
                    worldBounds.getRight() - asteroid.getHalfWidth());
            asteroid.setV(v0);
        }
    }
}
