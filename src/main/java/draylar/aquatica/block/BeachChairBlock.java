package draylar.aquatica.block;

import draylar.aquatica.api.WoodType;
import draylar.aquatica.entity.ChairBlockEntity;
import draylar.aquatica.entity.ChairEntity;
import draylar.aquatica.registry.AquaticaEntities;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class BeachChairBlock extends Block implements BlockEntityProvider {

    public static final VoxelShape SHAPE = Block.createCuboidShape(1.0f, 0.0f, 1.0f, 15.0f, 5.0f, 15.0f);
    private final DyeColor dyeColor;
    private final WoodType woodType;

    public BeachChairBlock(Settings settings, DyeColor dyeColor, WoodType woodType) {
        super(settings);
        this.dyeColor = dyeColor;
        this.woodType = woodType;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(!world.isClient) {
            ChairEntity chairEntity = new ChairEntity(AquaticaEntities.CHAIR, world);
            chairEntity.setPos(pos.getX() + .5, pos.getY() - 0.7, pos.getZ() + .5);
            world.spawnEntity(chairEntity);
            player.startRiding(chairEntity);
        }

        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        if(!world.isClient) {
            ChairBlockEntity be = (ChairBlockEntity) world.getBlockEntity(pos);

            if(be != null) {
                be.setRotation(placer.getHeadYaw());
            }
        }

        super.onPlaced(world, pos, state, placer, itemStack);
    }

    public WoodType getWoodType() {
        return woodType;
    }

    public DyeColor getDyeColor() {
        return dyeColor;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new ChairBlockEntity();
    }
}
