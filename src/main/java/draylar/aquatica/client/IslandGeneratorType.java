package draylar.aquatica.client;

import draylar.aquatica.mixin.ChunkGeneratorSettingsAccessor;
import draylar.aquatica.world.IslandChunkGenerator;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Blocks;
import net.minecraft.client.world.GeneratorType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.GeneratorOptions;
import net.minecraft.world.gen.chunk.*;

import java.util.ArrayList;
import java.util.List;

public class IslandGeneratorType extends GeneratorType {

    public IslandGeneratorType(String translationKey) {
        super(translationKey);
        GeneratorType.VALUES.add(this);
    }

    @Override
    protected ChunkGenerator getChunkGenerator(Registry<Biome> biomeRegistry, Registry<ChunkGeneratorSettings> chunkGeneratorSettingsRegistry, long seed) {
        return new IslandChunkGenerator(biomeRegistry, seed, new StructuresConfig(true));
    }

    public GeneratorOptions createDefaultOptions(DynamicRegistryManager.Impl registryManager, long seed, boolean generateStructures, boolean bonusChest) {
        Registry<Biome> registry = registryManager.get(Registry.BIOME_KEY);
        Registry<DimensionType> registry2 = registryManager.get(Registry.DIMENSION_TYPE_KEY);
        Registry<ChunkGeneratorSettings> registry3 = registryManager.get(Registry.NOISE_SETTINGS_WORLDGEN);
        return new GeneratorOptions(seed, generateStructures, bonusChest, GeneratorOptions.method_29962(DimensionType.createDefaultDimensionOptions(registry2, registry, registry3, seed), () -> {
            return (DimensionType)registry2.getOrThrow(DimensionType.OVERWORLD_CAVES_REGISTRY_KEY);
        }, this.getChunkGenerator(registry, registry3, seed)));
    }

    public static ChunkGeneratorSettings createAquaticaType(StructuresConfig config, boolean bl, Identifier id) {
        double scale = 0.9999999814507745D;

        return ChunkGeneratorSettingsAccessor.createChunkGeneratorSettings(
                config,
                new GenerationShapeConfig(
                        256,
                        new NoiseSamplingConfig(
                                scale,
                                scale,
                                80.0D,
                                160.0D),
                        new SlideConfig(-10, 3, 0),
                        new SlideConfig(-30, 0, 0),
                        1,
                        2,
                        1.0D,
                        -0.46875D,
                        true,
                        true,
                        false,
                        bl),
                Blocks.STONE.getDefaultState(),
                Blocks.WATER.getDefaultState(),
                -10,
                0,
                150,
                false);
    }
}
