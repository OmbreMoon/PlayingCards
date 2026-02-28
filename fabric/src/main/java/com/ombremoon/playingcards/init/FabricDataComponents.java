package com.ombremoon.playingcards.init;

import com.mojang.serialization.Codec;
import com.ombremoon.playingcards.main.CommonClass;
import net.minecraft.core.Registry;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;

import java.util.UUID;

public class FabricDataComponents {

    public static DataComponentType<UUID> OWNER_ID;
    public static DataComponentType<String> OWNER_NAME;
    public static DataComponentType<Byte> VALUE;
    public static DataComponentType<UUID> DECK_ID;
    public static DataComponentType<Byte> SKIN_ID;
    public static DataComponentType<Boolean> COVERED;

    public static void init() {
        OWNER_ID = Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, CommonClass.customLocation("owner_id"),
                DataComponentType.<UUID>builder().persistent(UUIDUtil.CODEC).networkSynchronized(UUIDUtil.STREAM_CODEC).build());
        OWNER_NAME = Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, CommonClass.customLocation("owner_name"),
                DataComponentType.<String>builder().persistent(Codec.STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8).build());
        VALUE = Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, CommonClass.customLocation("value"),
                DataComponentType.<Byte>builder().persistent(Codec.BYTE).networkSynchronized(ByteBufCodecs.BYTE).build());
        DECK_ID = Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, CommonClass.customLocation("deck_id"),
                DataComponentType.<UUID>builder().persistent(UUIDUtil.CODEC).networkSynchronized(UUIDUtil.STREAM_CODEC).build());
        SKIN_ID = Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, CommonClass.customLocation("skin_id"),
                DataComponentType.<Byte>builder().persistent(Codec.BYTE).networkSynchronized(ByteBufCodecs.BYTE).build());
        COVERED = Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, CommonClass.customLocation("covered"),
                DataComponentType.<Boolean>builder().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build());
    }
}
