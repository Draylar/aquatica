package draylar.aquatica.registry;

import draylar.aquatica.Aquatica;
import draylar.aquatica.client.IslandGeneratorType;
import draylar.aquatica.world.AquaticaBiomeSource;
import draylar.aquatica.world.config.PalmTreeFeatureConfig;
import draylar.aquatica.world.features.PalmTreeFeature;
import draylar.aquatica.world.features.BeachRockFeature;
import draylar.aquatica.world.sb.RockyShoreSurfaceBuilder;
import draylar.aquatica.world.ternary.RockyShoreTernarySurfaceConfig;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.source.TheEndBiomeSource;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.chunk.StructuresConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.surfacebuilder.NetherSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceConfig;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

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
        register(AQUATICA, IslandGeneratorType.createAquaticaType(new StructuresConfig(false), false, AQUATICA.getValue()));
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
}
