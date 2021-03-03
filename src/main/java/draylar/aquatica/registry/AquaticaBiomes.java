package draylar.aquatica.registry;

import draylar.aquatica.Aquatica;
import draylar.aquatica.world.ternary.RockyShoreTernarySurfaceConfig;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredFeatures;
import net.minecraft.world.gen.feature.ConfiguredStructureFeatures;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.surfacebuilder.*;

public class AquaticaBiomes {

    public static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> WATERSIDE_PEAKS_SB = register(
            "waterside_peaks",
            SurfaceBuilder.DEFAULT.withConfig(new TernarySurfaceConfig(Blocks.GRASS_BLOCK.getDefaultState(), Blocks.SANDSTONE.getDefaultState(), Blocks.GRAVEL.getDefaultState())));

    public static final ConfiguredSurfaceBuilder<RockyShoreTernarySurfaceConfig> ROCKY_SHORE_SB = register(
            "rocky_shore",
            AquaticaWorld.ROCKY_SHORE.withConfig(new RockyShoreTernarySurfaceConfig(Blocks.SAND.getDefaultState(), Blocks.SANDSTONE.getDefaultState(), Blocks.GRAVEL.getDefaultState())));

    public static final ConfiguredSurfaceBuilder<TernarySurfaceConfig> AQUATIC_HEIGHTS_SB = register(
            "aquatic_heights",
            SurfaceBuilder.DEFAULT.withConfig(new TernarySurfaceConfig(Blocks.SAND.getDefaultState(), Blocks.SANDSTONE.getDefaultState(), Blocks.SAND.getDefaultState())));

    // center
    public static final Biome BREEZY_BEACH = createBreezyBeach();
    public static final RegistryKey<Biome> BREEZY_BEACH_KEY = RegistryKey.of(Registry.BIOME_KEY, Aquatica.id("breezy_beach"));
    public static final Biome ROCKY_SHORE = createRockyShore();
    public static final RegistryKey<Biome> ROCKY_SHORE_KEY = RegistryKey.of(Registry.BIOME_KEY, Aquatica.id("rocky_shore"));
    public static final Biome WATERSIDE_PEAKS = createWatersidePeaks();
    public static final RegistryKey<Biome> WATERSIDE_PEAKS_KEY = RegistryKey.of(Registry.BIOME_KEY, Aquatica.id("waterside_peaks"));
    public static final Biome AQUATIC_ISLAND = createAquaticIsland();
    public static final RegistryKey<Biome> AQUATIC_ISLAND_KEY = RegistryKey.of(Registry.BIOME_KEY, Aquatica.id("aquatic_island"));
    public static final Biome TROPICAL_WILDERNESS = createTropicalWilderness();
    public static final RegistryKey<Biome> TROPICAL_WILDERNESS_KEY = RegistryKey.of(Registry.BIOME_KEY, Aquatica.id("tropical_wilderness"));
    public static final Biome TROPICAL_JUNGLE = createTropicalJungle();
    public static final RegistryKey<Biome> TROPICAL_JUNGLE_KEY = RegistryKey.of(Registry.BIOME_KEY, Aquatica.id("tropical_jungle"));
    public static final Biome CALM_LAKE = createCalmLake();
    public static final RegistryKey<Biome> CALM_LAKE_KEY = RegistryKey.of(Registry.BIOME_KEY, Aquatica.id("calm_lake"));

    // default zone
    public static final Biome CORAL_REEFS = createCoralReefs();
    public static final RegistryKey<Biome> CORAL_REEFS_KEY = RegistryKey.of(Registry.BIOME_KEY, Aquatica.id("coral_reefs"));
    public static final Biome STARFISH_VALLEY = createStarfishValley();
    public static final RegistryKey<Biome> STARFISH_VALLEY_KEY = RegistryKey.of(Registry.BIOME_KEY, Aquatica.id("starfish_valley"));

    // inner zone
    public static final Biome AQUATIC_HEIGHTS = createAquaticHeights();
    public static final RegistryKey<Biome> AQUATIC_HEIGHTS_KEY = RegistryKey.of(Registry.BIOME_KEY, Aquatica.id("aquatic_heights"));

