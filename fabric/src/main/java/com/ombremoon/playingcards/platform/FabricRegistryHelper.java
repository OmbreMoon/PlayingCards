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

public class FabricRegistryHelper implements IRegistryHelper {

    @Override public Supplier<Block> getPokerTable() { return () -> FabricItems.POKER_TABLE; }
    @Override public Supplier<Block> getBarStool() { return () -> FabricItems.BAR_STOOL; }
    @Override public Supplier<Item> getPokerTableItem() { return () -> FabricItems.POKER_TABLE_ITEM; }
    @Override public Supplier<Item> getBarStoolItem() { return () -> FabricItems.BAR_STOOL_ITEM; }
    @Override public Supplier<Item> getCardDeck() { return () -> FabricItems.CARD_DECK; }
    @Override public Supplier<Item> getCardCovered() { return () -> FabricItems.CARD_COVERED; }
    @Override public Supplier<Item> getCard() { return () -> FabricItems.CARD; }
    @Override public Supplier<Item> getPokerChipWhite() { return () -> FabricItems.POKER_CHIP_WHITE; }
    @Override public Supplier<Item> getPokerChipRed() { return () -> FabricItems.POKER_CHIP_RED; }
    @Override public Supplier<Item> getPokerChipBlue() { return () -> FabricItems.POKER_CHIP_BLUE; }
    @Override public Supplier<Item> getPokerChipGreen() { return () -> FabricItems.POKER_CHIP_GREEN; }
    @Override public Supplier<Item> getPokerChipBlack() { return () -> FabricItems.POKER_CHIP_BLACK; }
    @Override public Supplier<EntityType<EntityCard>> getCardEntityType() { return () -> FabricEntityTypes.CARD; }
    @Override public Supplier<EntityType<EntityCardDeck>> getCardDeckEntityType() { return () -> FabricEntityTypes.CARD_DECK; }
    @Override public Supplier<EntityType<EntityPokerChip>> getPokerChipEntityType() { return () -> FabricEntityTypes.POKER_CHIP; }
    @Override public Supplier<EntityType<EntityDice>> getDiceEntityType() { return () -> FabricEntityTypes.DICE; }
    @Override public Supplier<EntityType<EntitySeat>> getSeatEntityType() { return () -> FabricEntityTypes.SEAT; }
    @Override public Supplier<BlockEntityType<TileEntityPokerTable>> getPokerTableBlockEntityType() { return () -> FabricTileEntityTypes.POKER_TABLE; }
    @Override public Supplier<RecipeSerializer<CardDeckRecipe>> getDeckRecipeSerializer() { return () -> FabricRecipes.DECK; }
}
