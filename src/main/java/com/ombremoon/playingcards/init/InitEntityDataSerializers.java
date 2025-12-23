package com.ombremoon.playingcards.init;

import com.ombremoon.playingcards.main.PCReference;
import com.ombremoon.playingcards.util.ArrayHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class InitEntityDataSerializers {
    public static final DeferredRegister<EntityDataSerializer<?>> ENTITY_DATA_SERIALIZERS = DeferredRegister.create(NeoForgeRegistries.ENTITY_DATA_SERIALIZERS, PCReference.MOD_ID);

    public static final Supplier<EntityDataSerializer<Byte[]>> STACK = ENTITY_DATA_SERIALIZERS.register("stack", () -> new EntityDataSerializer<Byte[]>() {

        public void write(FriendlyByteBuf friendlyByteBuf, Byte[] bytes) {
            friendlyByteBuf.writeByteArray(ArrayHelper.toPrimitive(bytes));
        }

        public Byte[] read(FriendlyByteBuf friendlyByteBuf) {
            return ArrayHelper.toObject(friendlyByteBuf.readByteArray());
        }

        @Override
        public StreamCodec<? super RegistryFriendlyByteBuf, Byte[]> codec() {
            return StreamCodec.of(this::write, this::read);
        }

        @Override
        public Byte[] copy(Byte[] bytes) {
            return ArrayHelper.clone(bytes);
        }
    });

    public static void init(IEventBus modEventBus) {
        ENTITY_DATA_SERIALIZERS.register(modEventBus);
    }
}
