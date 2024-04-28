package me.syberiak.colorful_hex.mixin;

import net.minecraft.text.Text;
import net.minecraft.text.StringVisitable;
import net.minecraft.client.gui.screen.ingame.BookScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import me.syberiak.colorful_hex.ColorfulHEX;

@Mixin(BookScreen.WrittenBookContents.class)
public class WrittenBookScreenMixin {
    @SuppressWarnings("InvalidInjectorMethodSignature")
    @ModifyVariable(method = "getPageUnchecked(I)Lnet/minecraft/text/StringVisitable;", at = @At("STORE"), ordinal = 0)
    private StringVisitable injected(StringVisitable stringVisitable) {
        if (!(stringVisitable instanceof Text text)) return stringVisitable;

        return ColorfulHEX.formatHexColors(text);
    }
}