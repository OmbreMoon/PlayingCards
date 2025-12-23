package com.ombremoon.playingcards.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.ombremoon.playingcards.entity.EntityCardDeck;
import com.ombremoon.playingcards.init.InitDataComponents;
import com.ombremoon.playingcards.init.InitItems;
import com.ombremoon.playingcards.util.CardHelper;
import com.ombremoon.playingcards.util.ItemHelper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class RenderEntityCardDeck extends EntityRenderer<EntityCardDeck> {

    public RenderEntityCardDeck(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public void render(EntityCardDeck pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBuffer, pPackedLight);

        ItemStack cardStack = new ItemStack(InitItems.CARD_COVERED.get());
        cardStack.set(InitDataComponents.SKIN_ID, pEntity.getSkinID());

        pPoseStack.pushPose();
        pPoseStack.mulPose(Axis.YP.rotationDegrees(-pEntity.getRotation() + 180));
        pPoseStack.scale(1.5F, 1.5F, 1.5F);

        for (byte i = 0; i < pEntity.getStackAmount() + 2; i++) {
            CardHelper.renderItem(cardStack, pEntity.level(), 0, i * 0.003D, 0, pPoseStack, pBuffer, pPackedLight);
        }

        pPoseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(EntityCardDeck pEntity) {
        return null;
    }
}
