package com.ombremoon.playingcards.item;

import com.ombremoon.playingcards.platform.Services;
import com.ombremoon.playingcards.util.CardHelper;
import com.ombremoon.playingcards.util.ItemHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemCard extends ItemCardCovered {

    public ItemCard() {
//        covered = false;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        Byte value = stack.get(Services.DATA.getValue().get());
        if (value != null) {
            tooltipComponents.add(CardHelper.getCardName(value).withStyle(ChatFormatting.GOLD));
        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
