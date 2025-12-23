package com.ombremoon.playingcards.tileentity;

import com.ombremoon.playingcards.init.InitTileEntityTypes;
import com.ombremoon.playingcards.tileentity.base.TileEntityBase;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.UUID;

public class TileEntityPokerTable extends TileEntityBase {

    private UUID ownerID;
    private String ownerName;

    public TileEntityPokerTable(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    public TileEntityPokerTable(BlockPos pPos, BlockState pBlockState) {
        super(InitTileEntityTypes.POKER_TABLE.get(), pPos, pBlockState);
    }

    public void setOwner(Player player) {
        this.ownerID = player.getUUID();
        this.ownerName = player.getDisplayName().getString();
    }

    public UUID getOwnerID() {
        return this.ownerID;
    }

    @Override
    public void loadAdditional(CompoundTag pTag, HolderLookup.Provider lookupProvidier) {
        ownerID = pTag.getUUID("OwnerID");
        ownerName = pTag.getString("OwnerName");
        super.loadAdditional(pTag, lookupProvidier);
    }

    @Override
    public void saveAdditional(CompoundTag nbt, HolderLookup.Provider lookupProvidier) {
        nbt.putUUID("OwnerID", ownerID);
        nbt.putString("OwnerName", ownerName);
        super.saveAdditional(nbt, lookupProvidier);
    }
}