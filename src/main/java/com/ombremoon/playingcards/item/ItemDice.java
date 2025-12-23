package com.ombremoon.playingcards.item;

import com.ombremoon.playingcards.entity.EntityDice;
import com.ombremoon.playingcards.item.base.ItemBase;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemDice extends ItemBase {

    public ItemDice() {
        super(new Item.Properties().stacksTo(5));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);

        EntityDice cardDeck = new EntityDice(pLevel, pPlayer.position(), pPlayer.getYRot());
        pLevel.addFreshEntity(cardDeck);
        stack.shrink(1);

        return InteractionResultHolder.success(stack);
    }
}
