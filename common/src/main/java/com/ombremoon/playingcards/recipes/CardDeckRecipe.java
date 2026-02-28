package com.ombremoon.playingcards.recipes;

import com.ombremoon.playingcards.platform.Services;
import com.ombremoon.playingcards.util.ItemHelper;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class CardDeckRecipe extends CustomRecipe {

    public CardDeckRecipe(CraftingBookCategory category) {
        super(category);
    }

    @Override
    public boolean matches(CraftingInput craftingInput, Level level) {

        if (craftingInput.width() == 3 && craftingInput.height() == 3) {
            boolean matches = true;

            for (int i = 0; i < craftingInput.size(); ++i) {

                ItemStack stackInSlot = craftingInput.getItem(i);

                if (i != 4) {

                    if (stackInSlot.getItem() != Items.PAPER) {
                        matches = false;
                    }
                }
            }

            ItemStack middleSlot = craftingInput.getItem(4);

            if (middleSlot.getItem() != Items.BLUE_DYE && middleSlot.getItem() != Items.RED_DYE && middleSlot.getItem() != Items.BLACK_DYE && middleSlot.getItem() != Items.PINK_DYE) {
                matches = false;
            }

            return matches;
        } else {
            return false;
        }
    }

    @Override
    public ItemStack assemble(CraftingInput craftingInput, HolderLookup.Provider provider) {

        ItemStack result = new ItemStack(Services.REGISTRY.getCardDeck().get());
        ItemStack middleSlot = craftingInput.getItem(4);

        if (middleSlot.getItem() == Items.RED_DYE) {
            result.set(Services.DATA.getSkinId().get(), (byte)1);
        }

        else if (middleSlot.getItem() == Items.BLACK_DYE) {
            result.set(Services.DATA.getSkinId().get(), (byte)2);
        }

        else if (middleSlot.getItem() == Items.PINK_DYE) {
            result.set(Services.DATA.getSkinId().get(), (byte)3);
        }

        return result;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return pWidth >= 3 && pHeight >= 3;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Services.REGISTRY.getDeckRecipeSerializer().get();
    }
}
