package com.ombremoon.playingcards.block.base;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

/**
 * The base class for Items that place Blocks.
 */
public class BlockItemBase extends BlockItem {

    public BlockItemBase(Block block) {
        super(block, new Properties());
    }
}