    private AquaticaBiomes() {

    }

    public static void init() {
        Registry.register(BuiltinRegistries.BIOME, BREEZY_BEACH_KEY.getValue(), BREEZY_BEACH);
        Registry.register(BuiltinRegistries.BIOME, ROCKY_SHORE_KEY.getValue(), ROCKY_SHORE);
        Registry.register(BuiltinRegistries.BIOME, WATERSIDE_PEAKS_KEY.getValue(), WATERSIDE_PEAKS);
        Registry.register(BuiltinRegistries.BIOME, AQUATIC_ISLAND_KEY.getValue(), AQUATIC_ISLAND);
        Registry.register(BuiltinRegistries.BIOME, TROPICAL_WILDERNESS_KEY.getValue(), TROPICAL_WILDERNESS);
        Registry.register(BuiltinRegistries.BIOME, TROPICAL_JUNGLE_KEY.getValue(), TROPICAL_JUNGLE);
        Registry.register(BuiltinRegistries.BIOME, CALM_LAKE_KEY.getValue(), CALM_LAKE);

        Registry.register(BuiltinRegistries.BIOME, CORAL_REEFS_KEY.getValue(), CORAL_REEFS);
        Registry.register(BuiltinRegistries.BIOME, STARFISH_VALLEY_KEY.getValue(), STARFISH_VALLEY);
        Registry.register(BuiltinRegistries.BIOME, AQUATIC_HEIGHTS_KEY.getValue(), AQUATIC_HEIGHTS);
    }

    private static <SC extends SurfaceConfig> ConfiguredSurfaceBuilder<SC> register(String id, ConfiguredSurfaceBuilder<SC> configuredSurfaceBuilder) {
        return  BuiltinRegistries.add(BuiltinRegistries.CONFIGURED_SURFACE_BUILDER, id, configuredSurfaceBuilder);
    }

    public static Biome createAquaticIsland() {
        GenerationSettings.Builder settings = new GenerationSettings.Builder();
        settings.surfaceBuilder(ConfiguredSurfaceBuilders.GRASS);

        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();

        // Foliage and other Features
        DefaultBiomeFeatures.addMossyRocks(settings);
        DefaultBiomeFeatures.addDefaultFlowers(settings);
        DefaultBiomeFeatures.addDefaultGrass(settings);
        DefaultBiomeFeatures.addDefaultMushrooms(settings);
        DefaultBiomeFeatures.addDefaultVegetation(settings);
        DefaultBiomeFeatures.addMossyRocks(settings);
        DefaultBiomeFeatures.addDesertLakes(settings);

//        settings.feature(GenerationStep.Feature.VEGETAL_DECORATION, AquaticaConfiguredFeatures.RANDOM_PALM_TREE);
        settings.feature(GenerationStep.Feature.VEGETAL_DECORATION, AquaticaConfiguredFeatures.SMALL_PALM_TREE);

        return new Biome.Builder()
                .spawnSettings(spawnSettings.build())
                .generationSettings(settings.build())
                .precipitation(Biome.Precipitation.RAIN)
                .category(Biome.Category.BEACH)
                .depth(5.3f)
                .scale(0.03F)
                .temperature(1.5F)
                .downfall(0.4f)
                .effects(new BiomeEffects.Builder().waterColor(5893089).waterFogColor(5893089).skyColor(getSkyColor(1.5f))  .fogColor(12638463).build())
                .build();
    }

