package draylar.aquatica.mixin;

import draylar.aquatica.registry.Enchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SmeltingRecipe;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(FishingBobberEntity.class)
public abstract class FishingBobberEntityMixin extends Entity {

    @Unique
    private ItemStack stack = ItemStack.EMPTY;

    private FishingBobberEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(
            method = "use",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/ItemEntity;<init>(Lnet/minecraft/world/World;DDDLnet/minecraft/item/ItemStack;)V"
            )
    )
    private void preConstruct(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        this.stack = stack;
    }

    @ModifyVariable(
            method = "use",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/ItemEntity;setVelocity(DDD)V",
                    shift = At.Shift.AFTER
            ),
            name = "itemEntity"
    )
    private ItemEntity test(ItemEntity itemEntity) {
        if(EnchantmentHelper.getLevel(Enchantments.DEEPFRY, stack) != 0) {
            Optional<SmeltingRecipe> cooked = world.getRecipeManager().getFirstMatch(
                    RecipeType.SMELTING,
                    new SimpleInventory(itemEntity.getStack()),
                    world
            );

            cooked.ifPresent(smeltingRecipe -> itemEntity.setStack(smeltingRecipe.getOutput()));
        }

        return itemEntity;
    }

    /**
     * Patches {@link FishingBobberEntity#removeIfInvalid(PlayerEntity)} to work for all items of the type {@link FishingRodItem}.
     *
     * @param playerEntity  owner of this {@link FishingBobberEntity}
     * @param cir  mixin callback info
     */
    @Inject(
            method = "removeIfInvalid",
            at = @At("HEAD"),
            cancellable = true
    )
    private void removeIfInvalid(PlayerEntity playerEntity, CallbackInfoReturnable<Boolean> cir) {
        ItemStack mainHandStack = playerEntity.getMainHandStack();
        ItemStack offHandStack = playerEntity.getOffHandStack();

        boolean mainHandHasRod = mainHandStack.getItem() instanceof FishingRodItem;
        boolean offHandHasRod = offHandStack.getItem() instanceof FishingRodItem;

        if (!playerEntity.removed && playerEntity.isAlive() && (mainHandHasRod || offHandHasRod) && this.squaredDistanceTo(playerEntity) <= 1024.0D) {
            cir.setReturnValue(false);
        }
    }
}
