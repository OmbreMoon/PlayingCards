package com.ombremoon.playingcards.block;

import com.mojang.serialization.MapCodec;
import com.ombremoon.playingcards.tileentity.TileEntityPokerTable;
import com.ombremoon.playingcards.block.base.BlockContainerBase;
import com.ombremoon.playingcards.init.InitTileEntityTypes;
import com.ombremoon.playingcards.util.Location;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.BeaconBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class BlockPokerTable extends BlockContainerBase {
    public static final MapCodec<BlockPokerTable> CODEC = simpleCodec(BlockPokerTable::new);
    private static final BooleanProperty NORTH = BooleanProperty.create("north");
    private static final BooleanProperty EAST = BooleanProperty.create("east");
    private static final BooleanProperty SOUTH = BooleanProperty.create("south");
    private static final BooleanProperty WEST = BooleanProperty.create("west");

    private static final BooleanProperty NORTHWEST = BooleanProperty.create("northwest");
    private static final BooleanProperty NORTHEAST = BooleanProperty.create("northeast");
    private static final BooleanProperty SOUTHWEST = BooleanProperty.create("southwest");
    private static final BooleanProperty SOUTHEAST = BooleanProperty.create("southeast");

    private static final VoxelShape AABB = Block.box(0, 0, 0, 16, 15, 16);

    public BlockPokerTable(Block.Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(NORTH, false).setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false).setValue(NORTHWEST, false).setValue(NORTHEAST, false).setValue(SOUTHWEST, false).setValue(SOUTHEAST, false));
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        if (pPlacer instanceof Player player) {

            Location location = new Location(pLevel, pPos);
            BlockEntity tileEntity = location.getTileEntity();

            if (tileEntity instanceof TileEntityPokerTable pokerTable)
                pokerTable.setOwner(player);
        }
    }

    /**
     * Checks if the Block at the given pos can connect to the Block given by the Direction.
     */
    private boolean canConnectTo (LevelAccessor world, BlockPos pos, int offX, int offZ) {
        BlockPos otherPos = pos.offset(offX, 0, offZ);
        Block otherBlock = world.getBlockState(otherPos).getBlock();
        return otherBlock instanceof BlockPokerTable;
    }

    /*
        Methods for Block properties.
     */

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return getState(pContext.getLevel(), pContext.getClickedPos());
    }

    private BlockState getState (LevelAccessor world, BlockPos pos) {
        boolean north = canConnectTo(world, pos, 0, -1);
        boolean east = canConnectTo(world, pos, 1, 0);
        boolean south = canConnectTo(world, pos, 0, 1);
        boolean west = canConnectTo(world, pos, -1, 0);

        boolean northwest = canConnectTo(world, pos, -1, -1);
        boolean northeast = canConnectTo(world, pos, 1, -1);
        boolean southwest = canConnectTo(world, pos, -1, 1);
        boolean southeast = canConnectTo(world, pos, 1, 1);

        return defaultBlockState().setValue(NORTH, north).setValue(EAST, east).setValue(SOUTH, south).setValue(WEST, west).setValue(NORTHWEST, northwest).setValue(NORTHEAST, northeast).setValue(SOUTHWEST, southwest).setValue(SOUTHEAST, southeast);
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {
        return getState(pLevel, pCurrentPos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(NORTH, SOUTH, EAST, WEST, NORTHWEST, NORTHEAST, SOUTHWEST, SOUTHEAST);
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

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return InitTileEntityTypes.POKER_TABLE.get().create(blockPos, blockState);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }
}
