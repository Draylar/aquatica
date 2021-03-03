package draylar.aquatica.registry;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Sounds {

    // https://freesound.org/people/Breviceps/sounds/450621/
    public static final SoundEvent FINS_STEP = register("fins_step");

    private static SoundEvent register(String name) {
        return Registry.register(Registry.SOUND_EVENT, new Identifier("aquatica", name), new SoundEvent(new Identifier("aquatica", name)));
    }

    public static void init() {

    }

    private Sounds() {

    }
}
