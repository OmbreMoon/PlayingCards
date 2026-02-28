package com.ombremoon.playingcards.platform;

import com.ombremoon.playingcards.init.InitEntityDataSerializers;
import com.ombremoon.playingcards.platform.services.IEntityDataHelper;
import net.minecraft.network.syncher.EntityDataSerializer;

public class NeoForgeEntityDataHelper implements IEntityDataHelper {

    @Override
    public EntityDataSerializer<Byte[]> getStackSerializer() {
        return InitEntityDataSerializers.STACK.get();
    }
}
