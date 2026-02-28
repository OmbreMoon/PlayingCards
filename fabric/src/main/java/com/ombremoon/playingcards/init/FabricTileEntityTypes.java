package com.ombremoon.playingcards.init;

import com.ombremoon.playingcards.main.CommonClass;
import com.ombremoon.playingcards.tileentity.TileEntityPokerTable;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class FabricTileEntityTypes {

    public static BlockEntityType<TileEntityPokerTable> POKER_TABLE;

    public static void init() {
        POKER_TABLE = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, CommonClass.customLocation("poker_table"),
                BlockEntityType.Builder.of(TileEntityPokerTable::new, FabricItems.POKER_TABLE).build(null));
    }
}
