package me.syberiak.colorful_hex.mixin;

import java.util.List;
import net.minecraft.text.Text;
import net.minecraft.text.MutableText;
import net.minecraft.client.gui.hud.ChatHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import me.syberiak.colorful_hex.ColorfulHEX;

@Mixin(ChatHud.class)
public class ChatHudMixin {
    @ModifyVariable(method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;Lnet/minecraft/client/gui/hud/MessageIndicator;)V",
                    at = @At("HEAD"))
    private Text onAddMessage(Text message) {
        if (!message.getString().contains("#")) return message;
        List<Text> msgParts = message.withoutStyle();

        MutableText messageText = Text.empty();
        for (int i = 0; i < msgParts.size(); i++) {
            Text msgPart = msgParts.get(i);
            Text formattedMsgPart = msgPart.getString().contains("#") ? ColorfulHEX.parseMessagePart(msgPart) : msgPart;
            messageText.append(formattedMsgPart);
        }
        
        return messageText;
    }
}

