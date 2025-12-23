package com.ombremoon.playingcards.init;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;

public class InitModelOverrides {

    public static void init() {
        ItemProperties.register(InitItems.CARD.get(), ResourceLocation.withDefaultNamespace("value"), (stack, world, player, seed) -> {
            Byte value = stack.get(InitDataComponents.VALUE);
            return value != null ? value : 0;
        });
        ItemProperties.register(InitItems.CARD_COVERED.get(), ResourceLocation.withDefaultNamespace("skin"), (stack, world, player, seed) -> {
            Byte skin = stack.get(InitDataComponents.SKIN_ID);
            return skin != null ? skin : 0;
        });
        ItemProperties.register(InitItems.CARD_DECK.get(), ResourceLocation.withDefaultNamespace("skin"), (stack, world, player, seed) -> {
            Byte skin = stack.get(InitDataComponents.SKIN_ID);
            return skin != null ? skin : 0;
        });
    }
}
