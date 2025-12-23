package com.ombremoon.playingcards.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ombremoon.playingcards.entity.EntityDice;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class RenderEntityDice extends EntityRenderer<EntityDice> {

    public RenderEntityDice(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public void render(EntityDice pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBuffer, pPackedLight);

        //Push
        pPoseStack.pushPose();

        //Translate
        pPoseStack.translate(0, 0.15D, 0);

        //Scale
        //matrixStack.func_227862_a_(0.6F, 0.6F, 0.6F);

        //CardHelper.renderItem(new ItemStack(InitItems.DICE_WHITE.get()), 0, 0,0, matrixStack, buffer, combinedLight);

        //Pop
        pPoseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(EntityDice pEntity) {
        return null;
    }
}
