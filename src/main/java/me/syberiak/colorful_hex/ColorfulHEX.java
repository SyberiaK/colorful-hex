package me.syberiak.colorful_hex;

import java.util.List;
import net.minecraft.text.Text;
import net.minecraft.text.Style;
import net.minecraft.text.TextColor;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.MutableText;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ColorfulHEX implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger(ColorfulHEX.class);

    @Override
    public void onInitializeClient() {
        ItemTooltipCallback.EVENT.register(TooltipManager::onItemTooltip);
        LOGGER.info("Initialized successfully.");
    }

    public static Text formatHexColors(Text text) {
        if (!text.getString().contains("#")) return text;

        List<Text> textParts = text.withoutStyle();
        MutableText formattedText = Text.empty();

        textParts.forEach(textPart -> formattedText.append(formatHexColorsPart(textPart)));

        return formattedText;
    }

    public static Text formatHexColorsPart(Text text) {
        Style style = text.getStyle();
        String part = text.getString();
        int startIndex = 0;
        int sharpIndex = part.indexOf('#');

        MutableText formattedPart = Text.empty();

        while (sharpIndex != -1) {
            sharpIndex += startIndex;
            formattedPart.append(Text.literal(part.substring(startIndex, sharpIndex)).setStyle(style));

            startIndex = sharpIndex;
            if (part.length() - sharpIndex >= 7 && isHexColor(part.substring(sharpIndex, sharpIndex + 7))) {
                formattedPart.append(toStyledHexColor(part.substring(sharpIndex, sharpIndex + 7), style));
                startIndex += 7;
            } else if (part.length() - sharpIndex >= 4 &&isHexColor(part.substring(sharpIndex, sharpIndex + 4))) {
                formattedPart.append(toStyledHexColor(part.substring(sharpIndex, sharpIndex + 4), style));
                startIndex += 4;
            }

            if (startIndex >= part.length()) break;

            sharpIndex = part.substring(startIndex).indexOf('#');
        }

        if (startIndex < part.length()) {
            formattedPart.append(part.substring(startIndex));
        }

        return formattedPart;
    }

    public static Text toStyledHexColor(String code) { return toStyledHexColor(code, Style.EMPTY); }

    public static Text toStyledHexColor(String code, Style style) {
        if (!isHexColor(code)) return Text.literal(code).setStyle(style);

        String text = code;
        if (code.length() == 4) {
            String r = code.substring(1, 2), g = code.substring(2, 3), b = code.substring(3, 4);
            code = "#" + r.repeat(2) + g.repeat(2) + b.repeat(2);
        }

        TextColor color = TextColor.parse(code); //.result().orElseThrow();
        ClickEvent copyByClick = new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, text);
        HoverEvent hintByHover = new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.literal("Click to copy!"));
        
        return Text.literal(text).setStyle(style.withColor(color)
                    .withUnderline(true).withClickEvent(copyByClick).withHoverEvent(hintByHover));
    }

    public static boolean isHexColor(String str) {
        return str.matches("#([0-9a-fA-F]{6}|[0-9a-fA-F]{3})");
    }
}