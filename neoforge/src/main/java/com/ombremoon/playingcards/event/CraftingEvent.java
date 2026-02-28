package com.ombremoon.playingcards.event;

import com.ombremoon.playingcards.init.InitDataComponents;
import com.ombremoon.playingcards.item.ItemCardDeck;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class CraftingEvent {

    @SubscribeEvent
    public void onCrafted(PlayerEvent.ItemCraftedEvent event) {

        if (event.getCrafting().getItem() instanceof ItemCardDeck) {

            if (event.getInventory().getItem(4).getItem() == Items.RED_DYE) {
                event.getCrafting().set(InitDataComponents.SKIN_ID, (byte)1);
            }
        }
    }
}
