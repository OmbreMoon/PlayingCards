package com.ombremoon.playingcards.init;

import com.mojang.serialization.Codec;
import com.ombremoon.playingcards.main.PCReference;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.UUID;
import java.util.function.Supplier;

public class InitDataComponents {
    public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(
            Registries.DATA_COMPONENT_TYPE, PCReference.MOD_ID
    );

    public static final Supplier<DataComponentType<UUID>> OWNER_ID = DATA_COMPONENTS.registerComponentType("owner_id",
            builder -> builder.persistent(UUIDUtil.CODEC).networkSynchronized(UUIDUtil.STREAM_CODEC));

    public static final Supplier<DataComponentType<String>> OWNER_NAME = DATA_COMPONENTS.registerComponentType("owner_name",
            builder -> builder.persistent(Codec.STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8));

    public static final Supplier<DataComponentType<Byte>> VALUE = DATA_COMPONENTS.registerComponentType("value",
            builder -> builder.persistent(Codec.BYTE).networkSynchronized(ByteBufCodecs.BYTE));

    public static final Supplier<DataComponentType<UUID>> DECK_ID = DATA_COMPONENTS.registerComponentType("deck_id",
            builder -> builder.persistent(UUIDUtil.CODEC).networkSynchronized(UUIDUtil.STREAM_CODEC));

    public static final Supplier<DataComponentType<Byte>> SKIN_ID = DATA_COMPONENTS.registerComponentType("skin_id",
            builder -> builder.persistent(Codec.BYTE).networkSynchronized(ByteBufCodecs.BYTE));

    public static final Supplier<DataComponentType<Boolean>> COVERED = DATA_COMPONENTS.registerComponentType("covered",
            builder -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));

    public static void init(IEventBus modEventBus) {
        DATA_COMPONENTS.register(modEventBus);
    }
}
