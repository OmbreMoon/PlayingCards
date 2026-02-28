package com.ombremoon.playingcards.platform.services;

import net.minecraft.network.syncher.EntityDataSerializer;

public interface IEntityDataHelper {

    EntityDataSerializer<Byte[]> getStackSerializer();
}
