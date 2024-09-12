package me.kokostrike.creatortools.mixin;

import me.kokostrike.creatortools.config.ConfigSettings;
import me.kokostrike.creatortools.config.ConfigSettingsProvider;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ChatHud.class)

public class ChatMixin {
    @Unique
    private final ConfigSettings configSettings = ConfigSettingsProvider.getConfigSettings();

    @ModifyVariable(
            method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;ILnet/minecraft/client/gui/hud/MessageIndicator;Z)V",
            at = @At("HEAD"),
            argsOnly = true
    )
    private Text replaceChatMessage(Text message, Text parameterMessage, MessageSignatureData data, int ticks, MessageIndicator indicator, boolean refreshing) {
        // If the chat filter is disabled, return the message as is.
        if (!configSettings.isChatFilter()) return message;

        // If we're refreshing, we have probably already modified the message, therefore we don't want to do anything.
        if (refreshing) {
            return message;
        }

        // Remove the Streamer Mode functionality by always returning the original message
        return message;
    }
}
