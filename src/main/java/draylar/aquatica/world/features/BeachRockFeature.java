package draylar.aquatica.world.features;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BeachRockFeature extends Feature<DefaultFeatureConfig> {

    public static final List<Block> ROCK_BLOCKS = Arrays.asList(Blocks.STONE, Blocks.COBBLESTONE, Blocks.ANDESITE, Blocks.GRAVEL);

    public BeachRockFeature() {
        super(DefaultFeatureConfig.CODEC);
    }

    @Override
    public boolean generate(StructureWorldAccess structureWorldAccess, ChunkGenerator chunkGenerator, Random random, BlockPos spawnPos, DefaultFeatureConfig defaultFeatureConfig) {
        while(structureWorldAccess.isAir(spawnPos) && spawnPos.getY() > 2) {
            spawnPos = spawnPos.down();
        }

        if (!structureWorldAccess.getBlockState(spawnPos).isOf(Blocks.SAND)) {
            return false;
        } else {
            spawnPos = spawnPos.up(random.nextInt(4));
            int height = random.nextInt(4) + 7;
            int middle = height / 4 + random.nextInt(2);
            if (middle > 1 && random.nextInt(60) == 0) {
                spawnPos = spawnPos.up(10 + random.nextInt(30));
            }

            int y;
            int l;
            for(y = 0; y < height; ++y) {
                float f = (1.0F - (float) y / (float)height) * (float)middle;
                l = MathHelper.ceil(f);

                for(int m = -l; m <= l; ++m) {
                    float g = (float)MathHelper.abs(m) - 0.25F;

                    for(int n = -l; n <= l; ++n) {
                        float h = (float)MathHelper.abs(n) - 0.25F;
                        if ((m == 0 && n == 0 || g * g + h * h <= f * f) && (m != -l && m != l && n != -l && n != l || random.nextFloat() <= 0.75F)) {
                            BlockState blockState = structureWorldAccess.getBlockState(spawnPos.add(m, y, n));
                            Block block = blockState.getBlock();
                            if (blockState.isAir() || isSoil(block) || block == Blocks.SANDSTONE || block == Blocks.SAND) {
                                this.setBlockState(structureWorldAccess, spawnPos.add(m, y, n), getRandomStone(random));
                            }

                            if (y != 0 && l > 1) {
                                blockState = structureWorldAccess.getBlockState(spawnPos.add(m, -y, n));
                                block = blockState.getBlock();
                                if (blockState.isAir() || isSoil(block) || block == Blocks.SANDSTONE || block == Blocks.SAND) {
                                    this.setBlockState(structureWorldAccess, spawnPos.add(m, -y, n), getRandomStone(random));
                                }
                            }
                        }
                    }
                }
            }

            y = middle - 1;
            if (y < 0) {
                y = 0;
            } else if (y > 1) {
                y = 1;
            }

            for(int p = -y; p <= y; ++p) {
                for(l = -y; l <= y; ++l) {
                    BlockPos blockPos2 = spawnPos.add(p, -1, l);
                    int r = 50;
                    if (Math.abs(p) == 1 && Math.abs(l) == 1) {
                        r = random.nextInt(5);
                    }

                    while(blockPos2.getY() > 50) {
                        BlockState blockState2 = structureWorldAccess.getBlockState(blockPos2);
                        Block block2 = blockState2.getBlock();
                        if (!blockState2.isAir() && !isSoil(block2) && block2 != Blocks.SAND && block2 != Blocks.SANDSTONE && !ROCK_BLOCKS.contains(block2)) {
                            break;
                        }

                        this.setBlockState(structureWorldAccess, blockPos2, getRandomStone(random));
                        blockPos2 = blockPos2.down();
                        --r;
                        if (r <= 0) {
                            blockPos2 = blockPos2.down(random.nextInt(5) + 1);
                            r = random.nextInt(5);
                        }
                    }
                }
            }

            return true;
        }
    }

    private BlockState getRandomStone(Random random) {
        return ROCK_BLOCKS.get(random.nextInt(ROCK_BLOCKS.size())).getDefaultState();
    }
}
