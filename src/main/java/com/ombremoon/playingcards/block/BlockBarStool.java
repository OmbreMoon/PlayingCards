package com.ombremoon.playingcards.block;

import com.ombremoon.playingcards.entity.EntitySeat;
import com.ombremoon.playingcards.block.base.BlockBase;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class BlockBarStool extends BlockBase {
    public static final IntegerProperty FACING = IntegerProperty.create("facing", 0, 7);
    private static final VoxelShape AABB = Block.box(3, 0, 3, 13, 16, 13);

    public BlockBarStool() {
        super(Block.Properties.of().sound(SoundType.WOOD).strength(1).noOcclusion());
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, 0));
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        EntitySeat.createSeat(level, pos, player);
        return InteractionResult.SUCCESS;
    }

    /*
        Methods for Block properties.
     */

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
        if (pContext.getPlayer() != null) {
            return defaultBlockState().setValue(FACING, Mth.floor((double) ((pContext.getPlayer().getYRot() + 180) * 8.0F / 360.0F) + 0.5D) & 7);
        }

        return super.getStateForPlacement(pContext);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }

    /*
        Methods for Blocks that are not full and solid cubes.
     */

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return AABB;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return AABB;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return true;
    }


}
