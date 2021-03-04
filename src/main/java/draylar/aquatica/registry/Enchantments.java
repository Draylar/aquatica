package draylar.aquatica.registry;

import draylar.aquatica.Aquatica;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.registry.Registry;

public class Enchantments {


    public static Enchantment register(String name, Enchantment enchantment) {
        return Registry.register(Registry.ENCHANTMENT, Aquatica.id(name), enchantment);
    }

    public static void init() {
        // NO-OP
    }

    private Enchantments() {
        // NO-OP
    }
}
