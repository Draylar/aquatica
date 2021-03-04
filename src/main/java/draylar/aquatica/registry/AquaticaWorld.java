package draylar.aquatica.registry;

import draylar.aquatica.Aquatica;
import draylar.aquatica.mixin.ChunkGeneratorSettingsAccessor;
import draylar.aquatica.world.AquaticaBiomeSource;
import draylar.aquatica.world.config.PalmTreeFeatureConfig;
import draylar.aquatica.world.features.BeachRockFeature;
import draylar.aquatica.world.features.PalmTreeFeature;
import draylar.aquatica.world.sb.RockyShoreSurfaceBuilder;
import draylar.aquatica.world.ternary.RockyShoreTernarySurfaceConfig;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.chunk.*;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceConfig;

public class AquaticaWorld {

    public static final RegistryKey<ChunkGeneratorSettings> AQUATICA = RegistryKey.of(Registry.NOISE_SETTINGS_WORLDGEN, new Identifier("aquatica"));

//    public static final Feature NORMAL_PALM_TREE = register("palm_tree", new NormalPalmTreeFeature());
//    public static final Feature LARGE_PALM_TREE = register("large_palm_tree", new LargePalmTreeFeature());

    public static final Feature<PalmTreeFeatureConfig> PALM_TREE = register("palm_tree", new PalmTreeFeature());
    public static final Feature<DefaultFeatureConfig> BEACH_ROCK = register("beach_rock", new BeachRockFeature());

    public static final SurfaceBuilder<RockyShoreTernarySurfaceConfig> ROCKY_SHORE = register("rocky_shore", new RockyShoreSurfaceBuilder(RockyShoreTernarySurfaceConfig.CODEC));

//    public static final Feature<DefaultFeatureConfig> STARFISH = register("starfish", new StarfishFeature(DefaultFeatureConfig::deserialize));


    private AquaticaWorld() {
        // NO-OP
    }

    public static void init() {
        register(AQUATICA, createAquaticaType(new StructuresConfig(false), false, AQUATICA.getValue()));
        Registry.register(Registry.BIOME_SOURCE, Aquatica.id("aquatica"), AquaticaBiomeSource.CODEC);
    }

    private static <C extends FeatureConfig, F extends Feature<C>> F register(String name, F feature_1) {
        return Registry.register(Registry.FEATURE, new Identifier("aquatica", name), feature_1);
    }

    private static ChunkGeneratorSettings register(RegistryKey<ChunkGeneratorSettings> key, ChunkGeneratorSettings settings) {
        BuiltinRegistries.add(BuiltinRegistries.CHUNK_GENERATOR_SETTINGS, key.getValue(), settings);
        return settings;
    }

    private static <C extends SurfaceConfig, F extends SurfaceBuilder<C>> F register(String id, F surfaceBuilder) {
        return Registry.register(Registry.SURFACE_BUILDER, Aquatica.id(id), surfaceBuilder);
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
