package draylar.aquatica.mixin;

import draylar.aquatica.world.AquaticaChunkGenerator;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.GeneratorOptions;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.chunk.StructuresConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Properties;
import java.util.Random;

@Mixin(GeneratorOptions.class)
public class GeneratorOptionsMixin {

    @Unique private static final String LEVEL_TYPE_KEY = "level-type";
    @Unique private static final String AQUATICA_KEY = "aquatica";

    @Inject(
            method = "fromProperties",
            at = @At("HEAD"),
            cancellable = true)
    private static void loadAquaticaLevelType(DynamicRegistryManager dynamicRegistryManager, Properties properties, CallbackInfoReturnable<GeneratorOptions> cir) {
        // Ensure a level type field is present in the properties object before reading it.
        if(properties.get(LEVEL_TYPE_KEY) == null) {
            return;
        }

        // If the 'aquatica' world-type was specified in this property,
        // load our custom world/generator type.
        String parsedLevelType = properties.get(LEVEL_TYPE_KEY).toString().trim();
        if(parsedLevelType.equals(AQUATICA_KEY)) {
            // parse other data from properties file
            long seed = new Random().nextLong();

            // grab registries
            MutableRegistry<DimensionType> dimensions = dynamicRegistryManager.get(Registry.DIMENSION_TYPE_KEY);
            MutableRegistry<Biome> biomes = dynamicRegistryManager.get(Registry.BIOME_KEY);
            MutableRegistry<ChunkGeneratorSettings> chunkGeneratorSettings = dynamicRegistryManager.get(Registry.NOISE_SETTINGS_WORLDGEN);
            SimpleRegistry<DimensionOptions> dimensionOptions = DimensionType.createDefaultDimensionOptions(dimensions, biomes, chunkGeneratorSettings, seed);

            cir.setReturnValue(new GeneratorOptions(
                    seed,
                    true,
                    false,
                    GeneratorOptions.method_28608(
                            dimensions,
                            dimensionOptions,
                            new AquaticaChunkGenerator(biomes, seed, new StructuresConfig(true))
                    )
            ));
        }
    }
}
