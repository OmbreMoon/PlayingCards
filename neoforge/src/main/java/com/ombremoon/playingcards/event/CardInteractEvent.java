package com.ombremoon.playingcards.event;

import com.ombremoon.playingcards.item.ItemCardCovered;
import com.ombremoon.playingcards.main.PCReference;
import com.ombremoon.playingcards.network.ModNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;

@EventBusSubscriber(modid = PCReference.MOD_ID, value = Dist.CLIENT)
public class CardInteractEvent {

    @SubscribeEvent
    public static void onLeftClick(InputEvent.MouseButton.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.screen == null) {
            if (event.getAction() == 1 && event.getButton() == 0) {
                Player player = mc.player;
                if (mc.level != null && player != null) {
                    ItemStack heldStack = player.getMainHandItem();
                    if (heldStack.getItem() instanceof ItemCardCovered card) {
                        card.flipCard(heldStack, player);
                        ModNetworking.cardInteract("flipinv");
                        mc.options.keyAttack.setDown(false);
                    }
                }
            }
        }
    }
}
