package com.ombremoon.playingcards.platform;

import com.ombremoon.playingcards.init.FabricEntityDataSerializers;
import com.ombremoon.playingcards.platform.services.IEntityDataHelper;
import net.minecraft.network.syncher.EntityDataSerializer;

public class FabricEntityDataHelper implements IEntityDataHelper {

    @Override
    public EntityDataSerializer<Byte[]> getStackSerializer() {
        return FabricEntityDataSerializers.STACK;
    }
}
