package com.ombremoon.playingcards.network;

import com.ombremoon.playingcards.item.ItemCardCovered;
import com.ombremoon.playingcards.main.CommonClass;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record InteractCardPayload(String command) implements CustomPacketPayload {
    public static final Type<InteractCardPayload> TYPE = new Type<>(CommonClass.customLocation("interact_card"));
    public static final StreamCodec<RegistryFriendlyByteBuf, InteractCardPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, com.ombremoon.playingcards.network.InteractCardPayload::command,
            com.ombremoon.playingcards.network.InteractCardPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handleInteractCard(final InteractCardPayload payload, final IPayloadContext context) {
        Player player = context.player();
        if (payload.command.equalsIgnoreCase("flipinv")) {
            Item item = player.getMainHandItem().getItem();
            if (item instanceof ItemCardCovered) {
                ItemCardCovered card = (ItemCardCovered)player.getMainHandItem().getItem();
                card.flipCard(player.getMainHandItem(), player);
            }
        }
    }
}
