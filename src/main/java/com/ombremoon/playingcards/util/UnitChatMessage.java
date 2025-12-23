package com.ombremoon.playingcards.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;

public class UnitChatMessage {

    private final String unitName;
    private final Entity[] players;

    public UnitChatMessage(String unitName, Entity... players) {
        this.unitName = unitName;
        this.players = players;
    }

    public void printMessage(ChatFormatting format, MutableComponent message) {

        for (Entity player : players) {
            player.sendSystemMessage(Component.literal("[").withStyle(ChatFormatting.WHITE).append(getUnitName().append("] ")).append(message.withStyle(format)));
        }
    }

    private MutableComponent getUnitName() {
        return Component.translatable("unitname." + unitName);
    }

}
