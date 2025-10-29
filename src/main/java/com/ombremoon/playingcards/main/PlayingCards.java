package com.ombremoon.playingcards.main;

import com.ombremoon.playingcards.entity.data.PCDataSerializers;
import com.ombremoon.playingcards.event.CardInteractEvent;
import com.ombremoon.playingcards.init.InitEntityTypes;
import com.ombremoon.playingcards.init.InitModelOverrides;
import com.ombremoon.playingcards.network.ModNetworking;
import com.ombremoon.playingcards.render.*;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;

@Mod(PCReference.MOD_ID)
public class PlayingCards {

    public PlayingCards(IEventBus modEventBus) {
        CommonClass.init(modEventBus);
        modEventBus.addListener(this::onCommonSetup);
        modEventBus.addListener(this::onClientSetup);
        NeoForge.EVENT_BUS.register(this);
        EntityDataSerializers.registerSerializer(PCDataSerializers.STACK);
    }

    private void onCommonSetup(final FMLCommonSetupEvent event) {
        ModNetworking.registerPackets();
        NeoForge.EVENT_BUS.register(new CardInteractEvent());
    }

    private void onClientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            InitModelOverrides.init();

            EntityRenderers.register(InitEntityTypes.CARD.get(), RenderEntityCard::new);
            EntityRenderers.register(InitEntityTypes.CARD_DECK.get(), RenderEntityCardDeck::new);
            EntityRenderers.register(InitEntityTypes.POKER_CHIP.get(), RenderEntityPokerChip::new);
            EntityRenderers.register(InitEntityTypes.DICE.get(), RenderEntityDice::new);
            EntityRenderers.register(InitEntityTypes.SEAT.get(), RenderEntitySeat::new);
        });
    }
}
