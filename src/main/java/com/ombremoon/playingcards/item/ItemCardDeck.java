package com.ombremoon.playingcards.item;

import com.ombremoon.playingcards.entity.EntityCardDeck;
import com.ombremoon.playingcards.init.InitDataComponents;
import com.ombremoon.playingcards.item.base.ItemBase;
import com.ombremoon.playingcards.main.PCReference;
import com.ombremoon.playingcards.util.CardHelper;
import com.ombremoon.playingcards.util.ItemHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemCardDeck extends ItemBase {

    public ItemCardDeck() {
        super(new Item.Properties().stacksTo(1));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        Byte skinID = stack.get(InitDataComponents.SKIN_ID);
        tooltipComponents.add(Component.translatable("lore.cover").append(" ").withStyle(ChatFormatting.GRAY).append(Component.translatable(CardHelper.CARD_SKIN_NAMES[skinID != null ? skinID : 0]).withStyle(ChatFormatting.AQUA)));
    }

    public void fillItemGroup(CreativeModeTab.Output output) {
        for (byte colorID = 0; colorID < CardHelper.CARD_SKIN_NAMES.length; colorID++) {

            ItemStack stack = new ItemStack(this);
            stack.set(InitDataComponents.SKIN_ID, colorID);
            output.accept(stack);
        }
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level world = pContext.getLevel();
        if (!world.isClientSide) {
            Byte skinID = pContext.getItemInHand().get(InitDataComponents.SKIN_ID);
            EntityCardDeck cardDeck = new EntityCardDeck(world, pContext.getClickLocation(), pContext.getRotation(), skinID != null ? skinID : 0);
            world.addFreshEntity(cardDeck);
            pContext.getItemInHand().shrink(1);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.CONSUME;
    }
}