    public static Biome createBreezyBeach() {
        GenerationSettings.Builder settings = new GenerationSettings.Builder();
        settings.surfaceBuilder(ConfiguredSurfaceBuilders.FULL_SAND);

        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();

        // Foliage and other Features
        DefaultBiomeFeatures.addDefaultFlowers(settings);
        DefaultBiomeFeatures.addDefaultGrass(settings);
        DefaultBiomeFeatures.addDefaultMushrooms(settings);
        DefaultBiomeFeatures.addDefaultVegetation(settings);
        DefaultBiomeFeatures.addMossyRocks(settings);
        DefaultBiomeFeatures.addDesertLakes(settings);

        settings.structureFeature(ConfiguredStructureFeatures.BURIED_TREASURE);
        settings.structureFeature(ConfiguredStructureFeatures.SHIPWRECK_BEACHED);

        settings.feature(GenerationStep.Feature.VEGETAL_DECORATION, AquaticaConfiguredFeatures.RARE_SMALL_PALM_TREE);

        return new Biome.Builder()
                .spawnSettings(spawnSettings.build())
                .generationSettings(settings.build())
                .precipitation(Biome.Precipitation.RAIN)
                .category(Biome.Category.BEACH)
                .depth(5.01F)
                .scale(0.0F)
                .temperature(1.5F)
                .downfall(0.4f)
                .effects(new BiomeEffects.Builder().waterColor(0x59ebe1).skyColor(getSkyColor(1.5f)).waterFogColor(0x00ffffff).fogColor(12638463).build())
                .build();
    }

    public static Biome createWatersidePeaks() {
        GenerationSettings.Builder settings = new GenerationSettings.Builder();
        settings.surfaceBuilder(WATERSIDE_PEAKS_SB);

        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();

        // Foliage and other Features
        DefaultBiomeFeatures.addMossyRocks(settings);
        DefaultBiomeFeatures.addDefaultFlowers(settings);
        DefaultBiomeFeatures.addExtraDefaultFlowers(settings);
        DefaultBiomeFeatures.addDefaultMushrooms(settings);
        DefaultBiomeFeatures.addDefaultVegetation(settings);
        DefaultBiomeFeatures.addDesertLakes(settings);
        DefaultBiomeFeatures.addJungleGrass(settings);
        DefaultBiomeFeatures.addSprings(settings);
        DefaultBiomeFeatures.addDefaultGrass(settings);
        DefaultBiomeFeatures.addKelp(settings);
        DefaultBiomeFeatures.addSeagrassOnStone(settings);

        settings.structureFeature(ConfiguredStructureFeatures.BURIED_TREASURE);
        settings.structureFeature(ConfiguredStructureFeatures.SHIPWRECK_BEACHED);

        settings.feature(GenerationStep.Feature.VEGETAL_DECORATION, AquaticaConfiguredFeatures.SMALL_PALM_TREE);

        return new Biome.Builder()
                .spawnSettings(spawnSettings.build())
                .generationSettings(settings.build())
                .precipitation(Biome.Precipitation.RAIN)
                .category(Biome.Category.BEACH)
                .depth(5.03F)
                .scale(1F)
                .temperature(1.5F)
                .downfall(0.4f)
                .effects(new BiomeEffects.Builder().waterColor(5893089).waterFogColor(5893089).skyColor(getSkyColor(1.5f)).fogColor(12638463).build())
                .build();
    }

    public static Biome createTropicalJungle() {
        GenerationSettings.Builder settings = new GenerationSettings.Builder();
        settings.surfaceBuilder(ConfiguredSurfaceBuilders.GRASS);

        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();

        // Foliage and other Features
        DefaultBiomeFeatures.addMossyRocks(settings);
        DefaultBiomeFeatures.addDefaultFlowers(settings);
        DefaultBiomeFeatures.addDefaultMushrooms(settings);
        DefaultBiomeFeatures.addDefaultVegetation(settings);
        DefaultBiomeFeatures.addDesertLakes(settings);

        settings.feature(GenerationStep.Feature.VEGETAL_DECORATION, AquaticaConfiguredFeatures.JUNGLE_GRASS_PATCH);
        settings.feature(GenerationStep.Feature.VEGETAL_DECORATION, ConfiguredFeatures.JUNGLE_BUSH);
        settings.feature(GenerationStep.Feature.VEGETAL_DECORATION, AquaticaConfiguredFeatures.SMALL_PALM_TREE);
        settings.feature(GenerationStep.Feature.VEGETAL_DECORATION, AquaticaConfiguredFeatures.LARGE_PALM_TREE);

        return new Biome.Builder()
                .spawnSettings(spawnSettings.build())
                .generationSettings(settings.build())
                .precipitation(Biome.Precipitation.RAIN)
                .category(Biome.Category.BEACH)
                .depth(5.6F)
                .scale(0.1F)
                .temperature(1.5F)
                .downfall(0.4f)
                .effects(new BiomeEffects.Builder().waterColor(5893089).waterFogColor(5893089).skyColor(getSkyColor(1.5f)).fogColor(12638463).build())
                .build();
    }

