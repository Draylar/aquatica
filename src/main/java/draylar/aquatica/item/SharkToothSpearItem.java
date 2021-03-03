package draylar.aquatica.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TridentItem;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.List;

public class SharkToothSpearItem extends TridentItem {

    public SharkToothSpearItem(Settings settings) {
        super(settings);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);

        tooltip.add(new LiteralText("Pointy! This simple spear, fashioned with").formatted(Formatting.GRAY));
        tooltip.add(new LiteralText("the tooth of a Shark, packs a punch...").formatted(Formatting.GRAY));
        tooltip.add(new LiteralText("or a point. Jab with it or throw it,").formatted(Formatting.GRAY));
        tooltip.add(new LiteralText("but make sure not to miss!").formatted(Formatting.GRAY));
    }
}
