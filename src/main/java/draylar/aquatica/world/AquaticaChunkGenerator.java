package draylar.aquatica.world;

import draylar.aquatica.registry.AquaticaWorld;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;
import net.minecraft.world.gen.chunk.StructuresConfig;

public class AquaticaChunkGenerator extends NoiseChunkGenerator {

    private final StructuresConfig generatorConfig;

    public AquaticaChunkGenerator(Registry<Biome> biomeRegistry, long seed, StructuresConfig config) { // IslandGeneratorType.createAquaticaType(config, false, ChunkGeneratorType.Preset.AMPLIFIED)
        super(new AquaticaBiomeSource(biomeRegistry, seed), seed, () -> {
            return BuiltinRegistries.CHUNK_GENERATOR_SETTINGS.getOrThrow(AquaticaWorld.AQUATICA);
        });


        this.generatorConfig = config;
    }

    public StructuresConfig getConfig() {
        return this.generatorConfig;
    }

    @Override
    public int getSeaLevel() {
        return 150;
    }

    @Override
    public int getSpawnHeight() {
        return getSeaLevel() + 1;
    }
}
