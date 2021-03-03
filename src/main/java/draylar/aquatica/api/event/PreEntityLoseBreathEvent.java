package draylar.aquatica.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.TypedActionResult;

/**
 * Called before an entity loses breath due to being underwater.
 *
 * <p>The float provided to listeners represents the chance of the entity losing breath.
 * At 1.0F, the entity has an 100% chance to lose breath. At 0.0F, the entity has a 0% chance to lose breath.
 *
 * If a listener does not care about the breath chance, they should return the given float in their {@link TypedActionResult}.
 *
 * If {@link TypedActionResult#success(Object)} is returned, all future listeners will be skipped, and the entity will lose breath.
 * If {@link TypedActionResult#fail(Object)} is returned, all future listeners will be skipped, and the entity will not lose breath.
 */
@FunctionalInterface
public interface PreEntityLoseBreathEvent {

    Event<PreEntityLoseBreathEvent> EVENT = EventFactory.createArrayBacked(PreEntityLoseBreathEvent.class,
            listeners -> (entity, chance) -> {
                TypedActionResult<Float> result = TypedActionResult.success(chance);

                for(PreEntityLoseBreathEvent event : listeners) {
                    result = event.loseBreath(entity, result.getValue());

                    if(result.getResult() != ActionResult.PASS) {
                        return result;
                    }
                }

                return result;
            }
    );

    TypedActionResult<Float> loseBreath(LivingEntity entity, float chance);
}
