package me.syberiak.colorful_hex;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.text.Text;
import net.minecraft.item.ItemStack;
import net.minecraft.client.item.TooltipContext;

public class TooltipManager {
    public static void onItemTooltip(ItemStack _stack, TooltipContext _context, List<Text> lines) {
        List<Text> formattedTooltip = new ArrayList<>();
        for (Text line : lines) {
            formattedTooltip.add(ColorfulHEX.formatHexColors(line));
        }

        lines.clear();
        lines.addAll(formattedTooltip);
    }
}