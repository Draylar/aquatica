package draylar.aquatica.world.features;

import draylar.aquatica.world.config.PalmTreeFeatureConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PalmTreeFeature extends Feature<PalmTreeFeatureConfig> {

    private static final List<Direction> horizontalDirections = Arrays.asList(
            Direction.NORTH,
            Direction.EAST,
            Direction.SOUTH,
            Direction.WEST
    );

    private static final List<Vec3d> offsets = Arrays.asList(
                                           new Vec3d(-1, 0, 0),
            new Vec3d(0, 0, -1), new Vec3d(0, 0, 0), new Vec3d(0, 0, 1),
                                           new Vec3d(0, 0, 0)
    );

    public PalmTreeFeature() {
        super(PalmTreeFeatureConfig.CODEC);
    }

    @Override
    public boolean generate(StructureWorldAccess world, ChunkGenerator chunkGenerator, Random random, BlockPos pos, PalmTreeFeatureConfig config) {
        BlockPos add = pos.down().add(0, world.getTopY(Heightmap.Type.WORLD_SURFACE, pos.getX(), pos.getZ()), 0);
        BlockState downState = world.getBlockState(add);

        if(downState.getBlock().equals(Blocks.SAND) || downState.getBlock().equals(Blocks.SANDSTONE) || downState.getBlock().equals(Blocks.GRASS_BLOCK)) {
            generate(world, random, add, config);
            return true;
        }

        return false;
    }

    public static void generate(ServerWorldAccess world, Random random, BlockPos spawnPos, PalmTreeFeatureConfig config) {
        BlockPos topPos = generateTrunk(world, random, spawnPos, config);
        generateLeaves(world, random, topPos, config);
    }

    public static BlockPos generateTrunk(ServerWorldAccess world, Random random, BlockPos spawnPos, PalmTreeFeatureConfig config) {
        // get top trunk position offsets
        int height = config.getTreeHeight() + random.nextInt(config.getTreeHeightVary());
        int xOffset = (3 + random.nextInt(6)) * (random.nextInt(2) == 0 ? -1 : 1);
        int zOffset = (3 + random.nextInt(6)) * (random.nextInt(2) == 0 ? -1 : 1);

        // calculate target position & relevant data
        BlockPos originPos = new BlockPos(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ());
        BlockPos targetPos = originPos.add(xOffset, height, zOffset);

        double distance = Math.sqrt(Math.pow(targetPos.getX() - originPos.getX(), 2) + Math.pow(targetPos.getY() - originPos.getY(), 2) + Math.pow(targetPos.getZ() - originPos.getZ(), 2));
        double distanceX = (targetPos.getX() - originPos.getX()) / distance;
        double distanceY = (targetPos.getY() - originPos.getY()) / distance;
        double distanceZ = (targetPos.getZ() - originPos.getZ()) / distance;

        List<BlockPos> placed = new ArrayList<>();

        // build base
        horizontalDirections.forEach(direction -> {
            if(random.nextInt(2) == 0) {
                world.setBlockState(originPos.offset(direction), Blocks.JUNGLE_WOOD.getDefaultState(), 3);

                // go agane
                if(random.nextInt(2) == 0) {
                    world.setBlockState(originPos.offset(direction, 2), Blocks.JUNGLE_WOOD.getDefaultState(), 3);
                }

                // up
                if(random.nextInt(2) == 0) {
                    world.setBlockState(originPos.up(), Blocks.JUNGLE_WOOD.getDefaultState(), 3);
                }
            }
        });

        // place trunk
        BlockPos topPos = BlockPos.ORIGIN;
        Vec3d currentPos = new Vec3d(originPos.getX(), originPos.getY(), originPos.getZ());
        for (int i = 0; i < distance; i++) {
            BlockPos placementPos = new BlockPos(currentPos);
            world.setBlockState(placementPos, Blocks.JUNGLE_WOOD.getDefaultState(), 3);
            placed.add(placementPos);
            currentPos = currentPos.add(distanceX, distanceY, distanceZ);

            topPos = new BlockPos(currentPos);
        }

        // expand trunk downwards for depth
        placed.forEach(pos -> {
            if(world.getBlockState(pos.down()).isAir()) {
                for(int i = 0; i < 3 + random.nextInt(3) + Math.max(0, (20 - height) / 2); i++) {
                    world.setBlockState(pos.down(i), Blocks.JUNGLE_WOOD.getDefaultState(), 3);
                }
            }
        });

        return topPos;
    }

    private static void generateLeaves(ServerWorldAccess world, Random random, BlockPos topPos, PalmTreeFeatureConfig config) {
        world.setBlockState(topPos, Blocks.JUNGLE_LEAVES.getDefaultState().with(LeavesBlock.PERSISTENT, true), 3);

        horizontalDirections.forEach(direction -> {
            branch(world, topPos, direction, config.getLeavesRadius());
            branch(world, topPos.offset(direction.rotateYClockwise()), direction, config.getLeavesRadius() - 1);
            branch(world, topPos.offset(direction.rotateYClockwise(), 2), direction, config.getLeavesRadius() - 2);
            branch(world, topPos.offset(direction.rotateYCounterclockwise()), direction, config.getLeavesRadius() - 1);
            branch(world, topPos.offset(direction.rotateYCounterclockwise(), 2), direction, config.getLeavesRadius() - 2);
        });
    }

    private static void branch(ServerWorldAccess world, BlockPos origin, Direction direction, int initial) {
        int progress = 0;
        for(int i = 0; i < initial; i++) { // 0 -> 5, distance from top
            for(int z = initial - i; z > 0; z--) { // 5 -> 0, distance out
                world.setBlockState(origin.down(i).offset(direction, z + progress), Blocks.JUNGLE_LEAVES.getDefaultState().with(LeavesBlock.PERSISTENT, true), 3);
            }

            progress += initial - i;
        }
    }
}
