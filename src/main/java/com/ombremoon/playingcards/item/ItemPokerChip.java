package com.ombremoon.playingcards.item;

import com.ombremoon.playingcards.entity.EntityPokerChip;
import com.ombremoon.playingcards.init.InitDataComponents;
import com.ombremoon.playingcards.init.InitItems;
import com.ombremoon.playingcards.item.base.ItemBase;
import com.ombremoon.playingcards.tileentity.TileEntityPokerTable;
import com.ombremoon.playingcards.util.ItemHelper;
import com.ombremoon.playingcards.util.Location;
import com.ombremoon.playingcards.util.StringHelper;
import com.ombremoon.playingcards.util.UnitChatMessage;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class ItemPokerChip extends ItemBase {

    private final byte chipID;
    private final int value;

    public ItemPokerChip(byte chipID, int value) {
        super(new Properties());
        this.chipID = chipID;
        this.value = value;
    }

    private UnitChatMessage getUnitMessage(Player... players) {
        return new UnitChatMessage("poker_chip", players);
    }

    public byte getChipID() {
        return this.chipID;
    }

    public static Item getPokerChip(byte pokerChipID) {

        switch (pokerChipID) {
            case 1:
                return InitItems.POKER_CHIP_RED.get();
            case 2:
                return InitItems.POKER_CHIP_BLUE.get();
            case 3:
                return InitItems.POKER_CHIP_GREEN.get();
            case 4:
                return InitItems.POKER_CHIP_BLACK.get();
        }

        return InitItems.POKER_CHIP_WHITE.get();
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        UUID ownerID = stack.get(InitDataComponents.OWNER_ID);

        if (ownerID != null) {
            tooltipComponents.add(Component.literal(ChatFormatting.GRAY + "Owner: " + ChatFormatting.GOLD + stack.get(InitDataComponents.OWNER_NAME)));
        }

        else tooltipComponents.add(Component.literal(ChatFormatting.GRAY + "Owner: " + ChatFormatting.GOLD + "Not set"));

        tooltipComponents.add(Component.literal(ChatFormatting.GRAY + "Value (1): " + ChatFormatting.GOLD + value));

        if (stack.getCount() > 1) {
            tooltipComponents.add(Component.literal(ChatFormatting.GRAY + "Value (" + stack.getCount() + "): " + ChatFormatting.GOLD + StringHelper.printCommas(value * stack.getCount())));
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack heldItem = pPlayer.getItemInHand(pUsedHand);

        if (pPlayer.isCrouching()) {

            UnitChatMessage unitMessage = getUnitMessage(pPlayer);
            UUID ownerID = heldItem.get(InitDataComponents.OWNER_ID);

            if (ownerID == null) {

                heldItem.set(InitDataComponents.OWNER_ID, pPlayer.getUUID());
                heldItem.set(InitDataComponents.OWNER_NAME, pPlayer.getDisplayName().getString());

                if (pLevel.isClientSide) unitMessage.printMessage(ChatFormatting.GREEN, Component.translatable("message.poker_chip_owner_set"));
            }

            else if (pLevel.isClientSide) unitMessage.printMessage(ChatFormatting.RED, Component.translatable("message.poker_chip_owner_error"));

            return InteractionResultHolder.success(heldItem);
        }

        return InteractionResultHolder.fail(heldItem);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {

        Player player = pContext.getPlayer();

        if (player != null) {

            if (!player.isCrouching()) {

                Level world = pContext.getLevel();
                ItemStack stack = pContext.getItemInHand();
                Location location = new Location(world, pContext.getClickedPos());

                UnitChatMessage unitMessage = getUnitMessage(player);
                UUID ownerID = stack.get(InitDataComponents.OWNER_ID);

                if (ownerID != null) {

                    String ownerName = stack.get(InitDataComponents.OWNER_NAME);

                    if (location.getBlockState().hasBlockEntity()) {

                        BlockEntity tileEntity = location.getTileEntity();

                        if (tileEntity instanceof TileEntityPokerTable pokerTable) {

                            if (!ownerID.equals(pokerTable.getOwnerID())) {

                                if (world.isClientSide) unitMessage.printMessage(ChatFormatting.RED, Component.translatable("message.poker_chip_table_error"));
                                return InteractionResult.PASS;
                            }
                        }
                    }

                    EntityPokerChip chip = new EntityPokerChip(world, pContext.getClickLocation(), ownerID, ownerName, chipID);
                    world.addFreshEntity(chip);
                    pContext.getItemInHand().shrink(1);
                }

                else if (world.isClientSide) unitMessage.printMessage(ChatFormatting.RED, Component.translatable("message.poker_chip_owner_missing"));

                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }
}
