package com.ombremoon.playingcards.init;

import com.ombremoon.playingcards.util.ArrayHelper;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;

public class FabricEntityDataSerializers {

    private static final StreamCodec<RegistryFriendlyByteBuf, Byte[]> STACK_CODEC = StreamCodec.of(
            (buf, bytes) -> buf.writeByteArray(ArrayHelper.toPrimitive(bytes)),
            buf -> ArrayHelper.toObject(buf.readByteArray())
    );

    public static final EntityDataSerializer<Byte[]> STACK = new EntityDataSerializer<>() {

        @Override
        public StreamCodec<? super RegistryFriendlyByteBuf, Byte[]> codec() {
            return STACK_CODEC;
        }

        @Override
        public Byte[] copy(Byte[] bytes) {
            return ArrayHelper.clone(bytes);
        }
    };

    public static void init() {
        EntityDataSerializers.registerSerializer(STACK);
    }
}
