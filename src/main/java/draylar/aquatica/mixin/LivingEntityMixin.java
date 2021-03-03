package draylar.aquatica.mixin;

import draylar.aquatica.api.event.PreEntityLoseBreathEvent;
import draylar.aquatica.registry.AquaticaItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    private LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(at = @At("HEAD"), method = "getNextAirUnderwater", cancellable = true)
    private void getNextBreathInWater(final int currentBreath, final CallbackInfoReturnable<Integer> info) {
        TypedActionResult<Float> result = PreEntityLoseBreathEvent.EVENT.invoker().loseBreath((LivingEntity) (Object) this, 1.0F);

        // don't lose breath if the result is a direct fail
        if (result.getResult() == ActionResult.FAIL) {
            info.setReturnValue(currentBreath);
        }

        // don't lose breath if chance rolls (1 == lose breath, 0 == don't lose breath)
        if (world.random.nextDouble() > result.getValue()) {
            info.setReturnValue(currentBreath);
        }
    }

    @ModifyVariable(at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/LivingEntity;updateVelocity(FLnet/minecraft/util/math/Vec3d;)V"
    ), method = "travel", name = "g")
    private float changeSpeedModifier(float g) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        ItemStack boots = livingEntity.getEquippedStack(EquipmentSlot.FEET);

        if (boots.getItem().equals(AquaticaItems.FLIPPERS) && livingEntity.isInSwimmingPose()) {
            return .96f;
        }

        return g;
    }

//    @ModifyVariable(
//            method = "travel",
//            at = @At(
//                    value = "INVOKE",
//                    target = "Lnet/minecraft/entity/LivingEntity;hasStatusEffect(Lnet/minecraft/entity/effect/StatusEffect;)Z"
//            ),
//            print = true,
//            name = "vec3d6"
//    )
//    private Vec3d flippersOnLand(Vec3d input) {
//        LivingEntity livingEntity = (LivingEntity) (Object) this;
//        ItemStack bootStacks = livingEntity.getEquippedStack(EquipmentSlot.FEET);
//
//        if (bootStacks.getItem().equals(Items.FLIPPERS) && !livingEntity.isInSwimmingPose() && livingEntity.isOnGround()) {
//            return new Vec3d(input.getX() * .5, input.getY(), input.getZ() * .5);
//        }
//
//        return input;
//    }
}
