package com.ombremoon.playingcards.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class Location {

    public final Level world;
    public final int x, y, z;
    private final BlockPos blockPos;

    public Location (Level world, BlockPos pos) {
        this(world, pos.getX(), pos.getY(), pos.getZ());
    }

    public Location (Level world, int x, int y, int z) {

        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;

        blockPos = new BlockPos(x, y, z);
    }

    public Location (BlockEntity tileEntity) {
        this(tileEntity.getLevel(), tileEntity.getBlockPos().getX(), tileEntity.getBlockPos().getY(), tileEntity.getBlockPos().getZ());
    }

    public Location (Entity entity) {
        this(entity.level(), entity.getBlockX(), entity.getBlockY(), entity.getBlockZ());
    }

    public BlockPos getBlockPos () {
        return blockPos;
    }

    public BlockState getBlockState () {

        if (getBlockPos() == null) {
            return null;
        }

        return world.getBlockState(getBlockPos());
    }

    public Block getBlock () {

        if (getBlockState() == null) {
            return null;
        }

        return getBlockState().getBlock();
    }

    public BlockEntity getTileEntity () {
        return world.getBlockEntity(getBlockPos());
    }

    @Override
    public boolean equals (Object obj) {

        if (obj instanceof Location) {
            Location newLoc = (Location) obj;
            return world == newLoc.world && x == newLoc.x && y == newLoc.y && z == newLoc.z;
        }

        return super.equals(obj);
    }

    @Override
    public String toString () {
        return "[" + x + ", " + y + ", " + z + "]";
    }
}
