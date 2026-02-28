package com.ombremoon.playingcards.init;

import com.ombremoon.playingcards.platform.Services;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class FabricModelOverrides {

    public static void init() {
        ItemProperties.register(Services.REGISTRY.getCard().get(), ResourceLocation.withDefaultNamespace("value"), new ClampedItemPropertyFunction() {
            @Override
            public float unclampedCall(ItemStack stack, @Nullable ClientLevel world, @Nullable LivingEntity player, int seed) {
                Byte value = stack.get(Services.DATA.getValue().get());
                return value != null ? value : 0;
            }

            @Override
            public float call(ItemStack stack, @Nullable ClientLevel world, @Nullable LivingEntity player, int seed) {
                return unclampedCall(stack, world, player, seed);
            }
        });
        ItemProperties.register(Services.REGISTRY.getCardCovered().get(), ResourceLocation.withDefaultNamespace("skin"), new ClampedItemPropertyFunction() {
            @Override
            public float unclampedCall(ItemStack stack, @Nullable ClientLevel world, @Nullable LivingEntity player, int seed) {
                Byte skin = stack.get(Services.DATA.getSkinId().get());
                return skin != null ? skin : 0;
            }

            @Override
            public float call(ItemStack stack, @Nullable ClientLevel world, @Nullable LivingEntity player, int seed) {
                return unclampedCall(stack, world, player, seed);
            }
        });
        ItemProperties.register(Services.REGISTRY.getCardDeck().get(), ResourceLocation.withDefaultNamespace("skin"), new ClampedItemPropertyFunction() {
            @Override
            public float unclampedCall(ItemStack stack, @Nullable ClientLevel world, @Nullable LivingEntity player, int seed) {
                Byte skin = stack.get(Services.DATA.getSkinId().get());
                return skin != null ? skin : 0;
            }

            @Override
            public float call(ItemStack stack, @Nullable ClientLevel world, @Nullable LivingEntity player, int seed) {
                return unclampedCall(stack, world, player, seed);
            }
        });
    }
}
