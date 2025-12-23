package com.ombremoon.playingcards.tileentity.base;

import com.ombremoon.playingcards.util.Location;
import com.ombremoon.playingcards.util.UnitChatMessage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public abstract class TileEntityBase extends BlockEntity {

    public TileEntityBase(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    protected UnitChatMessage getUnitName(Player player) {
        return new UnitChatMessage(getLocation().getBlock().getName().getString(), player);
    }

    public Location getLocation() {
        return new Location(level, worldPosition);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider lookupProvider) {
        loadAdditional(pkt.getTag(), lookupProvider);
    }

    @Override
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        loadAdditional(tag, lookupProvider);
    }


    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        CompoundTag nbtTagCompound = new CompoundTag();
        if (this.level != null)
            saveAdditional(nbtTagCompound, this.level.registryAccess());

        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider lookupProvider) {
        CompoundTag nbt = new CompoundTag();
        saveAdditional(nbt, lookupProvider);
        return nbt;
    }

    @Override
    public CompoundTag getPersistentData() {
        CompoundTag nbt = new CompoundTag();
        if (this.level != null)
            saveAdditional(nbt, this.level.registryAccess());

        return nbt;
    }
}
