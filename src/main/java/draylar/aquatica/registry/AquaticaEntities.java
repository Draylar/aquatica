package draylar.aquatica.registry;

import draylar.aquatica.Aquatica;
import draylar.aquatica.entity.ChairBlockEntity;
import draylar.aquatica.entity.ChairEntity;
import draylar.aquatica.entity.PirateEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.Registry;

public class AquaticaEntities {

    public static final EntityType<PirateEntity> PIRATE = register(
            "pirate",
            FabricEntityTypeBuilder.<PirateEntity>create(
                    SpawnGroup.MONSTER,
                    PirateEntity::new
            ).dimensions(EntityDimensions.fixed(1, 2)).build());

    public static final EntityType<ChairEntity> CHAIR = register(
            "chair",
            FabricEntityTypeBuilder.create(
                    SpawnGroup.MISC,
                    ChairEntity::new
            ).dimensions(EntityDimensions.fixed(1, 1)).build());

    public static final BlockEntityType<ChairBlockEntity> CHAIR_BE = register(
            "chair",
            BlockEntityType.Builder.create(ChairBlockEntity::new, AquaticaBlocks.CHAIRS.toArray(new Block[0])).build(null));

    public static <T extends Entity> EntityType<T> register(String name, EntityType<T> entityType) {
        return Registry.register(Registry.ENTITY_TYPE, Aquatica.id(name), entityType);
    }

    public static <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType<T> entityType) {
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, Aquatica.id(name), entityType);
    }

    public static void init() {
        // NO-OP
    }

    private AquaticaEntities() {
        // NO-OP
    }
}
