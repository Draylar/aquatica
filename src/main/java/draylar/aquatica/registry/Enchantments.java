package draylar.aquatica.registry;

import draylar.aquatica.Aquatica;
import draylar.aquatica.enchantment.DeepfryEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.registry.Registry;

public class Enchantments {

    public static final Enchantment DEEPFRY = register("deepfry", new DeepfryEnchantment());


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
