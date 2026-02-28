package com.ombremoon.playingcards.init;

import com.ombremoon.playingcards.entity.*;
import com.ombremoon.playingcards.main.CommonClass;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class FabricEntityTypes {

    public static EntityType<EntityCard> CARD;
    public static EntityType<EntityCardDeck> CARD_DECK;
    public static EntityType<EntityPokerChip> POKER_CHIP;
    public static EntityType<EntityDice> DICE;
    public static EntityType<EntitySeat> SEAT;

    public static void init() {
        CARD = Registry.register(BuiltInRegistries.ENTITY_TYPE, CommonClass.customLocation("card"),
                EntityType.Builder.<EntityCard>of(EntityCard::new, MobCategory.MISC).sized(0.5F, 0.5F).build(CommonClass.customLocation("card").toString()));
        CARD_DECK = Registry.register(BuiltInRegistries.ENTITY_TYPE, CommonClass.customLocation("card_deck"),
                EntityType.Builder.<EntityCardDeck>of(EntityCardDeck::new, MobCategory.MISC).sized(0.5F, 0.5F).build(CommonClass.customLocation("card_deck").toString()));
        POKER_CHIP = Registry.register(BuiltInRegistries.ENTITY_TYPE, CommonClass.customLocation("poker_chip"),
                EntityType.Builder.<EntityPokerChip>of(EntityPokerChip::new, MobCategory.MISC).sized(0.3F, 0.3F).build(CommonClass.customLocation("poker_chip").toString()));
        DICE = Registry.register(BuiltInRegistries.ENTITY_TYPE, CommonClass.customLocation("dice"),
                EntityType.Builder.<EntityDice>of(EntityDice::new, MobCategory.MISC).sized(0.3F, 0.3F).build(CommonClass.customLocation("dice").toString()));
        SEAT = Registry.register(BuiltInRegistries.ENTITY_TYPE, CommonClass.customLocation("seat"),
                EntityType.Builder.<EntitySeat>of(EntitySeat::new, MobCategory.MISC).sized(0, 0).build(CommonClass.customLocation("seat").toString()));
    }
}
