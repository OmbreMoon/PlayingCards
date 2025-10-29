package com.ombremoon.playingcards.network;

import com.ombremoon.playingcards.item.ItemCardCovered;
import com.ombremoon.playingcards.main.CommonClass;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class PacketInteractCard implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<PacketInteractCard> TYPE = new CustomPacketPayload.Type<>(CommonClass.customLocation("interact_card"));
    private final String command;

    public PacketInteractCard (String command) {
        this.command = command;
    }

    public PacketInteractCard (FriendlyByteBuf buf) {
        command = buf.readUtf(11).trim();
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeUtf(command, 11);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(PacketInteractCard packet, IPayloadContext ctx) {

        ctx.enqueueWork(() -> {

            ServerPlayer player = (ServerPlayer) ctx.player();

            if (player != null) {

                if (packet.command.equalsIgnoreCase("flipinv")) {

                    Item item = player.getMainHandItem().getItem();

                    if (item instanceof ItemCardCovered) {
                        ItemCardCovered card = (ItemCardCovered)player.getMainHandItem().getItem();
                        card.flipCard(player.getMainHandItem(), player);
                    }
                }
            }
        });
    }
}
