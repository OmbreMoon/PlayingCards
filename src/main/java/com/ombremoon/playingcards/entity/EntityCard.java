package com.ombremoon.playingcards.entity;

import com.ombremoon.playingcards.entity.base.EntityStacked;
import com.ombremoon.playingcards.init.InitDataComponents;
import com.ombremoon.playingcards.init.InitEntityTypes;
import com.ombremoon.playingcards.init.InitItems;
import com.ombremoon.playingcards.item.ItemCardCovered;
import com.ombremoon.playingcards.util.ChatHelper;
import com.ombremoon.playingcards.util.ItemHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class EntityCard extends EntityStacked {

    private static final EntityDataAccessor<Float> ROTATION = SynchedEntityData.defineId(EntityCard.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Byte> SKIN_ID = SynchedEntityData.defineId(EntityCard.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Optional<UUID>> DECK_UUID = SynchedEntityData.defineId(EntityCard.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<Boolean> COVERED = SynchedEntityData.defineId(EntityCard.class, EntityDataSerializers.BOOLEAN);

    public EntityCard(EntityType<? extends EntityCard> type, Level world) {
        super(type, world);
    }

    public EntityCard(Level world, Vec3 position, float rotation, byte skinID, UUID deckUUID, boolean covered, byte firstCardID) {
        super(InitEntityTypes.CARD.get(), world, position);

        createStack();
        addToTop(firstCardID);
        this.entityData.set(ROTATION, rotation);
        this.entityData.set(SKIN_ID, skinID);
        this.entityData.set(DECK_UUID, Optional.of(deckUUID));
        this.entityData.set(COVERED, covered);
    }

    public float getRotation() {
        return this.entityData.get(ROTATION);
    }

    public byte getSkinID() {
        return this.entityData.get(SKIN_ID);
    }

    public UUID getDeckUUID() {
        return (this.entityData.get(DECK_UUID).isPresent()) ? this.entityData.get(DECK_UUID).get() : null;
    }

    public boolean isCover() {
        return this.entityData.get(COVERED);
    }

    private void takeCard(Player player) {

        ItemStack card = new ItemStack(InitItems.CARD.get());
        if (this.entityData.get(COVERED)) card = new ItemStack(InitItems.CARD_COVERED.get());

        card.set(InitDataComponents.VALUE, getTopStackID());
        card.set(InitDataComponents.DECK_ID, getDeckUUID());
        card.set(InitDataComponents.SKIN_ID, this.entityData.get(SKIN_ID));
        card.set(InitDataComponents.COVERED, this.entityData.get(COVERED));

        if (!level().isClientSide) {
            ItemHelper.spawnStackAtEntity(level(), player, card);
        }

        removeFromTop();

        if (getStackAmount() <= 0) {
            discard();
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (level().getGameTime() % 20 == 0) {

            BlockPos pos = blockPosition();

            List<EntityCardDeck> closeDecks = level().getEntitiesOfClass(EntityCardDeck.class, new AABB(pos.getX() - 20, pos.getY() - 20, pos.getZ() - 20, pos.getX() + 20, pos.getY() + 20, pos.getZ() + 20));

            boolean foundParentDeck = false;

            for (EntityCardDeck closeDeck : closeDecks) {

                if (getDeckUUID().equals(closeDeck.getUUID())) {
                    foundParentDeck = true;
                }
            }

            if (!foundParentDeck) discard();

            super.onRemovedFromLevel();
        }
    }

    @Override
    public InteractionResult interact(Player pPlayer, InteractionHand pHand) {
        ItemStack stack = pPlayer.getItemInHand(pHand);

        if (stack.getItem() instanceof ItemCardCovered) {

            if (getStackAmount() < MAX_STACK_SIZE) {
                addToTop(stack.get(InitDataComponents.VALUE));
                stack.shrink(1);
            }

            else {
                if (level().isClientSide) ChatHelper.printModMessage(ChatFormatting.RED, Component.translatable("message.stack_full"), pPlayer);
            }
        }

        else takeCard(pPlayer);

        return InteractionResult.SUCCESS;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        this.entityData.set(COVERED, !this.entityData.get(COVERED));
        return true;
    }

    @Override
    public void moreData(SynchedEntityData.Builder builder) {
        builder.define(ROTATION, 0F);
        builder.define(SKIN_ID, (byte) 0);
        builder.define(DECK_UUID, Optional.empty());
        builder.define(COVERED, false);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        this.entityData.set(ROTATION, compoundTag.getFloat("Rotation"));
        this.entityData.set(SKIN_ID, compoundTag.getByte("SkinID"));
        this.entityData.set(DECK_UUID, Optional.of(compoundTag.getUUID("DeckID")));
        this.entityData.set(COVERED, compoundTag.getBoolean("Covered"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        compoundTag.putFloat("Rotation", this.entityData.get(ROTATION));
        compoundTag.putByte("SkinID", this.entityData.get(SKIN_ID));
        compoundTag.putUUID("DeckID", getDeckUUID());
        compoundTag.putBoolean("Covered", this.entityData.get(COVERED));
    }
}
