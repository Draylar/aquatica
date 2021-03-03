package draylar.aquatica.item;

import draylar.aquatica.block.BeachChairBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public class BeachChairItem extends BlockItem {

    private final BeachChairBlock block;

    public BeachChairItem(BeachChairBlock block, Settings settings) {
        super(block, settings);
        this.block = block;
    }

    @Override
    public Text getName(ItemStack stack) {
        return new LiteralText(block.getDyeColor().name()).append(new LiteralText(" ")).append(new LiteralText(block.getWoodType().name())).append(new LiteralText(" Chair"));
    }
}
