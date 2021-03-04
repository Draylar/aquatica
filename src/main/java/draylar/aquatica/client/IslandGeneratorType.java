package draylar.aquatica.client;

import draylar.aquatica.world.AquaticaChunkGenerator;
import net.minecraft.client.world.GeneratorType;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.GeneratorOptions;
import net.minecraft.world.gen.chunk.*;

public class IslandGeneratorType extends GeneratorType {

    public IslandGeneratorType(String translationKey) {
        super(translationKey);
        GeneratorType.VALUES.add(this);
    }

    @Override
    protected ChunkGenerator getChunkGenerator(Registry<Biome> biomeRegistry, Registry<ChunkGeneratorSettings> chunkGeneratorSettingsRegistry, long seed) {
        return new AquaticaChunkGenerator(biomeRegistry, seed, new StructuresConfig(true));
    }

    public GeneratorOptions createDefaultOptions(DynamicRegistryManager.Impl registryManager, long seed, boolean generateStructures, boolean bonusChest) {
        Registry<Biome> registry = registryManager.get(Registry.BIOME_KEY);
        Registry<DimensionType> registry2 = registryManager.get(Registry.DIMENSION_TYPE_KEY);
        Registry<ChunkGeneratorSettings> registry3 = registryManager.get(Registry.NOISE_SETTINGS_WORLDGEN);
        return new GeneratorOptions(seed, generateStructures, bonusChest, GeneratorOptions.method_29962(DimensionType.createDefaultDimensionOptions(registry2, registry, registry3, seed), () -> {
            return (DimensionType)registry2.getOrThrow(DimensionType.OVERWORLD_CAVES_REGISTRY_KEY);
        }, this.getChunkGenerator(registry, registry3, seed)));
    }
}
