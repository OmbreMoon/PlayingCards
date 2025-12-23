package com.ombremoon.playingcards.block.base;

import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;

/**
 * The base class for Blocks that have Containers
 */
public abstract class BlockContainerBase extends BaseEntityBlock {

    /**
     * @param properties The specific properties for the Block. (Creative Tab, hardness, material, etc.)
     */
    public BlockContainerBase (Properties properties) {
        super(properties);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }
}
