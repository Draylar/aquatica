package draylar.aquatica.registry;

import draylar.aquatica.Aquatica;
import draylar.aquatica.world.config.PalmTreeFeatureConfig;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.CountConfig;
import net.minecraft.world.gen.decorator.CountExtraDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placer.SimpleBlockPlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import sun.security.provider.PolicySpiFile;

public class AquaticaConfiguredFeatures {

    public static final ConfiguredFeature<?, ?> RARE_SMALL_PALM_TREE =
            register(
                    "rare_small_palm_tree",
                    AquaticaWorld.PALM_TREE.configure(new PalmTreeFeatureConfig())
                            .decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(0, 0.2F, 1))));

    public static final ConfiguredFeature<?, ?> SMALL_PALM_TREE =
            register(
                    "small_palm_tree",
                    AquaticaWorld.PALM_TREE.configure(new PalmTreeFeatureConfig())
                            .decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(1, 0.1F, 1))));

    public static final ConfiguredFeature<?, ?> LARGE_PALM_TREE =
            register(
                    "large_palm_tree",
                    AquaticaWorld.PALM_TREE.configure(new PalmTreeFeatureConfig(20, 15, 7, 3))
                            .decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(1, 0.1F, 1))));

    public static final ConfiguredFeature<?, ?> RARE_LARGE_PALM_TREE =
            register(
                    "rare_large_palm_tree",
                    AquaticaWorld.PALM_TREE.configure(new PalmTreeFeatureConfig(20, 15, 7, 3))
                            .decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(0, 0.1F, 1))));

    public static final ConfiguredFeature<?, ?> JUNGLE_GRASS_PATCH =
            register(
                    "jungle_grass_patch",
                    Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.GRASS.getDefaultState()), SimpleBlockPlacer.INSTANCE).tries(32).build())
                            .decorate(Decorator.COUNT.configure(new CountConfig(10))));

    public static final ConfiguredFeature<?, ?> BEACH_ROCK =
            register(
                    "beach_rock",
                    AquaticaWorld.BEACH_ROCK.configure(new DefaultFeatureConfig()).decorate(ConfiguredFeatures.Decorators.SQUARE_HEIGHTMAP).repeat(1));

//    public static final ConfiguredFeature<?, ?> RANDOM_PALM_TREE
//            = register(
//                    "palm_tree",
//            Feature.RANDOM_SELECTOR.configure(new RandomFeatureConfig(
//                    ImmutableList.of(
//                            SMALL_PALM_TREE.withChance(0.2F),
//                            LARGE_PALM_TREE.withChance(0.1f)
//                    ),
//                    SMALL_PALM_TREE
//            )).decorate(Decorator.COUNT_EXTRA.configure(new CountExtraDecoratorConfig(6, 0.1F, 1))));


    private AquaticaConfiguredFeatures() {

    }

    private static <FC extends FeatureConfig> ConfiguredFeature<FC, ?> register(String id, ConfiguredFeature<FC, ?> configuredFeature) {
        return  Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, Aquatica.id(id), configuredFeature);
    }

    public static void init() {
        // NO-OP
    }
}
