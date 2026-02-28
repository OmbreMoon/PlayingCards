package com.ombremoon.playingcards.platform;

import com.ombremoon.playingcards.init.InitDataComponents;
import com.ombremoon.playingcards.platform.services.IDataHelper;
import net.minecraft.core.component.DataComponentType;

import java.util.UUID;
import java.util.function.Supplier;

public class NeoForgeDataHelper implements IDataHelper {

    @Override public Supplier<DataComponentType<UUID>> getOwnerId() { return InitDataComponents.OWNER_ID; }
    @Override public Supplier<DataComponentType<String>> getOwnerName() { return InitDataComponents.OWNER_NAME; }
    @Override public Supplier<DataComponentType<Byte>> getValue() { return InitDataComponents.VALUE; }
    @Override public Supplier<DataComponentType<UUID>> getDeckId() { return InitDataComponents.DECK_ID; }
    @Override public Supplier<DataComponentType<Byte>> getSkinId() { return InitDataComponents.SKIN_ID; }
    @Override public Supplier<DataComponentType<Boolean>> getCovered() { return InitDataComponents.COVERED; }
}
