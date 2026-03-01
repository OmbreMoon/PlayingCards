package com.ombremoon.playingcards.item;

import com.ombremoon.playingcards.entity.EntityCard;
import com.ombremoon.playingcards.entity.EntityCardDeck;
import com.ombremoon.playingcards.platform.Services;
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
        Byte skinID = stack.get(Services.DATA.getSkinId().get());
        tooltipComponents.add(Component.translatable("lore.cover").append(" ").withStyle(ChatFormatting.GRAY).append(Component.translatable(CardHelper.CARD_SKIN_NAMES[skinID != null ? skinID : 0]).withStyle(ChatFormatting.AQUA)));
    }

    public void flipCard(ItemStack heldItem, LivingEntity entity) {

        if (entity instanceof Player player) {

            if (heldItem.getItem() instanceof ItemCardCovered) {
                Item nextCard = Services.REGISTRY.getCard().get();
                boolean covered = true;
                if (heldItem.get(Services.DATA.getCovered().get()) != null)
                    covered = heldItem.get(Services.DATA.getCovered().get());

                if (!covered) nextCard = Services.REGISTRY.getCardCovered().get();

                ItemStack newCard = new ItemStack(nextCard);
                newCard.set(Services.DATA.getValue().get(), heldItem.get(Services.DATA.getValue().get()));
                newCard.set(Services.DATA.getDeckId().get(), heldItem.get(Services.DATA.getDeckId().get()));
                newCard.set(Services.DATA.getSkinId().get(), heldItem.get(Services.DATA.getSkinId().get()));
                newCard.set(Services.DATA.getCovered().get(), !covered);

                player.setItemInHand(InteractionHand.MAIN_HAND, newCard);
            }
        }
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (pLevel.getGameTime() % 60 == 0) {

            if (pEntity instanceof Player player) {
                BlockPos pos = player.blockPosition();

                UUID id = pStack.get(Services.DATA.getDeckId().get());

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
                UUID deckID = stack.get(Services.DATA.getDeckId().get());
                if (deckID != null) {

                    for (EntityCardDeck closeDeck : closeDecks) {

                        if (closeDeck.getUUID().equals(deckID)) {

                            Level world = pContext.getLevel();
                            Byte skinID = stack.get(Services.DATA.getSkinId().get());
                            Boolean covered = stack.get(Services.DATA.getCovered().get());
                            Byte cardValue = pContext.getItemInHand().get(Services.DATA.getValue().get());
                            EntityCard cardDeck = new EntityCard(world, pContext.getClickLocation(), pContext.getRotation(), skinID != null ? skinID : 0, deckID, covered != null ? covered : true, cardValue != null ? cardValue : 0);
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
