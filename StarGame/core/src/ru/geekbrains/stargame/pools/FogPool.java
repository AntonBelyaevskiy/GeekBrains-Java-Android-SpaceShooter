package ru.geekbrains.stargame.pools;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekbrains.stargame.base.SpritesPool;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.sprite.Fog;

public class FogPool extends SpritesPool<Fog> {

    private TextureRegion textureRegion;
    private Rect worldBounds;

    public FogPool(TextureAtlas atlas, Rect worldBounds) {
        this.textureRegion = atlas.findRegion("fog");
        this.worldBounds = worldBounds;
    }

    @Override
    protected Fog newObject() {
        return new Fog(textureRegion, 5,5,25, worldBounds);
    }
}
