package me.syberiak.colorful_hex;

import net.minecraft.text.Text;
import net.minecraft.text.Style;
import net.minecraft.text.TextColor;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.MutableText;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ColorfulHEX implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("Colorful HEX");

    @Override
    public void onInitializeClient() { LOGGER.info("Initialized successfully."); }

    public static Text parseMessagePart(Text part) {
        Style style = part.getStyle();
        String[] words = part.getString().split("\\s+");
        MutableText formattedText = Text.empty();

        for (int i = 0; i < words.length; i++) {
            if (i != 0) formattedText.append(" ");
            String word = words[i];
            Text formattedWord = word.contains("#") ? parseHexColor(word, style) : Text.literal(word).setStyle(style);
            formattedText.append(formattedWord);
        }

        return formattedText;
    }

    public static Text parseHexColor(String word, Style baseStyle) {
        /*
         * The code is just a mess tbh
         * TODO: try to refactor this crap
         */
        
        if (!word.contains("#") || word.length() < 3) return Text.of(word);

        int codeStartInd = word.indexOf('#');
        String hexCode = word.substring(codeStartInd);

        if (hexCode.length() <= 3 || (hexCode.length() > 7 && isHex(hexCode.substring(0, 8)))) {
            return Text.of(word);
        }

        int codeEndInd = Math.min(codeStartInd + 7, word.length());
        
        hexCode = hexCode.substring(0, codeEndInd - codeStartInd);
        if (!isHexColor(hexCode)) {      
            if (hexCode.length() >= 3 && isHex(hexCode.substring(0, 5))) return Text.of(word);

            hexCode = hexCode.substring(0, 4);
            codeEndInd = codeStartInd + 4;

            if (!isHexColor(hexCode)) return Text.of(word); 
        }

        LOGGER.info("Got HEX color! " + hexCode);

        MutableText textBeforeCode = Text.literal(word.substring(0, codeStartInd)).setStyle(baseStyle);
        Text codeText = toStyledHex(hexCode, baseStyle);
        MutableText textAfterCode = Text.literal(word.substring(codeEndInd)).setStyle(baseStyle);
        
        return textBeforeCode.append(codeText).append(textAfterCode);
    }

    public static Text toStyledHex(String code) { return toStyledHex(code, Style.EMPTY); }

    public static Text toStyledHex(String code, Style style) {
        if (!isHexColor(code)) return Text.literal(code).setStyle(style);

        String text = code;
        if (code.length() == 4) {
            String r = code.substring(1, 2), g = code.substring(2, 3), b = code.substring(3, 4);
            code = "#" + r.repeat(2) + g.repeat(2) + b.repeat(2);
        }
        
        TextColor color = TextColor.parse(code);
        ClickEvent copyByClick = new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, text);
        HoverEvent hintByHover = new HoverEvent(HoverEvent.Action.SHOW_TEXT, Text.literal("Click to copy!"));
        
        return Text.literal(text).setStyle(style.withColor(color)
                    .withUnderline(true).withClickEvent(copyByClick).withHoverEvent(hintByHover));
    }

    public static boolean isHexColor(String str) {
        return str.matches("#([0-9a-fA-F]{6}|[0-9a-fA-F]{3})");
    }

    public static boolean isHex(String str) {
        return str.matches("#[0-9a-fA-F]+");
    }
}
