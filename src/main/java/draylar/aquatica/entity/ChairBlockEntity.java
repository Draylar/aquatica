package draylar.aquatica.entity;

import draylar.aquatica.registry.AquaticaEntities;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.CompoundTag;

public class ChairBlockEntity extends BlockEntity implements BlockEntityClientSerializable {

    private double rotation;

    public ChairBlockEntity() {
        super(AquaticaEntities.CHAIR_BE);
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
        sync();
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        tag.putDouble("Rotation", rotation);
        return super.toTag(tag);
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        rotation = tag.getDouble("Rotation");
        super.fromTag(state, tag);
    }

    @Override
    public void fromClientTag(CompoundTag tag) {
        fromTag(getCachedState(), tag);
    }

    @Override
    public CompoundTag toClientTag(CompoundTag tag) {
        return toTag(tag);
    }

    public double getRotation() {
        return rotation;
    }
}
