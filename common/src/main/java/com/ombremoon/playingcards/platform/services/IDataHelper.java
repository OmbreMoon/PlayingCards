package com.ombremoon.playingcards.platform.services;

import net.minecraft.core.component.DataComponentType;

import java.util.UUID;
import java.util.function.Supplier;

public interface IDataHelper {

    Supplier<DataComponentType<UUID>> getOwnerId();
    Supplier<DataComponentType<String>> getOwnerName();
    Supplier<DataComponentType<Byte>> getValue();
    Supplier<DataComponentType<UUID>> getDeckId();
    Supplier<DataComponentType<Byte>> getSkinId();
    Supplier<DataComponentType<Boolean>> getCovered();
}
