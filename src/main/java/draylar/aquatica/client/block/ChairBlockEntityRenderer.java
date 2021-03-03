package draylar.aquatica.client.block;

import draylar.aquatica.entity.ChairBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ChairBlockEntityRenderer extends BlockEntityRenderer<ChairBlockEntity> {

    public ChairBlockEntityRenderer(BlockEntityRenderDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(ChairBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        BlockState cachedState = entity.getCachedState();
        Block block = cachedState.getBlock();
        Item item = block.asItem();

        if(item != null) {
            matrices.push();

            matrices.translate(.5, .5, .5);
            matrices.scale(2, 2, 2);
            matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-(float) entity.getRotation() - 180));

            MinecraftClient.getInstance().getItemRenderer().renderItem(
                    new ItemStack(item),
                    ModelTransformation.Mode.FIXED,
                    light,
                    overlay,
                    matrices,
                    vertexConsumers
            );

            matrices.pop();
        }
    }
}
