package com.ombremoon.playingcards.init;

import com.ombremoon.playingcards.main.PCReference;
import com.ombremoon.playingcards.tileentity.TileEntityPokerTable;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class InitTileEntityTypes {

    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, PCReference.MOD_ID);

    public static final Supplier<BlockEntityType<TileEntityPokerTable>> POKER_TABLE = TILE_ENTITY_TYPES.register("poker_table", () -> BlockEntityType.Builder.of(TileEntityPokerTable::new, InitItems.POKER_TABLE.get()).build(null));

    public static void init(IEventBus modEventBus) {
        TILE_ENTITY_TYPES.register(modEventBus);
    }
}