    public static Biome createRockyShore() {
        GenerationSettings.Builder settings = new GenerationSettings.Builder();
        settings.surfaceBuilder(ROCKY_SHORE_SB);

        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();

        settings.feature(GenerationStep.Feature.SURFACE_STRUCTURES, AquaticaConfiguredFeatures.BEACH_ROCK);
        settings.feature(GenerationStep.Feature.VEGETAL_DECORATION, AquaticaConfiguredFeatures.RARE_LARGE_PALM_TREE);
        settings.feature(GenerationStep.Feature.VEGETAL_DECORATION, AquaticaConfiguredFeatures.RARE_SMALL_PALM_TREE);

        return new Biome.Builder()
                .spawnSettings(spawnSettings.build())
                .generationSettings(settings.build())
                .precipitation(Biome.Precipitation.RAIN)
                .category(Biome.Category.BEACH)
                .depth(5.01F)
                .scale(0.08F)
                .temperature(1.5F)
                .downfall(0.4f)
                .effects(new BiomeEffects.Builder().waterColor(5893089).waterFogColor(5893089).skyColor(getSkyColor(1.5f)).fogColor(12638463).build())
                .build();
    }

    public static Biome createCalmLake() {
        GenerationSettings.Builder settings = new GenerationSettings.Builder();
        settings.surfaceBuilder(ConfiguredSurfaceBuilders.FULL_SAND);

        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();

        //
        DefaultBiomeFeatures.addDefaultFlowers(settings);
        DefaultBiomeFeatures.addDefaultGrass(settings);
        DefaultBiomeFeatures.addDefaultMushrooms(settings);
        DefaultBiomeFeatures.addDefaultVegetation(settings);
        DefaultBiomeFeatures.addSeagrassOnStone(settings);
        DefaultBiomeFeatures.addKelp(settings);
        DefaultBiomeFeatures.addDefaultGrass(settings);
        DefaultBiomeFeatures.addOceanCarvers(settings);
        DefaultBiomeFeatures.addClay(settings);
        DefaultBiomeFeatures.addMossyRocks(settings);

//        this.addSpawn(SpawnGroup.WATER_CREATURE, new SpawnEntry(EntityType.SQUID, 10, 4, 4));
//        this.addSpawn(SpawnGroup.WATER_CREATURE, new SpawnEntry(EntityType.PUFFERFISH, 15, 1, 3));
//        this.addSpawn(SpawnGroup.WATER_CREATURE, new SpawnEntry(EntityType.TROPICAL_FISH, 25, 8, 8));
//        this.addSpawn(SpawnGroup.WATER_CREATURE, new SpawnEntry(EntityType.DOLPHIN, 2, 1, 2));
//        this.addSpawn(SpawnGroup.WATER_CREATURE, new SpawnEntry(EntityType.TURTLE, 2, 1, 2));

        return new Biome.Builder()
                .spawnSettings(spawnSettings.build())
                .generationSettings(settings.build())
                .precipitation(Biome.Precipitation.NONE)
                .category(Biome.Category.DESERT)
                .depth(4.6F)
                .scale(-0.1f)
                .temperature(.8F)
                .downfall(0.0F)
                .effects(new BiomeEffects.Builder().waterColor(5893089).waterFogColor(5893089).skyColor(getSkyColor(0.8f)).fogColor(12638463).build())
                .build();
    }

