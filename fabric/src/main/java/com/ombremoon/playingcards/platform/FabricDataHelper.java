package com.ombremoon.playingcards.platform;

import com.ombremoon.playingcards.init.FabricDataComponents;
import com.ombremoon.playingcards.platform.services.IDataHelper;
import net.minecraft.core.component.DataComponentType;

import java.util.UUID;
import java.util.function.Supplier;

public class FabricDataHelper implements IDataHelper {

    @Override public Supplier<DataComponentType<UUID>> getOwnerId() { return () -> FabricDataComponents.OWNER_ID; }
    @Override public Supplier<DataComponentType<String>> getOwnerName() { return () -> FabricDataComponents.OWNER_NAME; }
    @Override public Supplier<DataComponentType<Byte>> getValue() { return () -> FabricDataComponents.VALUE; }
    @Override public Supplier<DataComponentType<UUID>> getDeckId() { return () -> FabricDataComponents.DECK_ID; }
    @Override public Supplier<DataComponentType<Byte>> getSkinId() { return () -> FabricDataComponents.SKIN_ID; }
    @Override public Supplier<DataComponentType<Boolean>> getCovered() { return () -> FabricDataComponents.COVERED; }
}
