package com.ombremoon.playingcards.network;

import com.ombremoon.playingcards.item.ItemCardCovered;
import com.ombremoon.playingcards.main.CommonClass;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public class FabricNetworking {

    public static final CustomPacketPayload.Type<InteractCardPayload> TYPE =
            new CustomPacketPayload.Type<>(CommonClass.customLocation("interact_card"));

    public static final StreamCodec<RegistryFriendlyByteBuf, InteractCardPayload> STREAM_CODEC =
            StreamCodec.composite(ByteBufCodecs.STRING_UTF8, InteractCardPayload::command, InteractCardPayload::new);

    public record InteractCardPayload(String command) implements CustomPacketPayload {
        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

    public static void init() {
        PayloadTypeRegistry.playC2S().register(TYPE, STREAM_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(TYPE, (payload, context) -> {
            Player player = context.player();
            context.server().execute(() -> {
                if (payload.command().equalsIgnoreCase("flipinv")) {
                    Item item = player.getMainHandItem().getItem();
                    if (item instanceof ItemCardCovered card) {
                        card.flipCard(player.getMainHandItem(), player);
                    }
                }
            });
        });
    }

    public static void sendCardInteract(String command) {
        ClientPlayNetworking.send(new InteractCardPayload(command));
    }
}
