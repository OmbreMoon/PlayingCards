package com.ombremoon.playingcards.main;

import com.ombremoon.playingcards.entity.data.PCDataSerializers;
import com.ombremoon.playingcards.event.CardInteractEvent;
import com.ombremoon.playingcards.init.InitEntityTypes;
import com.ombremoon.playingcards.init.InitModelOverrides;
import com.ombremoon.playingcards.network.ModNetworking;
import com.ombremoon.playingcards.render.*;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(PCReference.MOD_ID)
@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class PlayingCards {

    public PlayingCards() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        CommonClass.init(modEventBus);
        modEventBus.addListener(this::onCommonSetup);
        modEventBus.addListener(this::onClientSetup);
        MinecraftForge.EVENT_BUS.register(this);
        EntityDataSerializers.registerSerializer(PCDataSerializers.STACK);
    }

    private void onCommonSetup(final FMLCommonSetupEvent event) {
        ModNetworking.registerPackets();
        MinecraftForge.EVENT_BUS.register(new CardInteractEvent());
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