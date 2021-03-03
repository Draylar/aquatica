package draylar.aquatica.world.features;

import com.mojang.serialization.Codec;
import draylar.aquatica.block.StarfishBlock;
import draylar.aquatica.registry.AquaticaBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class StarfishFeature extends Feature<DefaultFeatureConfig> {

    public StarfishFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(StructureWorldAccess world, ChunkGenerator chunkGenerator, Random random, BlockPos pos, DefaultFeatureConfig config) {
        int generated = 0;

        for(int count = 0; count < 15; ++count) {
            int int_3 = random.nextInt(8) - random.nextInt(8);
            int int_4 = random.nextInt(8) - random.nextInt(8);
            int int_5 = world.getTopY(Heightmap.Type.OCEAN_FLOOR, pos.getX() + int_3, pos.getZ() + int_4);
            BlockPos blockPos_2 = new BlockPos(pos.getX() + int_3, int_5, pos.getZ() + int_4);
            BlockState blockState_1 = AquaticaBlocks.STARFISH.getDefaultState().with(StarfishBlock.WATERLOGGED, true);
            if (world.getBlockState(blockPos_2).getBlock() == Blocks.WATER && blockState_1.canPlaceAt(world, blockPos_2)) {
                world.setBlockState(blockPos_2, blockState_1, 2);
                ++generated;
            }
        }

        return generated > 0;
    }
}
