package com.ombremoon.playingcards.init;

import com.ombremoon.playingcards.block.BlockBarStool;
import com.ombremoon.playingcards.block.BlockPokerTable;
import com.ombremoon.playingcards.block.base.BlockItemBase;
import com.ombremoon.playingcards.item.ItemCard;
import com.ombremoon.playingcards.item.ItemCardCovered;
import com.ombremoon.playingcards.item.ItemCardDeck;
import com.ombremoon.playingcards.item.ItemPokerChip;
import com.ombremoon.playingcards.main.PCReference;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class InitItems {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.createBlocks(PCReference.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.createItems(PCReference.MOD_ID);
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, PCReference.MOD_ID);

    //----- BLOCKS ------\\

    public static final Supplier<Block> POKER_TABLE = BLOCKS.register("poker_table", () -> new BlockPokerTable(BlockBehaviour.Properties.of().sound(SoundType.WOOD).strength(1).noCollission()));
    public static final Supplier<Item> POKER_TABLE_ITEM = ITEMS.register("poker_table", () -> new BlockItemBase(POKER_TABLE.get()));

    public static final Supplier<Block> BAR_STOOL = BLOCKS.register("bar_stool", BlockBarStool::new);
    public static final Supplier<Item> BAR_STOOL_ITEM = ITEMS.register("bar_stool", () -> new BlockItemBase(BAR_STOOL.get()));

    //public static final Supplier<Block> CASINO_CARPET_SPACE = BLOCKS.register("casino_carpet_space", BlockCasinoCarpet::new);
    //public static final Supplier<Item> CASINO_CARPET_SPACE_ITEM = ITEMS.register("casino_carpet_space", () -> new BlockItemBase(CASINO_CARPET_SPACE.get()));

    //----- ITEMS ------\\

    public static final Supplier<Item> CARD_DECK = ITEMS.register("card_deck", ItemCardDeck::new);
    public static final Supplier<Item> CARD_COVERED = ITEMS.register("card_covered", ItemCardCovered::new);
    public static final Supplier<Item> CARD = ITEMS.register("card", ItemCard::new);

    public static final Supplier<Item> POKER_CHIP_WHITE = ITEMS.register("poker_chip_white", () -> new ItemPokerChip((byte)0, 1));
    public static final Supplier<Item> POKER_CHIP_RED = ITEMS.register("poker_chip_red", () -> new ItemPokerChip((byte)1,5));
    public static final Supplier<Item> POKER_CHIP_BLUE = ITEMS.register("poker_chip_blue", () -> new ItemPokerChip((byte)2,10));
    public static final Supplier<Item> POKER_CHIP_GREEN = ITEMS.register("poker_chip_green", () -> new ItemPokerChip((byte)3,25));
    public static final Supplier<Item> POKER_CHIP_BLACK = ITEMS.register("poker_chip_black", () -> new ItemPokerChip((byte)4,100));

    public static final Supplier<CreativeModeTab> TAB = TABS.register(PCReference.MOD_ID, () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
            .icon(() -> new ItemStack(CARD.get()))
            .displayItems(
                    (itemDisplayParameters, output) -> {
                        ITEMS.getEntries().stream().filter(object -> !(object.get() instanceof ItemCardCovered)).forEach((Supplier) -> {
                            if (Supplier.get() instanceof ItemCardDeck deck) {
                                deck.fillItemGroup(output);
                            } else {
                                output.accept(new ItemStack(Supplier.get()));
                            }
                        });
                    }).title(Component.translatable("itemGroup." + PCReference.MOD_ID + ".tab"))
            .build());

    //public static final Supplier<Item> DICE_WHITE = ITEMS.register("dice_white", ItemDice::new);

    public static void init (IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        TABS.register(modEventBus);
    }
}
