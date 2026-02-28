package com.ombremoon.playingcards.main;

import com.ombremoon.playingcards.init.*;
import com.ombremoon.playingcards.network.FabricNetworking;
import net.fabricmc.api.ModInitializer;

public class PlayingCardsFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        CommonClass.init();
        FabricDataComponents.init();
        FabricEntityDataSerializers.init();
        FabricEntityTypes.init();
        FabricItems.init();
        FabricTileEntityTypes.init();
        FabricRecipes.init();
        FabricNetworking.init();
    }
}
