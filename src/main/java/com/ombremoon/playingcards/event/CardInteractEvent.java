package com.ombremoon.playingcards.event;

import com.ombremoon.playingcards.item.ItemCardCovered;
import com.ombremoon.playingcards.network.ModNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.InputEvent;

public class CardInteractEvent {

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public void onLeftClick(InputEvent.InteractionKeyMappingTriggered event) {

        Minecraft mc = Minecraft.getInstance();

        if (mc.screen == null) {

            if (event.isAttack()) {

                Player player = mc.player;

                if (mc.level != null && player != null) {

                    ItemStack heldStack = player.getMainHandItem();

                    if (heldStack.getItem() instanceof ItemCardCovered card) {
                        card.flipCard(heldStack, player);

                        ModNetworking.cardInteract("flipinv");
                        event.setCanceled(true);
                    }
                }
            }
        }
    }
}
