package com.ombremoon.playingcards.platform.services;

import com.ombremoon.playingcards.entity.*;
import com.ombremoon.playingcards.recipes.CardDeckRecipe;
import com.ombremoon.playingcards.tileentity.TileEntityPokerTable;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.util.function.Supplier;

public interface IRegistryHelper {

    Supplier<Block> getPokerTable();
    Supplier<Block> getBarStool();

    Supplier<Item> getPokerTableItem();
    Supplier<Item> getBarStoolItem();

    Supplier<Item> getCardDeck();
    Supplier<Item> getCardCovered();
    Supplier<Item> getCard();

    Supplier<Item> getPokerChipWhite();
    Supplier<Item> getPokerChipRed();
    Supplier<Item> getPokerChipBlue();
    Supplier<Item> getPokerChipGreen();
    Supplier<Item> getPokerChipBlack();

    Supplier<EntityType<EntityCard>> getCardEntityType();
    Supplier<EntityType<EntityCardDeck>> getCardDeckEntityType();
    Supplier<EntityType<EntityPokerChip>> getPokerChipEntityType();
    Supplier<EntityType<EntityDice>> getDiceEntityType();
    Supplier<EntityType<EntitySeat>> getSeatEntityType();

    Supplier<BlockEntityType<TileEntityPokerTable>> getPokerTableBlockEntityType();

    Supplier<RecipeSerializer<CardDeckRecipe>> getDeckRecipeSerializer();
}
