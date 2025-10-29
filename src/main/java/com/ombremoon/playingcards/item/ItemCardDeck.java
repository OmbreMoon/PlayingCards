package com.ombremoon.playingcards.item;

import com.ombremoon.playingcards.entity.EntityCardDeck;
import com.ombremoon.playingcards.item.base.ItemBase;
import com.ombremoon.playingcards.main.PCReference;
import com.ombremoon.playingcards.util.CardHelper;
import com.ombremoon.playingcards.util.ItemHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemCardDeck extends ItemBase {

    public ItemCardDeck() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        CompoundTag nbt = ItemHelper.getNBT(pStack);
        pTooltipComponents.add(Component.translatable("lore.cover").append(" ").withStyle(ChatFormatting.GRAY).append(Component.translatable(CardHelper.CARD_SKIN_NAMES[nbt.getByte("SkinID")]).withStyle(ChatFormatting.AQUA)));
    }

    public void fillItemGroup(BuildCreativeModeTabContentsEvent output) {
        for (byte colorID = 0; colorID < CardHelper.CARD_SKIN_NAMES.length; colorID++) {

            ItemStack stack = new ItemStack(this);
            CompoundTag nbt = ItemHelper.getNBT(stack);

            nbt.putByte("SkinID", colorID);
            output.accept(stack);
        }
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level world = pContext.getLevel();
        if (!world.isClientSide) {
            CompoundTag nbt = ItemHelper.getNBT(pContext.getItemInHand());
            EntityCardDeck cardDeck = new EntityCardDeck(world, pContext.getClickLocation(), pContext.getRotation(), nbt.getByte("SkinID"));
            world.addFreshEntity(cardDeck);
            pContext.getItemInHand().shrink(1);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.CONSUME;
    }
}