    public static Biome createTropicalWilderness() {
        GenerationSettings.Builder settings = new GenerationSettings.Builder();
        settings.surfaceBuilder(ConfiguredSurfaceBuilders.GRASS);

        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();

        //
        DefaultBiomeFeatures.addMossyRocks(settings);
        DefaultBiomeFeatures.addDefaultFlowers(settings);
        DefaultBiomeFeatures.addExtraDefaultFlowers(settings);
        DefaultBiomeFeatures.addDefaultMushrooms(settings);
        DefaultBiomeFeatures.addDefaultVegetation(settings);
        DefaultBiomeFeatures.addDesertLakes(settings);
        DefaultBiomeFeatures.addJungleGrass(settings);
        DefaultBiomeFeatures.addSprings(settings);
        DefaultBiomeFeatures.addDefaultGrass(settings);

//        this.addFeature(
//                GenerationStep.Feature.VEGETAL_DECORATION,
//                Feature.TREE.configure(DefaultBiomeFeatures.JUNGLE_GROUND_BUSH_CONFIG).createDecoratedFeature(Decorator.COUNT_HEIGHTMAP_DOUBLE.configure(new CountDecoratorConfig(5))));
//
//
        settings.feature(GenerationStep.Feature.VEGETAL_DECORATION, AquaticaConfiguredFeatures.SMALL_PALM_TREE);

        return new Biome.Builder()
                .spawnSettings(spawnSettings.build())
                .generationSettings(settings.build())
                .precipitation(Biome.Precipitation.RAIN)
                .category(Biome.Category.BEACH)
                .depth(5.55F)
                .scale(0.1F)
                .temperature(1.5F)
                .downfall(0.4f)
                .effects(new BiomeEffects.Builder().waterColor(5893089).waterFogColor(5893089).skyColor(getSkyColor(1.5f)).fogColor(12638463).build())
                .build();
    }

    public static Biome createAquaticHeights() {
        GenerationSettings.Builder settings = new GenerationSettings.Builder();
        settings.surfaceBuilder(AQUATIC_HEIGHTS_SB);

        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();

        return new Biome.Builder()
                .generationSettings(settings.build())
                .spawnSettings(spawnSettings.build())
                .precipitation(Biome.Precipitation.RAIN)
                .category(Biome.Category.BEACH)
                .depth(2.25F)
                .scale(0.1F)
                .temperature(1.5F)
                .downfall(0.4f)
                .effects(new BiomeEffects.Builder().waterColor(0x59ebe1).waterFogColor(0x59ebe1).skyColor(getSkyColor(1.5f)).fogColor(12638463).build())
                .build();
    }

    public static Biome createCoralReefs() {
        GenerationSettings.Builder settings = new GenerationSettings.Builder();
        settings.surfaceBuilder(AQUATIC_HEIGHTS_SB);

        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();

        DefaultBiomeFeatures.addOceanCarvers(settings);
        DefaultBiomeFeatures.addDefaultLakes(settings);
        DefaultBiomeFeatures.addMineables(settings);
        DefaultBiomeFeatures.addDefaultOres(settings);
        DefaultBiomeFeatures.addDefaultDisks(settings);
        DefaultBiomeFeatures.addWaterBiomeOakTrees(settings);
        DefaultBiomeFeatures.addDefaultFlowers(settings);
        DefaultBiomeFeatures.addDefaultGrass(settings);
        DefaultBiomeFeatures.addDefaultMushrooms(settings);
        DefaultBiomeFeatures.addDefaultVegetation(settings);
        DefaultBiomeFeatures.addSprings(settings);
        DefaultBiomeFeatures.addSeagrassOnStone(settings);

        //        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, configureFeature(Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfig(new Feature[]{Feature.CORAL_TREE, Feature.CORAL_CLAW, Feature.CORAL_MUSHROOM}, new FeatureConfig[]{FeatureConfig.DEFAULT, FeatureConfig.DEFAULT, FeatureConfig.DEFAULT}), Decorator.TOP_SOLID_HEIGHTMAP_NOISE_BIASED, new TopSolidHeightmapNoiseBiasedDecoratorConfig(100, 40, 0.0D, Heightmap.Type.OCEAN_FLOOR_WG)));
//        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, configureFeature(Feature.SEA_PICKLE, new SeaPickleFeatureConfig(20), Decorator.CHANCE_TOP_SOLID_HEIGHTMAP, new ChanceDecoratorConfig(16)));

//        this.addSpawn(SpawnGroup.WATER_CREATURE, new SpawnEntry(EntityType.SQUID, 10, 4, 4));
//        this.addSpawn(SpawnGroup.WATER_CREATURE, new SpawnEntry(EntityType.PUFFERFISH, 15, 1, 3));
//        this.addSpawn(SpawnGroup.WATER_CREATURE, new SpawnEntry(EntityType.TROPICAL_FISH, 25, 8, 8));
//        this.addSpawn(SpawnGroup.WATER_CREATURE, new SpawnEntry(EntityType.DOLPHIN, 2, 1, 2));
//        this.addSpawn(SpawnGroup.MONSTER, new SpawnEntry(EntityType.ZOMBIE, 95, 4, 4));
        settings.structureFeature(ConfiguredStructureFeatures.SHIPWRECK_BEACHED);

        return new Biome.Builder()
                .generationSettings(settings.build())
                .spawnSettings(spawnSettings.build())
                .precipitation(Biome.Precipitation.RAIN)
                .category(Biome.Category.BEACH)
                .depth(3.6F)
                .scale(0.15F)
                .temperature(1.5F)
                .downfall(0.4f)
                .effects(new BiomeEffects.Builder().waterColor(0x59ebe1).waterFogColor(0x59ebe1).skyColor(getSkyColor(1.5f)).fogColor(12638463).build())
                .build();
    }

