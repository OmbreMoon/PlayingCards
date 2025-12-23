package com.ombremoon.playingcards.util;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CardHelper {

    public static final String[] CARD_SKIN_NAMES = {"card.skin.blue", "card.skin.red", "card.skin.black", "card.skin.pig"};

    public static void renderItem(ItemStack stack, Level level, double offsetX, double offsetY, double offsetZ, PoseStack matrixStack, MultiBufferSource buffer, int combinedLight) {
        matrixStack.pushPose();
        matrixStack.translate(offsetX, offsetY, offsetZ);
        var renderer = Minecraft.getInstance().getItemRenderer();
        BakedModel model = renderer.getModel(stack, level, null, 0);
        renderer.render(stack, ItemDisplayContext.GROUND, false, matrixStack, buffer, combinedLight, OverlayTexture.NO_OVERLAY, model);
        matrixStack.popPose();
    }

    public static MutableComponent getCardName(int id) {

        String type = "card.ace";

        int typeID = id / 4 + 1;

        if (typeID > 1 && typeID < 11) {
            type = "" + typeID;
        }

        if (typeID > 10) {
            type = "card.jack";

            if (typeID > 11) {
                type = "card.queen";

                if (typeID > 12) {
                    type = "card.king";
                }
            }
        }

        String suite = "card.spades";

        int suiteID = id % 4;

        switch(suiteID) {

            case 1: {
                suite = "card.clubs";
                break;
            }

            case 2: {
                suite = "card.diamonds";
                break;
            }

            case 3: {
                suite = "card.hearts";
                break;
            }
        }

        return Component.translatable(type).append(" ").append(Component.translatable("card.of").append(" ").append(Component.translatable(suite)));
    }
}
