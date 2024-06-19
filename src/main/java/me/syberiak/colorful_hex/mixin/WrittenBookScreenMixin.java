package me.syberiak.colorful_hex.mixin;

import net.minecraft.text.MutableText;
import net.minecraft.client.gui.screen.ingame.BookScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import me.syberiak.colorful_hex.ColorfulHEX;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BookScreen.Contents.class)
public class WrittenBookScreenMixin {
    @Inject(method = "getPage(I)Lnet/minecraft/text/StringVisitable;", at = @At("RETURN"), cancellable = true)
    private void modifyPageContent(CallbackInfoReturnable<MutableText> cir) {
        cir.setReturnValue(ColorfulHEX.formatHexColors(cir.getReturnValue()));
    }
}