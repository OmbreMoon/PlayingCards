package com.ombremoon.playingcards.network;


import com.ombremoon.playingcards.main.PCReference;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = PCReference.MOD_ID)
public class ModNetworking {

    public static void cardInteract(String command) {
        PacketDistributor.sendToServer(new InteractCardPayload(command));
    }

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(PCReference.MOD_ID).optional();
        registrar.playToServer(
                InteractCardPayload.TYPE,
                InteractCardPayload.STREAM_CODEC,
                InteractCardPayload::handleInteractCard
        );
    }
}