    public static Biome createStarfishValley() {
        GenerationSettings.Builder settings = new GenerationSettings.Builder();
        settings.surfaceBuilder(AQUATIC_HEIGHTS_SB);

        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();

        DefaultBiomeFeatures.addOceanCarvers(settings);
        DefaultBiomeFeatures.addDefaultLakes(settings);
        DefaultBiomeFeatures.addMineables(settings);
        DefaultBiomeFeatures.addDefaultOres(settings);
        DefaultBiomeFeatures.addDefaultDisks(settings);
        DefaultBiomeFeatures.addWaterBiomeOakTrees(settings);
        DefaultBiomeFeatures.addDefaultFlowers(settings);
        DefaultBiomeFeatures.addDefaultGrass(settings);
        DefaultBiomeFeatures.addDefaultMushrooms(settings);
        DefaultBiomeFeatures.addDefaultVegetation(settings);
        DefaultBiomeFeatures.addSprings(settings);
        DefaultBiomeFeatures.addSeagrassOnStone(settings);

//        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, configureFeature(World.STARFISH, new DefaultFeatureConfig(), Decorator.CHANCE_TOP_SOLID_HEIGHTMAP, new ChanceDecoratorConfig(1)));
//        // mthis.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, configureFeature(Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfig(new Feature[]{Feature.CORAL_TREE, Feature.CORAL_CLAW, Feature.CORAL_MUSHROOM}, new FeatureConfig[]{FeatureConfig.DEFAULT, FeatureConfig.DEFAULT, FeatureConfig.DEFAULT}), Decorator.TOP_SOLID_HEIGHTMAP_NOISE_BIASED, new TopSolidHeightmapNoiseBiasedDecoratorConfig(100, 40, 0.0D, Heightmap.Type.OCEAN_FLOOR_WG)));
//        this.addFeature(GenerationStep.Feature.VEGETAL_DECORATION, configureFeature(Feature.SEA_PICKLE, new SeaPickleFeatureConfig(20), Decorator.CHANCE_TOP_SOLID_HEIGHTMAP, new ChanceDecoratorConfig(1)));
        settings.structureFeature(ConfiguredStructureFeatures.SHIPWRECK_BEACHED);
        // -> sunken shipwreck

        return new Biome.Builder()
                .generationSettings(settings.build())
                .spawnSettings(spawnSettings.build())
                .precipitation(Biome.Precipitation.RAIN)
                .category(Biome.Category.BEACH)
                .depth(3.6F)
                .scale(0.15F)
                .temperature(1.5F)
                .downfall(0.4f)
                .effects(new BiomeEffects.Builder().waterColor(0x59ebe1).waterFogColor(0x59ebe1).skyColor(getSkyColor(1.5f)).fogColor(12638463).build())
                .build();
    }

    private static int getSkyColor(float temperature) {
        float f = temperature / 3.0F;
        f = MathHelper.clamp(f, -1.0F, 1.0F);
        return MathHelper.hsvToRgb(0.62222224F - f * 0.05F, 0.5F + f * 0.1F, 1.0F);
    }
}
