package draylar.aquatica.mixin;

import draylar.aquatica.registry.AquaticaItems;
import draylar.aquatica.registry.Sounds;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow public abstract void playSound(SoundEvent soundEvent, float volume, float pitch);

    @Inject(
            method = "playStepSound",
            at = @At("HEAD"),
            cancellable = true
    )
    private void playStepSound(final BlockPos pos, final BlockState state, final CallbackInfo info) {
        Entity entity = (Entity) (Object) this;

        if(entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) entity;
            ItemStack feetStack = livingEntity.getEquippedStack(EquipmentSlot.FEET);

            // play flipper sound if they are equipped
            if(feetStack.getItem().equals(AquaticaItems.FLIPPERS)) {
                this.playSound(Sounds.FINS_STEP, .2f, .8f + (float) (entity.world.random.nextDouble() * .4f));
                info.cancel();
            }
        }
    }
}
