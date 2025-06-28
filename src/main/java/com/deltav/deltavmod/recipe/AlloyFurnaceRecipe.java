package com.deltav.deltavmod.recipe;

import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

import com.mojang.datafixers.TypeRewriteRule.All;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeBookCategories;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public record AlloyFurnaceRecipe(Ingredient inputItem, ItemStack output) implements Recipe<AlloyFurnaceRecipeInput> {
    // inputItem & output => read from JSON file
    // AlloyFurnaceRecipeInput => inventory of the block entity

    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(inputItem);
        return list;
    }

    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return output;
    }

    @Override
    public ItemStack assemble(AlloyFurnaceRecipeInput alloyFurnaceRecipeInput, HolderLookup.Provider provider) {
        return output.copy();
    }

    @Override
    public RecipeSerializer<? extends Recipe<AlloyFurnaceRecipeInput>> getSerializer() {
        return ModRecipes.ALLOY_FURNACE_SERIALIZER.get();
    }

    @Override
    public RecipeType<? extends Recipe<AlloyFurnaceRecipeInput>> getType() {
        return ModRecipes.ALLOY_FURNACE_TYPE.get();
    }

    @Override
    public boolean matches(AlloyFurnaceRecipeInput alloyFurnaceRecipeInput, Level level) {
        if (level.isClientSide()) {
            return false;
        }
        return inputItem.test(alloyFurnaceRecipeInput.getItem(0));
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return RecipeBookCategories.FURNACE_MISC;
    }

    public static class Serializer implements RecipeSerializer<AlloyFurnaceRecipe> {
        public static final MapCodec<AlloyFurnaceRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Ingredient.CODEC.fieldOf("ingredient").forGetter(AlloyFurnaceRecipe::inputItem),
            ItemStack.CODEC.fieldOf("result").forGetter(AlloyFurnaceRecipe::output)
        ).apply(inst, AlloyFurnaceRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, AlloyFurnaceRecipe> STREAM_CODEC = 
        StreamCodec.composite(
            Ingredient.CONTENTS_STREAM_CODEC, AlloyFurnaceRecipe::inputItem, 
            ItemStack.STREAM_CODEC, AlloyFurnaceRecipe::output,
            AlloyFurnaceRecipe::new);

        @Override
        public MapCodec<AlloyFurnaceRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, AlloyFurnaceRecipe> streamCodec() {
            return STREAM_CODEC;
        }
        
    }

}
