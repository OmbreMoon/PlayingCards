package com.ombremoon.playingcards.event;

import com.ombremoon.playingcards.item.ItemCardCovered;
import com.ombremoon.playingcards.network.FabricNetworking;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class FabricClientEvents implements ClientModInitializer {

    private boolean wasAttacking = false;

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.screen != null) {
                wasAttacking = false;
                return;
            }

            boolean isAttacking = client.options.keyAttack.isDown();
            if (isAttacking && !wasAttacking) {
                Player player = client.player;
                if (client.level != null && player != null) {
                    ItemStack heldStack = player.getMainHandItem();
                    if (heldStack.getItem() instanceof ItemCardCovered card) {
                        card.flipCard(heldStack, player);
                        FabricNetworking.sendCardInteract("flipinv");
                        client.options.keyAttack.setDown(false);
                    }
                }
            }
            wasAttacking = isAttacking;
        });
    }
}
