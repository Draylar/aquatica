package draylar.aquatica;

import draylar.aquatica.entity.PirateEntity;
import draylar.aquatica.registry.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class Aquatica implements ModInitializer {

    public static final ItemGroup GROUP = FabricItemGroupBuilder.build(new Identifier("aquatica", "group"), () -> new ItemStack(AquaticaItems.DIVING_GOGGLES));

    @Override
    public void onInitialize() {
        AquaticaItems.init();
        AquaticaBlocks.init();
        AquaticaWorld.init();
        AquaticaConfiguredFeatures.init();
        Sounds.init();
        AquaticaEntities.init();
        Enchantments.init();
        AquaticaBiomes.init();

        FabricDefaultAttributeRegistry.register(AquaticaEntities.PIRATE, PirateEntity.createAbstractSkeletonAttributes());
    }

    public static Identifier id(String path) {
        return new Identifier("aquatica", path);
    }
}
