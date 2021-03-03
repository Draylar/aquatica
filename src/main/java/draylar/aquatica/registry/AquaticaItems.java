package draylar.aquatica.registry;

import draylar.aquatica.Aquatica;
import draylar.aquatica.item.SharkToothSpearItem;
import draylar.aquatica.material.SunglassesMaterial;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.registry.Registry;

public class AquaticaItems {

    // scuba gear
    public static final Item DIVING_GOGGLES = register("diving_goggles", new ArmorItem(ArmorMaterials.LEATHER, EquipmentSlot.HEAD, new Item.Settings().group(Aquatica.GROUP)));
    public static final Item FLIPPERS = register("flippers", new ArmorItem(ArmorMaterials.LEATHER, EquipmentSlot.FEET, new Item.Settings().group(Aquatica.GROUP)));
    public static final Item SCUBA_TANK = register("scuba_tank", new ArmorItem(ArmorMaterials.LEATHER, EquipmentSlot.CHEST, new Item.Settings().group(Aquatica.GROUP)));
    public static final Item WETSUIT = register("wetsuit", new ArmorItem(ArmorMaterials.LEATHER, EquipmentSlot.LEGS, new Item.Settings().group(Aquatica.GROUP)));
    private static final Item SNORKEL = register("snorkel", new Item(new Item.Settings().group(Aquatica.GROUP)));

    // tools
    public static final Item SHARK_TOOTH_SPEAR = register("shark_tooth_spear", new SharkToothSpearItem(new Item.Settings().group(Aquatica.GROUP).maxCount(1)));


    // drops
    public static final Item SHARK_TOOTH = register("shark_tooth", new Item(new Item.Settings().group(Aquatica.GROUP)));

    // misc
    public static final Item CANNONBALL = register("cannonball", new Item(new Item.Settings().group(Aquatica.GROUP)));
    public static final Item SUNGLASSES = register("sunglasses", new ArmorItem(SunglassesMaterial.INSTANCE, EquipmentSlot.HEAD, new Item.Settings().group(Aquatica.GROUP)));


    private static <T extends Item> T register(String name, T item) {
        return Registry.register(Registry.ITEM, Aquatica.id(name), item);
    }

    public static void init() {
    }

    private AquaticaItems() {
        // NO-OP
    }
}
