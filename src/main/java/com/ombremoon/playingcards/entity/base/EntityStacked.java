package com.ombremoon.playingcards.entity.base;

import com.ombremoon.playingcards.init.InitEntityDataSerializers;
import com.ombremoon.playingcards.util.ArrayHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public abstract class EntityStacked extends Entity {

    public static final byte MAX_STACK_SIZE = 52;

    protected static final EntityDataAccessor<Byte[]> STACK = SynchedEntityData.defineId(EntityStacked.class, InitEntityDataSerializers.STACK.get());

    public EntityStacked(EntityType<? extends EntityStacked> type, Level world) {
        super(type, world);
    }

    public EntityStacked(EntityType<? extends EntityStacked> type, Level world, Vec3 position) {
        this(type, world);
        setPos(position.x, position.y, position.z);
        setRot(0, 0);
    }

    public int getStackAmount() {
        return this.entityData.get(STACK).length;
    }

    public byte getTopStackID() {
        return getIDAt(getStackAmount() - 1);
    }

    public byte getIDAt(int index) {

        if (index >= 0 && index < getStackAmount()) {
            return this.entityData.get(STACK)[index];
        }

        return 0;
    }

    public void removeFromTop() {
        Byte[] newStack = new Byte[getStackAmount() - 1];

        for (int index = 0; index < newStack.length; index++) {
            newStack[index] = this.entityData.get(STACK)[index];
        }

        this.entityData.set(STACK, newStack);
    }

    public void addToTop(byte id) {

        Byte[] newStack = new Byte[getStackAmount() + 1];

        for (int index = 0; index < getStackAmount(); index++) {
            newStack[index] = this.entityData.get(STACK)[index];
        }

        newStack[newStack.length - 1] = id;

        this.entityData.set(STACK, newStack);
    }

    public void createStack() {
        Byte[] newStack = new Byte[0];
        this.entityData.set(STACK, newStack);
    }

    public void shuffleStack() {

        Byte[] newStack = new Byte[getStackAmount()];

        for (int index = 0; index < getStackAmount(); index++) {
            newStack[index] = this.entityData.get(STACK)[index];
        }

        ArrayHelper.shuffle(newStack);

        this.entityData.set(STACK, newStack);
    }

    @Override
    public void tick() {
        super.tick();

        if (level().isClientSide) {
            noPhysics = false;
        } else {
            noPhysics = !level().noCollision(this);

            if (noPhysics) {
                setDeltaMovement(getDeltaMovement().add(0.0D, 0.02D, 0.0D));
            } else {
                setDeltaMovement(getDeltaMovement().add(0.0D, -0.04D, 0.0D));
            }
        }

        move(MoverType.SELF, getDeltaMovement());

        Vec3 pos = position();
        double size = 0.2D;
        double addAmount = 0.0045D;

        setBoundingBox(new AABB(pos.x - size, pos.y, pos.z - size, pos.x + size, pos.y + 0.03D + (addAmount * getStackAmount()), pos.z + size));
    }

    public abstract void moreData(SynchedEntityData.Builder builder);

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(STACK, new Byte[0]);
        moreData(builder);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
        this.entityData.set(STACK, ArrayHelper.toObject(compoundTag.getByteArray("Stack")));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
        compoundTag.putByteArray("Stack", ArrayHelper.toPrimitive(this.entityData.get(STACK)));
    }

    @Override
    public boolean isPickable() {
        return true;
    }
}
