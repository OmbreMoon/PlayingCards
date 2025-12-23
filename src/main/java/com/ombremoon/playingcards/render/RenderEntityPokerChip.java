package com.ombremoon.playingcards.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.ombremoon.playingcards.entity.EntityPokerChip;
import com.ombremoon.playingcards.item.ItemPokerChip;
import com.ombremoon.playingcards.util.CardHelper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.Random;

public class RenderEntityPokerChip extends EntityRenderer<EntityPokerChip> {

    public RenderEntityPokerChip(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public void render(EntityPokerChip pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBuffer, pPackedLight);

        pPoseStack.pushPose();
        pPoseStack.translate(0, 0.01D, 0.07D);
        pPoseStack.scale(0.5F, 0.5F, 0.5F);

        for (byte i = 0; i < pEntity.getStackAmount(); i++) {
            pPoseStack.pushPose();

            Random randomX = new Random(i * 200000);
            Random randomY = new Random(i * 100000);

            pPoseStack.translate(randomX.nextDouble() * 0.05D - 0.025D, 0, randomY.nextDouble() * 0.05D - 0.025D);
            pPoseStack.mulPose(Axis.XN.rotationDegrees(90));

            CardHelper.renderItem(new ItemStack(ItemPokerChip.getPokerChip(pEntity.getIDAt(i))), pEntity.level(), 0, 0,i * 0.032D, pPoseStack, pBuffer, pPackedLight);

            pPoseStack.popPose();
        }

        pPoseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(EntityPokerChip pEntity) {
        return null;
    }
}
