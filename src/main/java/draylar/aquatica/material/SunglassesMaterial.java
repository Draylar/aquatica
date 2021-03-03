package draylar.aquatica.material;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public class SunglassesMaterial implements ArmorMaterial {

    public static final SunglassesMaterial INSTANCE = new SunglassesMaterial();

    @Override
    public int getDurability(EquipmentSlot slot) {
        return -1;
    }

    @Override
    public int getProtectionAmount(EquipmentSlot slot) {
        return 1;
    }

    @Override
    public int getEnchantability() {
        return 15;
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ENTITY_PLAYER_LEVELUP;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(Items.BLACK_DYE);
    }

    @Override
    public String getName() {
        return "sunglasses";
    }

    @Override
    public float getToughness() {
        return 0;
    }

    @Override
    public float getKnockbackResistance() {
        return 0;
    }
}
