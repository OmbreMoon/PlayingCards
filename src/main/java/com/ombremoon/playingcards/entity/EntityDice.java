package com.ombremoon.playingcards.entity;

import com.ombremoon.playingcards.init.InitEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class EntityDice extends Entity {

    public EntityDice(EntityType<? extends EntityDice> type, Level world) {
        super(type, world);
    }

    public EntityDice(Level world, Vec3 position, float rotation) {
        this(InitEntityTypes.DICE.get(), world);
        setPos(position.x, position.y, position.z);
        setRot(rotation, 0);

        float sin = Mth.sin(this.getYRot() * 0.017453292F - 11);
        float cos = Mth.cos(this.getYRot() * 0.017453292F - 11);

        this.setDeltaMovement(0.5D * cos, 0.2D, 0.5D * sin);
    }

    public void tick() {

        super.tick();

        this.xo = this.blockPosition().getX();
        this.yo = this.blockPosition().getY();
        this.zo = this.blockPosition().getZ();

        Vec3 motion = this.getDeltaMovement();

        if (!this.isNoGravity()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.04D, 0.0D));
        }

        if (this.level().isClientSide) {
            this.noPhysics = false;
        }

        if (!this.onGround() || (this.tickCount + this.getId()) % 4 == 0) {

            this.move(MoverType.SELF, this.getDeltaMovement());
            float f = 0.98F;

            if (this.onGround()) {
                BlockPos pos = new BlockPos(this.blockPosition().getX(), (int) (this.blockPosition().getY() - 1.0D), this.blockPosition().getZ());
                f = this.level().getBlockState(pos).getFriction(this.level(), pos, this) * 0.98F;
            }

            this.setDeltaMovement(this.getDeltaMovement().multiply(f, 0.98D, f));
            if (this.onGround()) {
                this.setDeltaMovement(this.getDeltaMovement().multiply(1.0D, -0.5D, 1.0D));
            }
        }

        if (!this.level().isClientSide) {
            double d0 = this.getDeltaMovement().subtract(motion).lengthSqr();
            if (d0 > 0.01D) {
                this.hasImpulse = true;
            }
        }
    }

    @Override
    public @Nullable Component getCustomName() {
        return Component.literal("6");
    }

    @Override
    public boolean shouldShowName() {
        return true;
    }

    @Override
    public boolean isCustomNameVisible() {
        return true;
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {

    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {

    }
}
