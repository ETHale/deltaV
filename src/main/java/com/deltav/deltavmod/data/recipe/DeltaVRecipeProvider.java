package com.deltav.deltavmod.data.recipe;

import java.util.concurrent.CompletableFuture;

import com.deltav.deltavmod.DeltaV;
import com.deltav.deltavmod.block.ModBlocks;
import com.deltav.deltavmod.item.ModItems;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class DeltaVRecipeProvider extends RecipeProvider{

    protected DeltaVRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
        super(provider, output);
    }

    @Override
    protected void buildRecipes() {
        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.STEEL_BLOCK)
            .pattern("SSS")
            .pattern("SSS")
            .pattern("SSS")
            .define('S', ModItems.STEEL_INGOT)
            .unlockedBy("has_steel_ingot", this.has(ModItems.STEEL_INGOT))
            .save(this.output);

        ShapelessRecipeBuilder.shapeless(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC, ModItems.STEEL_INGOT, 9)
            .requires(ModBlocks.STEEL_BLOCK)
            .unlockedBy("has_steel_block", this.has(ModBlocks.STEEL_BLOCK))
            .save(this.output, "steel_ingot_from_steel_block");

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.ZINC_BLOCK)
            .pattern("ZZZ")
            .pattern("ZZZ")
            .pattern("ZZZ")
            .define('Z', ModItems.ZINC_INGOT)
            .unlockedBy("has_zinc_ingot", this.has(ModItems.ZINC_INGOT))
            .save(this.output);

        ShapelessRecipeBuilder.shapeless(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC, ModItems.ZINC_INGOT, 9)
            .requires(ModBlocks.ZINC_BLOCK)
            .unlockedBy("has_zinc_block", this.has(ModBlocks.ZINC_BLOCK))
            .save(this.output, "zinc_ingot_from_zinc_block");
        
        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.RAW_ZINC_BLOCK)
            .pattern("ZZZ")
            .pattern("ZZZ")
            .pattern("ZZZ")
            .define('Z', ModItems.RAW_ZINC)
            .unlockedBy("has_raw_zinc", this.has(ModItems.RAW_ZINC))
            .save(this.output);

        ShapelessRecipeBuilder.shapeless(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC, ModItems.RAW_ZINC, 9)
            .requires(ModBlocks.RAW_ZINC_BLOCK)
            .unlockedBy("has_raw_zinc_block", this.has(ModBlocks.RAW_ZINC_BLOCK))
            .save(this.output);

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModBlocks.ZINC_ORE), RecipeCategory.MISC, ModItems.ZINC_INGOT, 0.7F, 200)
            .unlockedBy("has_zinc_ore", this.has(ModBlocks.ZINC_ORE))
            .save(this.output, "zinc_ingot_from_smelting_zinc_ore");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModBlocks.DEEPSLATE_ZINC_ORE), RecipeCategory.MISC, ModItems.ZINC_INGOT, 0.7F, 200)
            .unlockedBy("has_deepslate_zinc_ore", this.has(ModBlocks.DEEPSLATE_ZINC_ORE))
            .save(this.output, "zinc_ingot_from_smelting_deepslate_zinc_ore");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.RAW_ZINC), RecipeCategory.MISC, ModItems.ZINC_INGOT, 0.7F, 200)
            .unlockedBy("has_raw_zinc", this.has(ModItems.RAW_ZINC))
            .save(this.output, "zinc_ingot_from_smelting_raw_zinc");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModBlocks.ZINC_ORE), RecipeCategory.MISC, ModItems.ZINC_INGOT, 0.7F, 100)
            .unlockedBy("has_zinc_ore", this.has(ModBlocks.ZINC_ORE))
            .save(this.output, "zinc_ingot_from_blasting_zinc_ore");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModBlocks.DEEPSLATE_ZINC_ORE), RecipeCategory.MISC, ModItems.ZINC_INGOT, 0.7F, 100)
            .unlockedBy("has_deepslate_zinc_ore", this.has(ModBlocks.DEEPSLATE_ZINC_ORE))
            .save(this.output, "zinc_ingot_from_blasting_deepslate_zinc_ore");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.RAW_ZINC), RecipeCategory.MISC, ModItems.ZINC_INGOT, 0.7F, 100)
            .unlockedBy("has_raw_zinc", this.has(ModItems.RAW_ZINC))
            .save(this.output, "zinc_ingot_from_blasting_raw_zinc");

        // Kimberlite reciepes 
        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.KIMBERLITE_SLAB, 6)
            .pattern("ZZZ")
            .define('Z', ModItems.KIMBERLITE_ITEM)
            .unlockedBy("has_kimberlite", this.has(ModItems.KIMBERLITE_ITEM))
            .save(this.output, "kimberlite_slab_from_kimberlite");
        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.KIMBERLITE_STAIRS, 4)
            .pattern("Z  ")
            .pattern("ZZ ")
            .pattern("ZZZ")
            .define('Z', ModItems.KIMBERLITE_ITEM)
            .unlockedBy("has_kimberlite", this.has(ModItems.KIMBERLITE_ITEM))
            .save(this.output, "kimberlite_stairs_from_kimberlite");
        ShapelessRecipeBuilder.shapeless(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.REDSTONE, ModBlocks.KIMBERLITE_BUTTON)
            .requires(ModBlocks.KIMBERLITE)
            .unlockedBy("has_kimberlite", this.has(ModItems.KIMBERLITE_ITEM))
            .save(this.output, "kimberlite_button_from_kimberlite");
        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.KIMBERLITE_PRESSURE_PLATE)
            .pattern("ZZ")
            .define('Z', ModItems.KIMBERLITE_ITEM)
            .unlockedBy("has_kimberlite", this.has(ModItems.KIMBERLITE_ITEM))
            .save(this.output, "kimberlite_pressure_plate_from_kimberlite");
        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.KIMBERLITE_WALL, 6)
            .pattern("ZZZ")
            .pattern("ZZZ")
            .define('Z', ModItems.KIMBERLITE_ITEM)
            .unlockedBy("has_kimberlite", this.has(ModItems.KIMBERLITE_ITEM))
            .save(this.output, "kimberlite_wall_from_kimberlite");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModItems.KIMBERLITE_ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.KIMBERLITE_SLAB, 2)
            .unlockedBy("has_kimberlite", this.has(ModItems.KIMBERLITE_ITEM))
            .save(output, "kimberlite_slab_from_kimberlite_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModItems.KIMBERLITE_ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.KIMBERLITE_STAIRS, 1)
            .unlockedBy("has_kimberlite", this.has(ModItems.KIMBERLITE_ITEM))
            .save(output, "kimberlite_stairs_from_kimberlite_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModItems.KIMBERLITE_ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.KIMBERLITE_WALL, 1)
            .unlockedBy("has_kimberlite", this.has(ModItems.KIMBERLITE_ITEM))
            .save(output, "kimberlite_wall_from_kimberlite_stonecutting");
    }

    @EventBusSubscriber(modid = DeltaV.MODID, value = Dist.CLIENT)
    public static class Runner extends RecipeProvider.Runner {

        protected Runner(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
            super(output, lookupProvider);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
            return new DeltaVRecipeProvider(provider, output);
        }

        @Override
        public String getName() {
            return "deltaVRecipeProvider";
        }

        @SubscribeEvent
        public static void gatherData(GatherDataEvent.Client event) {
            event.createProvider(DeltaVRecipeProvider.Runner::new);
        }
        
    }
    
}
