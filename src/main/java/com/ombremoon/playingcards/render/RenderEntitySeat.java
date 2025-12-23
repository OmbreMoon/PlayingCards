package com.ombremoon.playingcards.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ombremoon.playingcards.entity.EntitySeat;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class RenderEntitySeat extends EntityRenderer<EntitySeat> {

    public RenderEntitySeat(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public void render(EntitySeat pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
    }

    @Override
    public ResourceLocation getTextureLocation(EntitySeat pEntity) {
        return null;
    }
}
