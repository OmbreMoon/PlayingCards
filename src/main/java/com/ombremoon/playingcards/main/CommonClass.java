package com.ombremoon.playingcards.main;

import com.ombremoon.playingcards.init.*;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;

public class CommonClass {

    public static void init(IEventBus modEventBus) {
        InitDataComponents.init(modEventBus);
        InitEntityDataSerializers.init(modEventBus);
        InitEntityTypes.init(modEventBus);
        InitItems.init(modEventBus);
        InitTileEntityTypes.init(modEventBus);
        InitRecipes.init(modEventBus);
    }

    public static ResourceLocation customLocation(String name) {
        return ResourceLocation.fromNamespaceAndPath(PCReference.MOD_ID, name);
    }
}
