package draylar.aquatica.world;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import draylar.aquatica.registry.AquaticaBiomes;
import draylar.aquatica.util.FastNoise;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.ChunkRandom;

import java.util.Arrays;

public class AquaticaBiomeSource extends BiomeSource {

    public static final Codec<AquaticaBiomeSource> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(RegistryLookupCodec.of(Registry.BIOME_KEY).forGetter((source) -> {
            return source.biomeRegistry;
        }), Codec.LONG.fieldOf("seed").stable().forGetter((source) -> {
            return source.seed;
        })).apply(instance, instance.stable(AquaticaBiomeSource::new));
    });

    private final Registry<Biome> biomeRegistry;
    private final FastNoise islandNoise = new FastNoise();
    private final FastNoise biomeNoise = new FastNoise();
    private final ChunkRandom random;
    private final long seed;

//    private static final Set<Biome> biomes = ImmutableSet.of(Biomes.OCEAN, Biomes.OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN, Biomes.DEEP_WARM_OCEAN);;

    public AquaticaBiomeSource(Registry<Biome> biomeRegistry, long seed) {
        super(Arrays.asList());
        this.biomeRegistry = biomeRegistry;

        this.random = new ChunkRandom(seed);
        this.seed = seed;
        this.random.consume(17292);

        islandNoise.SetFractalOctaves(1);
        islandNoise.SetFrequency(0.01F);
        islandNoise.SetSeed((int) seed);

        biomeNoise.SetFractalOctaves(5);
        biomeNoise.SetFrequency(0.001F);
        biomeNoise.SetSeed((int) seed);
    }

    public long getSeed() {
        return seed;
    }

    @Override
    public Biome getBiomeForNoiseGen(int biomeX, int biomeY, int biomeZ) {
        biomeX = biomeX * 4;
        biomeZ = biomeZ * 4;

        // 16, 16: 512 out
        double distanceFromCenter = Math.sqrt((long) biomeX * (long) biomeX + (long) biomeZ * (long) biomeZ);

        float islandNoiseResult = islandNoise.GetSimplexFractal(biomeX, biomeZ);

        // we assume the noise is between -.7 and 7, but verify:
        if(islandNoiseResult < 0) islandNoiseResult = (float) Math.max(-.7, islandNoiseResult);
        if(islandNoiseResult > 0) islandNoiseResult = (float) Math.min(.7, islandNoiseResult);

        islandNoiseResult = (float) convertRange(islandNoiseResult, -.7, .7, 0, 1);


        if (distanceFromCenter < 80 + islandNoiseResult * 75) {
            return getCenterBiome(biomeX, biomeZ);
        }

        else if (distanceFromCenter < 140 + islandNoiseResult * 150) {
            return getEdgeBiome(biomeX, biomeZ);
        }

        // outside the 100 blocks
        else if (distanceFromCenter < 300) {
            return getBiome(AquaticaBiomes.CALM_LAKE_KEY);
        }

        else if (distanceFromCenter < 600) {
            return getBiome(AquaticaBiomes.STARFISH_VALLEY_KEY);
        }

        else {
//            return BuiltinRegistries.BIOME.get(BiomeKeys.WARM_OCEAN.getValue());
            return getBiome(AquaticaBiomes.STARFISH_VALLEY_KEY);
        }
    }

    private Biome getCenterBiome(int posX, int posZ) {
        float islandNoiseResult = biomeNoise.GetSimplexFractal(posX * 2, posZ * 2);

        // we assume the noise is between -.7 and 7, but verify:
        if(islandNoiseResult < 0) islandNoiseResult = (float) Math.max(-.7, islandNoiseResult);
        if(islandNoiseResult > 0) islandNoiseResult = (float) Math.min(.7, islandNoiseResult);

        islandNoiseResult = (float) convertRange(islandNoiseResult, -.7, .7, 0, 1);

        if(islandNoiseResult < .40) {
            return getBiome(AquaticaBiomes.TROPICAL_WILDERNESS_KEY);
        }

        else if (islandNoiseResult < .65) {
            return getBiome(AquaticaBiomes.AQUATIC_ISLAND_KEY);
        }

        else {
            return getBiome(AquaticaBiomes.TROPICAL_JUNGLE_KEY);
        }
    }

    private Biome getEdgeBiome(int posX, int posZ) {
        float islandNoiseResult = biomeNoise.GetSimplexFractal(posX, posZ);

        // we assume the noise is between -.7 and 7, but verify:
        if(islandNoiseResult < 0) islandNoiseResult = (float) Math.max(-.7, islandNoiseResult);
        if(islandNoiseResult > 0) islandNoiseResult = (float) Math.min(.7, islandNoiseResult);

        islandNoiseResult = (float) convertRange(islandNoiseResult, -.7, .7, 0, 1);

        if(islandNoiseResult < .35) {
            return getBiome(AquaticaBiomes.ROCKY_SHORE_KEY);
        }

        else if (islandNoiseResult < .55) {
            return getBiome(AquaticaBiomes.BREEZY_BEACH_KEY);
        }

        else {
            return getBiome(AquaticaBiomes.WATERSIDE_PEAKS_KEY);
        }
    }

    private double convertRange(double value, double oldMin, double oldMax, double newMin, double newMax) {
        return (((value - oldMin) * (newMax - newMin)) / (oldMax - oldMin)) + newMin;
    }

    @Override
    protected Codec<? extends BiomeSource> getCodec() {
        return CODEC;
    }

    @Override
    public BiomeSource withSeed(long seed) {
        return new AquaticaBiomeSource(biomeRegistry, seed);
    }

//    @Override
//    public float getNoiseRange(int int_1, int int_2) {
//        int int_3 = int_1 / 2;
//        int int_4 = int_2 / 2;
//        int int_5 = int_1 % 2;
//        int int_6 = int_2 % 2;
//        float float_1 = 100.0F - MathHelper.sqrt((float)(int_1 * int_1 + int_2 * int_2)) * 8.0F;
//        float_1 = MathHelper.clamp(float_1, -100.0F, 80.0F);
//
//        for(int int_7 = -12; int_7 <= 12; ++int_7) {
//            for(int int_8 = -12; int_8 <= 12; ++int_8) {
//                long long_1 = int_3 + int_7;
//                long long_2 = int_4 + int_8;
//                if (long_1 * long_1 + long_2 * long_2 > 4096L && this.noise.sample((double)long_1, (double)long_2) < -0.8999999761581421D) {
//                    float float_2 = (MathHelper.abs((float)long_1) * 3439.0F + MathHelper.abs((float)long_2) * 147.0F) % 13.0F + 9.0F;
//                    float float_3 = (float)(int_5 - int_7 * 2);
//                    float float_4 = (float)(int_6 - int_8 * 2);
//                    float float_5 = 100.0F - MathHelper.sqrt(float_3 * float_3 + float_4 * float_4) * float_2;
//                    float_5 = MathHelper.clamp(float_5, -100.0F, 80.0F);
//                    float_1 = Math.max(float_1, float_5);
//                }
//            }
//        }
//
//        return float_1;
//    }

    public Biome getBiome(RegistryKey<Biome> key) {
        return biomeRegistry.get(key.getValue());
    }
}
