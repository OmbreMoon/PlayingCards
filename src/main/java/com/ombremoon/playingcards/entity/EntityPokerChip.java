package com.ombremoon.playingcards.entity;

import com.ombremoon.playingcards.entity.base.EntityStacked;
import com.ombremoon.playingcards.init.InitDataComponents;
import com.ombremoon.playingcards.init.InitEntityTypes;
import com.ombremoon.playingcards.init.InitItems;
import com.ombremoon.playingcards.item.ItemPokerChip;
import com.ombremoon.playingcards.util.ChatHelper;
import com.ombremoon.playingcards.util.ItemHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;
import java.util.UUID;

public class EntityPokerChip extends EntityStacked {

    private static final EntityDataAccessor<Optional<UUID>> OWNER_UUID = SynchedEntityData.defineId(EntityPokerChip.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<String> OWNER_NAME = SynchedEntityData.defineId(EntityPokerChip.class, EntityDataSerializers.STRING);

    public EntityPokerChip(EntityType<? extends EntityPokerChip> type, Level world) {
        super(type, world);
    }

    public EntityPokerChip(Level world, Vec3 position, UUID ownerID, String ownerName, byte firstChipID) {
        super(InitEntityTypes.POKER_CHIP.get(), world, position);

        createStack();
        addToTop(firstChipID);
        this.entityData.set(OWNER_UUID, Optional.of(ownerID));
        this.entityData.set(OWNER_NAME, ownerName);
    }

    public UUID getOwnerUUID() {
        return (this.entityData.get(OWNER_UUID).isPresent()) ? this.entityData.get(OWNER_UUID).get() : null;
    }

    private void takeChip(Player player) {

        byte chipID = getTopStackID();

        if (!level().isClientSide) spawnChip(player, ItemPokerChip.getPokerChip(chipID), 1);

        removeFromTop();

        if (getStackAmount() <= 0) {
            discard();
        }
    }

    @Override
    public InteractionResult interact(Player pPlayer, InteractionHand pHand) {

        ItemStack stack = pPlayer.getItemInHand(pHand);

        if (stack.getItem() instanceof ItemPokerChip) {

            UUID ownerID = stack.get(InitDataComponents.OWNER_ID);

            if (ownerID != null) {

                if (ownerID.equals(getOwnerUUID())) {

                    if (pPlayer.isCrouching()) {

                        while (true) {

                            if (getStackAmount() < MAX_STACK_SIZE && stack.getCount() > 0) {
                                ItemPokerChip chip = (ItemPokerChip) stack.getItem();
                                addToTop(chip.getChipID());
                                stack.shrink(1);
                            }

                            else break;
                        }
                    }

                    else {

                        if (getStackAmount() < MAX_STACK_SIZE) {
                            ItemPokerChip chip = (ItemPokerChip) stack.getItem();
                            addToTop(chip.getChipID());
                            stack.shrink(1);
                        }

                        else {
                            if (level().isClientSide) ChatHelper.printModMessage(ChatFormatting.RED, Component.translatable("message.stack_full"), pPlayer);
                        }
                    }
                }

                else if (level().isClientSide) ChatHelper.printModMessage(ChatFormatting.RED, Component.translatable("message.stack_owner_error"), pPlayer);
            }
        }

        else takeChip(pPlayer);

        return InteractionResult.SUCCESS;
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {

        if (pSource.getDirectEntity() instanceof Player player) {

            int whiteAmount = 0;
            int redAmount = 0;
            int blueAmount = 0;
            int greenAmount = 0;
            int blackAmount = 0;

            for (int i = 0; i < this.entityData.get(STACK).length; i++) {

                byte chipID = getIDAt(i);

                if (chipID == 0) whiteAmount++;
                if (chipID == 1) redAmount++;
                if (chipID == 2) blueAmount++;
                if (chipID == 3) greenAmount++;
                if (chipID == 4) blackAmount++;
            }

            if (whiteAmount > 0) spawnChip(player, InitItems.POKER_CHIP_WHITE.get(), whiteAmount);
            if (redAmount > 0) spawnChip(player, InitItems.POKER_CHIP_RED.get(), redAmount);
            if (blueAmount > 0) spawnChip(player, InitItems.POKER_CHIP_BLUE.get(), blueAmount);
            if (greenAmount > 0) spawnChip(player, InitItems.POKER_CHIP_GREEN.get(), greenAmount);
            if (blackAmount > 0) spawnChip(player, InitItems.POKER_CHIP_BLACK.get(), blackAmount);

            discard();

            return false;
        }

        return true;
    }

    private void spawnChip(Player player, Item item, int amount) {

        if (!level().isClientSide) {
            ItemStack chip = new ItemStack(item, amount);
            chip.set(InitDataComponents.OWNER_ID, getOwnerUUID());
            chip.set(InitDataComponents.OWNER_NAME, this.entityData.get(OWNER_NAME));
            ItemHelper.spawnStackAtEntity(level(), player, chip);
        }
    }

    @Override
    public void tick() {
        super.tick();

        Vec3 pos = position();
        double size = 0.1D;
        double addAmount = 0.01575D;

        setBoundingBox(new AABB(pos.x - size, pos.y, pos.z - size, pos.x + size, pos.y + 0.02D + (addAmount * getStackAmount()), pos.z + size));
    }

    @Override
    public void moreData(SynchedEntityData.Builder builder) {
        builder.define(OWNER_UUID, Optional.empty());
        builder.define(OWNER_NAME, "");
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.entityData.set(OWNER_UUID, Optional.of(compoundTag.getUUID("OwnerID")));
        this.entityData.set(OWNER_NAME, compoundTag.getString("OwnerName"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putUUID("OwnerID", getOwnerUUID());
        compoundTag.putString("OwnerName", this.entityData.get(OWNER_NAME));
    }
}
