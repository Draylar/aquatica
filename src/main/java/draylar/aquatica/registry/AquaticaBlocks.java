package draylar.aquatica.registry;

import draylar.aquatica.Aquatica;
import draylar.aquatica.api.WoodType;
import draylar.aquatica.block.BeachChairBlock;
import draylar.aquatica.block.StarfishBlock;
import draylar.aquatica.item.BeachChairItem;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.DyeColor;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public class AquaticaBlocks {

    public static final List<Block> CHAIRS = new ArrayList<>();
    public static final Block STARFISH = register("starfish", new StarfishBlock(FabricBlockSettings.of(Material.METAL)));

    private static <T extends Block> T register(String name, T block, Item.Settings settings) {
        block = Registry.register(Registry.BLOCK, Aquatica.id(name), block);
        Registry.register(Registry.ITEM, Aquatica.id(name), new BlockItem(block, settings));
        return block;
    }

    private static BeachChairBlock registerChair(String name, BeachChairBlock block) {
        block = Registry.register(Registry.BLOCK, Aquatica.id(name), block);
        Registry.register(Registry.ITEM, Aquatica.id(name), new BeachChairItem(block, new Item.Settings().maxCount(16).group(Aquatica.GROUP)));
        CHAIRS.add(block);
        return block;
    }

    private static <T extends Block> T register(String name, T block) {
        return Registry.register(Registry.BLOCK, Aquatica.id(name), block);
    }

    public static void init() {
        // Register chairs
        for (DyeColor dyeColor : DyeColor.values()) {
            for (WoodType woodType : WoodType.values()) {
                registerChair(String.format("%s_%s_chair", dyeColor.name().toLowerCase(), woodType.name().toLowerCase()), new BeachChairBlock(FabricBlockSettings.of(Material.WOOD), dyeColor, woodType));
            }
        }
    }

    private AquaticaBlocks() {
        // NO-OP
    }
}
