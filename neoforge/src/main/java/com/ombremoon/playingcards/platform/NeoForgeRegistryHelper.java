package com.ombremoon.playingcards.platform;

import com.ombremoon.playingcards.entity.*;
import com.ombremoon.playingcards.init.*;
import com.ombremoon.playingcards.platform.services.IRegistryHelper;
import com.ombremoon.playingcards.recipes.CardDeckRecipe;
import com.ombremoon.playingcards.tileentity.TileEntityPokerTable;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public class NeoForgeRegistryHelper implements IRegistryHelper {

    @Override public Supplier<Block> getPokerTable() { return InitItems.POKER_TABLE; }
    @Override public Supplier<Block> getBarStool() { return InitItems.BAR_STOOL; }
    @Override public Supplier<Item> getPokerTableItem() { return InitItems.POKER_TABLE_ITEM; }
    @Override public Supplier<Item> getBarStoolItem() { return InitItems.BAR_STOOL_ITEM; }
    @Override public Supplier<Item> getCardDeck() { return InitItems.CARD_DECK; }
    @Override public Supplier<Item> getCardCovered() { return InitItems.CARD_COVERED; }
    @Override public Supplier<Item> getCard() { return InitItems.CARD; }
    @Override public Supplier<Item> getPokerChipWhite() { return InitItems.POKER_CHIP_WHITE; }
    @Override public Supplier<Item> getPokerChipRed() { return InitItems.POKER_CHIP_RED; }
    @Override public Supplier<Item> getPokerChipBlue() { return InitItems.POKER_CHIP_BLUE; }
    @Override public Supplier<Item> getPokerChipGreen() { return InitItems.POKER_CHIP_GREEN; }
    @Override public Supplier<Item> getPokerChipBlack() { return InitItems.POKER_CHIP_BLACK; }
    @Override public Supplier<EntityType<EntityCard>> getCardEntityType() { return InitEntityTypes.CARD; }
    @Override public Supplier<EntityType<EntityCardDeck>> getCardDeckEntityType() { return InitEntityTypes.CARD_DECK; }
    @Override public Supplier<EntityType<EntityPokerChip>> getPokerChipEntityType() { return InitEntityTypes.POKER_CHIP; }
    @Override public Supplier<EntityType<EntityDice>> getDiceEntityType() { return InitEntityTypes.DICE; }
    @Override public Supplier<EntityType<EntitySeat>> getSeatEntityType() { return InitEntityTypes.SEAT; }
    @Override public Supplier<BlockEntityType<TileEntityPokerTable>> getPokerTableBlockEntityType() { return InitTileEntityTypes.POKER_TABLE; }
    @Override public Supplier<RecipeSerializer<CardDeckRecipe>> getDeckRecipeSerializer() { return InitRecipes.DECK; }
}
