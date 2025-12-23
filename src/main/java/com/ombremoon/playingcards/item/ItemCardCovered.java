package com.ombremoon.playingcards.item;

import com.ombremoon.playingcards.entity.EntityCard;
import com.ombremoon.playingcards.entity.EntityCardDeck;
import com.ombremoon.playingcards.init.InitDataComponents;
import com.ombremoon.playingcards.init.InitItems;
import com.ombremoon.playingcards.item.base.ItemBase;
import com.ombremoon.playingcards.main.PCReference;
import com.ombremoon.playingcards.util.CardHelper;
import com.ombremoon.playingcards.util.ItemHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class ItemCardCovered extends ItemBase {

    public ItemCardCovered() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        Byte skinID = stack.get(InitDataComponents.SKIN_ID);
        tooltipComponents.add(Component.translatable("lore.cover").append(" ").withStyle(ChatFormatting.GRAY).append(Component.translatable(CardHelper.CARD_SKIN_NAMES[skinID != null ? skinID : 0]).withStyle(ChatFormatting.AQUA)));
    }

    public void flipCard(ItemStack heldItem, LivingEntity entity) {

        if (entity instanceof Player player) {

            if (heldItem.getItem() instanceof ItemCardCovered) {
                Item nextCard = InitItems.CARD.get();
                boolean covered = true;
                if (heldItem.get(InitDataComponents.COVERED) != null)
                    covered = heldItem.get(InitDataComponents.COVERED);

                if (!covered) nextCard = InitItems.CARD_COVERED.get();

                ItemStack newCard = new ItemStack(nextCard);
                newCard.set(InitDataComponents.VALUE, heldItem.get(InitDataComponents.VALUE));
                newCard.set(InitDataComponents.DECK_ID, heldItem.get(InitDataComponents.DECK_ID));
                newCard.set(InitDataComponents.SKIN_ID, heldItem.get(InitDataComponents.SKIN_ID));
                newCard.set(InitDataComponents.COVERED, !covered);

                player.setItemInHand(InteractionHand.MAIN_HAND, newCard);
            }
        }
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (pLevel.getGameTime() % 60 == 0) {

            if (pEntity instanceof Player player) {
                BlockPos pos = player.blockPosition();

                UUID id = pStack.get(InitDataComponents.DECK_ID);

                if (id != null) {
                    if (id.getLeastSignificantBits() == 0) {
                        return;
                    }

                    List<EntityCardDeck> closeDecks = pLevel.getEntitiesOfClass(EntityCardDeck.class, new AABB(pos.getX() - 20, pos.getY() - 20, pos.getZ() - 20, pos.getX() + 20, pos.getY() + 20, pos.getZ() + 20));

                    boolean found = false;

                    for (EntityCardDeck closeDeck : closeDecks) {

                        if (closeDeck.getUUID().equals(id)) {
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        player.getInventory().getItem(pSlotId).shrink(1);
                    }
                }
            }
        }
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Player player = pContext.getPlayer();

        if (player != null) {

            if (!player.isCrouching()) {

                BlockPos pos = pContext.getClickedPos();
                List<EntityCardDeck> closeDecks = pContext.getLevel().getEntitiesOfClass(EntityCardDeck.class, new AABB(pos.getX() - 8, pos.getY() - 8, pos.getZ() - 8, pos.getX() + 8, pos.getY() + 8, pos.getZ() + 8));

                ItemStack stack = pContext.getItemInHand();
                UUID deckID = stack.get(InitDataComponents.DECK_ID);
                if (deckID != null) {

                    for (EntityCardDeck closeDeck : closeDecks) {

                        if (closeDeck.getUUID().equals(deckID)) {

                            Level world = pContext.getLevel();
                            Byte skinID = stack.get(InitDataComponents.SKIN_ID);
                            Boolean covered = stack.get(InitDataComponents.COVERED);
                            EntityCard cardDeck = new EntityCard(world, pContext.getClickLocation(), pContext.getRotation(), skinID != null ? skinID : 0, deckID, covered != null ? covered : true, pContext.getItemInHand().get(InitDataComponents.VALUE));
                            world.addFreshEntity(cardDeck);
                            pContext.getItemInHand().shrink(1);

                            return InteractionResult.SUCCESS;
                        }
                    }
                }
            }
        }

        return InteractionResult.PASS;
    }
}
