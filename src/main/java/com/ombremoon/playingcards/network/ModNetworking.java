package com.ombremoon.playingcards.network;

import com.ombremoon.playingcards.main.CommonClass;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;

public class ModNetworking {

    public static void cardInteract(String command) {
        sendToServer(new PacketInteractCard(command));
    }

    public static void registerPackets(final RegisterPayloadHandlerEvent event) {
        final IPayloadRegistrar registrar = event.registrar(CommonClass.MOD_ID).versioned("1.0");
        registrar.play(PacketInteractCard.TYPE, PacketInteractCard::new, handler -> handler
                .server(PacketInteractCard::handle)
        );
    }

    protected static  <MSG> void sendToServer(MSG message) {
        PacketDistributor.sendToServer(message);
    }

    protected static  <MSG> void sendToPlayer(MSG message, ServerPlayer serverPlayer) {
        PacketDistributor.sendToPlayer(serverPlayer, message);
    }

    protected static  <MSG> void sendToClients(MSG message) {
        PacketDistributor.sendToAllPlayers(message);
    }
}
