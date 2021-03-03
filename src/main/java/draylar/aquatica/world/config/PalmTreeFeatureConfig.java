package draylar.aquatica.world.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.feature.FeatureConfig;

public class PalmTreeFeatureConfig implements FeatureConfig {

    public static final Codec<PalmTreeFeatureConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("treeHeight").forGetter(PalmTreeFeatureConfig::getTreeHeight),
            Codec.INT.fieldOf("treeHeightVary").forGetter(PalmTreeFeatureConfig::getTreeHeightVary),
            Codec.INT.fieldOf("leavesRadius").forGetter(PalmTreeFeatureConfig::getLeavesRadius),
            Codec.INT.fieldOf("leavesRadiusVary").forGetter(PalmTreeFeatureConfig::getLeavesRadiusVary)
    ).apply(instance, PalmTreeFeatureConfig::new));

    private final int treeHeight;
    private final int treeHeightVary;
    private final int leavesRadius;
    private final int leavesRadiusVary;

    public PalmTreeFeatureConfig() {
        this.treeHeight = 15;
        this.treeHeightVary = 10;
        this.leavesRadius = 5;
        this.leavesRadiusVary = 0;
    }

    public PalmTreeFeatureConfig(int treeHeight, int treeHeightVary, int leavesRadius, int leavesRadiusVary) {
        this.treeHeight = treeHeight;
        this.treeHeightVary = treeHeightVary;
        this.leavesRadius = leavesRadius;
        this.leavesRadiusVary = leavesRadiusVary;
    }

    public int getTreeHeight() {
        return treeHeight;
    }

    public int getTreeHeightVary() {
        return treeHeightVary;
    }

    public int getLeavesRadius() {
        return leavesRadius;
    }

    public int getLeavesRadiusVary() {
        return leavesRadiusVary;
    }
}
