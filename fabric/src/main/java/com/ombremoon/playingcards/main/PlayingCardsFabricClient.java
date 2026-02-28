package com.ombremoon.playingcards.main;

import com.ombremoon.playingcards.init.FabricEntityTypes;
import com.ombremoon.playingcards.init.FabricModelOverrides;
import com.ombremoon.playingcards.render.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class PlayingCardsFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        FabricModelOverrides.init();
        EntityRendererRegistry.register(FabricEntityTypes.CARD, RenderEntityCard::new);
        EntityRendererRegistry.register(FabricEntityTypes.CARD_DECK, RenderEntityCardDeck::new);
        EntityRendererRegistry.register(FabricEntityTypes.POKER_CHIP, RenderEntityPokerChip::new);
        EntityRendererRegistry.register(FabricEntityTypes.DICE, RenderEntityDice::new);
        EntityRendererRegistry.register(FabricEntityTypes.SEAT, RenderEntitySeat::new);
    }
}
