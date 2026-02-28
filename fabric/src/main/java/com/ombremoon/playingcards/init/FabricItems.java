package com.ombremoon.playingcards.init;

import com.ombremoon.playingcards.block.BlockBarStool;
import com.ombremoon.playingcards.block.BlockPokerTable;
import com.ombremoon.playingcards.block.base.BlockItemBase;
import com.ombremoon.playingcards.item.*;
import com.ombremoon.playingcards.main.CommonClass;
import com.ombremoon.playingcards.main.PCReference;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class FabricItems {

    public static Block POKER_TABLE;
    public static Item POKER_TABLE_ITEM;
    public static Block BAR_STOOL;
    public static Item BAR_STOOL_ITEM;
    public static Item CARD_DECK;
    public static Item CARD_COVERED;
    public static Item CARD;
    public static Item POKER_CHIP_WHITE;
    public static Item POKER_CHIP_RED;
    public static Item POKER_CHIP_BLUE;
    public static Item POKER_CHIP_GREEN;
    public static Item POKER_CHIP_BLACK;

    public static void init() {
        POKER_TABLE = Registry.register(BuiltInRegistries.BLOCK, CommonClass.customLocation("poker_table"),
                new BlockPokerTable(BlockBehaviour.Properties.of().sound(SoundType.WOOD).strength(1).noCollission()));
        BAR_STOOL = Registry.register(BuiltInRegistries.BLOCK, CommonClass.customLocation("bar_stool"),
                new BlockBarStool());

        POKER_TABLE_ITEM = Registry.register(BuiltInRegistries.ITEM, CommonClass.customLocation("poker_table"),
                new BlockItemBase(POKER_TABLE));
        BAR_STOOL_ITEM = Registry.register(BuiltInRegistries.ITEM, CommonClass.customLocation("bar_stool"),
                new BlockItemBase(BAR_STOOL));

        CARD_DECK = Registry.register(BuiltInRegistries.ITEM, CommonClass.customLocation("card_deck"), new ItemCardDeck());
        CARD_COVERED = Registry.register(BuiltInRegistries.ITEM, CommonClass.customLocation("card_covered"), new ItemCardCovered());
        CARD = Registry.register(BuiltInRegistries.ITEM, CommonClass.customLocation("card"), new ItemCard());

        POKER_CHIP_WHITE = Registry.register(BuiltInRegistries.ITEM, CommonClass.customLocation("poker_chip_white"), new ItemPokerChip((byte) 0, 1));
        POKER_CHIP_RED = Registry.register(BuiltInRegistries.ITEM, CommonClass.customLocation("poker_chip_red"), new ItemPokerChip((byte) 1, 5));
        POKER_CHIP_BLUE = Registry.register(BuiltInRegistries.ITEM, CommonClass.customLocation("poker_chip_blue"), new ItemPokerChip((byte) 2, 10));
        POKER_CHIP_GREEN = Registry.register(BuiltInRegistries.ITEM, CommonClass.customLocation("poker_chip_green"), new ItemPokerChip((byte) 3, 25));
        POKER_CHIP_BLACK = Registry.register(BuiltInRegistries.ITEM, CommonClass.customLocation("poker_chip_black"), new ItemPokerChip((byte) 4, 100));

        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, CommonClass.customLocation(PCReference.MOD_ID),
                FabricItemGroup.builder()
                        .icon(() -> new ItemStack(CARD))
                        .title(Component.translatable("itemGroup." + PCReference.MOD_ID + ".tab"))
                        .displayItems((itemDisplayParameters, output) -> {
                            output.accept(new ItemStack(POKER_TABLE_ITEM));
                            output.accept(new ItemStack(BAR_STOOL_ITEM));
                            ItemCardDeck deck = (ItemCardDeck) CARD_DECK;
                            deck.fillItemGroup(output);
                            output.accept(new ItemStack(CARD));
                            output.accept(new ItemStack(POKER_CHIP_WHITE));
                            output.accept(new ItemStack(POKER_CHIP_RED));
                            output.accept(new ItemStack(POKER_CHIP_BLUE));
                            output.accept(new ItemStack(POKER_CHIP_GREEN));
                            output.accept(new ItemStack(POKER_CHIP_BLACK));
                        })
                        .build());
    }
}
