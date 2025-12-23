package com.ombremoon.playingcards.main;

import com.ombremoon.playingcards.init.InitEntityTypes;
import com.ombremoon.playingcards.init.InitModelOverrides;
import com.ombremoon.playingcards.render.*;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(PCReference.MOD_ID)
public class PlayingCards {

    public PlayingCards(IEventBus modEventBus, ModContainer modContainer) {
        CommonClass.init(modEventBus);
        modEventBus.addListener(this::onCommonSetup);
        modEventBus.addListener(this::onClientSetup);
    }

    private void onCommonSetup(final FMLCommonSetupEvent event) {
    }

    private void onClientSetup(final FMLClientSetupEvent event) {
        InitModelOverrides.init();
        EntityRenderers.register(InitEntityTypes.CARD.get(), RenderEntityCard::new);
        EntityRenderers.register(InitEntityTypes.CARD_DECK.get(), RenderEntityCardDeck::new);
        EntityRenderers.register(InitEntityTypes.POKER_CHIP.get(), RenderEntityPokerChip::new);
        EntityRenderers.register(InitEntityTypes.DICE.get(), RenderEntityDice::new);
        EntityRenderers.register(InitEntityTypes.SEAT.get(), RenderEntitySeat::new);
    }
}