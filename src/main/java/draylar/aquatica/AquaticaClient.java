package draylar.aquatica;

import draylar.aquatica.client.IslandGeneratorType;
import draylar.aquatica.client.block.ChairBlockEntityRenderer;
import draylar.aquatica.client.entity.ChairEntityRenderer;
import draylar.aquatica.network.ClientNetworking;
import draylar.aquatica.registry.AquaticaEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.SkeletonEntityRenderer;

@Environment(EnvType.CLIENT)
public class AquaticaClient implements ClientModInitializer {

    public static final IslandGeneratorType A = new IslandGeneratorType("aquatica");

    @Override
    public void onInitializeClient() {
        ClientNetworking.init();

        EntityRendererRegistry.INSTANCE.register(AquaticaEntities.PIRATE, (dispatcher, context) -> new SkeletonEntityRenderer(dispatcher));
        EntityRendererRegistry.INSTANCE.register(AquaticaEntities.CHAIR, (dispatcher, context) -> new ChairEntityRenderer(dispatcher));

        BlockEntityRendererRegistry.INSTANCE.register(AquaticaEntities.CHAIR_BE, ChairBlockEntityRenderer::new);
    }
}
