package com.ombremoon.playingcards.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;

public class ChatHelper {

    /**
     * Used to print the main mod's messages.
     * @param format The color or style of the message.
     * @param component The message.
     * @param players The Players that will receive the message.
     */
    public static void printModMessage (ChatFormatting format, MutableComponent component, Entity... players) {
        UnitChatMessage unitMessage = new UnitChatMessage("mod_name", players);
        unitMessage.printMessage(format, component);
    }
}
