package com.ombremoon.playingcards.init;

import com.ombremoon.playingcards.platform.Services;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;

public class FabricModelOverrides {

    public static void init() {
        ItemProperties.register(Services.REGISTRY.getCard().get(), ResourceLocation.withDefaultNamespace("value"), (stack, world, player, seed) -> {
            Byte value = stack.get(Services.DATA.getValue().get());
            return value != null ? value : 0;
        });
        ItemProperties.register(Services.REGISTRY.getCardCovered().get(), ResourceLocation.withDefaultNamespace("skin"), (stack, world, player, seed) -> {
            Byte skin = stack.get(Services.DATA.getSkinId().get());
            return skin != null ? skin : 0;
        });
        ItemProperties.register(Services.REGISTRY.getCardDeck().get(), ResourceLocation.withDefaultNamespace("skin"), (stack, world, player, seed) -> {
            Byte skin = stack.get(Services.DATA.getSkinId().get());
            return skin != null ? skin : 0;
        });
    }
}
