package com.ombremoon.playingcards.init;

import com.ombremoon.playingcards.main.PCReference;
import com.ombremoon.playingcards.recipes.CardDeckRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class InitRecipes {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPES = DeferredRegister.create(Registries.RECIPE_SERIALIZER, PCReference.MOD_ID);

    public static final Supplier<RecipeSerializer<CardDeckRecipe>> DECK = RECIPES.register("deck", () -> new SimpleCraftingRecipeSerializer<>(CardDeckRecipe::new));

    public static void init(IEventBus modEventBus) {
        RECIPES.register(modEventBus);
    }
}
