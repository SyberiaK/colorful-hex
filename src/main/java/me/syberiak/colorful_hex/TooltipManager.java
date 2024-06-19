package me.syberiak.colorful_hex;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.item.TooltipType;
import net.minecraft.item.Item;
import net.minecraft.text.Text;
import net.minecraft.item.ItemStack;

public class TooltipManager {
    public static void onItemTooltip(ItemStack ignored, Item.TooltipContext ignored1, TooltipType ignored2, List<Text> texts) {
        List<Text> formattedTooltip = new ArrayList<>();
        for (Text line : texts) {
            formattedTooltip.add(ColorfulHEX.formatHexColors(line));
        }

        texts.clear();
        texts.addAll(formattedTooltip);
    }
}