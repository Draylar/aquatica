package draylar.aquatica.client.entity;

import draylar.aquatica.entity.ChairEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.util.Identifier;

public class ChairEntityRenderer extends EntityRenderer<ChairEntity> {

    public ChairEntityRenderer(EntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public Identifier getTexture(ChairEntity entity) {
        return null;
    }
}
