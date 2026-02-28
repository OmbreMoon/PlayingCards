package com.ombremoon.playingcards.init;

import com.ombremoon.playingcards.main.CommonClass;
import com.ombremoon.playingcards.recipes.CardDeckRecipe;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;

public class FabricRecipes {

    public static RecipeSerializer<CardDeckRecipe> DECK;

    public static void init() {
        DECK = Registry.register(BuiltInRegistries.RECIPE_SERIALIZER, CommonClass.customLocation("deck"),
                new SimpleCraftingRecipeSerializer<>(CardDeckRecipe::new));
    }
}
