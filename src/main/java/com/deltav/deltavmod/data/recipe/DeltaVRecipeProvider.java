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
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
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

        // ZINC
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

        // COBALT
        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.COBALT_BLOCK)
            .pattern("CCC")
            .pattern("CCC")
            .pattern("CCC")
            .define('C', ModItems.COBALT_INGOT)
            .unlockedBy("has_cobalt_ingot", this.has(ModItems.COBALT_INGOT))
            .save(this.output);

        ShapelessRecipeBuilder.shapeless(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC, ModItems.COBALT_INGOT, 9)
            .requires(ModBlocks.COBALT_BLOCK)
            .unlockedBy("has_cobalt_block", this.has(ModBlocks.COBALT_BLOCK))
            .save(this.output, "cobalt_ingot_from_cobalt_block");
        
        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.RAW_COBALT_BLOCK)
            .pattern("CCC")
            .pattern("CCC")
            .pattern("CCC")
            .define('C', ModItems.RAW_COBALT)
            .unlockedBy("has_raw_cobalt", this.has(ModItems.RAW_COBALT))
            .save(this.output);

        ShapelessRecipeBuilder.shapeless(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC, ModItems.RAW_COBALT, 9)
            .requires(ModBlocks.RAW_COBALT_BLOCK)
            .unlockedBy("has_raw_cobalt_block", this.has(ModBlocks.RAW_COBALT_BLOCK))
            .save(this.output);

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModBlocks.COBALT_ORE), RecipeCategory.MISC, ModItems.COBALT_INGOT, 0.7F, 200)
            .unlockedBy("has_cobalt_ore", this.has(ModBlocks.COBALT_ORE))
            .save(this.output, "cobalt_ingot_from_smelting_cobalt_ore");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModBlocks.DEEPSLATE_COBALT_ORE), RecipeCategory.MISC, ModItems.COBALT_INGOT, 0.7F, 200)
            .unlockedBy("has_deepslate_cobalt_ore", this.has(ModBlocks.DEEPSLATE_COBALT_ORE))
            .save(this.output, "cobalt_ingot_from_smelting_deepslate_cobalt_ore");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.RAW_COBALT), RecipeCategory.MISC, ModItems.COBALT_INGOT, 0.7F, 200)
            .unlockedBy("has_raw_cobalt", this.has(ModItems.RAW_COBALT))
            .save(this.output, "cobalt_ingot_from_smelting_raw_cobalt");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModBlocks.COBALT_ORE), RecipeCategory.MISC, ModItems.COBALT_INGOT, 0.7F, 100)
            .unlockedBy("has_cobalt_ore", this.has(ModBlocks.COBALT_ORE))
            .save(this.output, "cobalt_ingot_from_blasting_cobalt_ore");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModBlocks.DEEPSLATE_COBALT_ORE), RecipeCategory.MISC, ModItems.COBALT_INGOT, 0.7F, 100)
            .unlockedBy("has_deepslate_cobalt_ore", this.has(ModBlocks.DEEPSLATE_COBALT_ORE))
            .save(this.output, "cobalt_ingot_from_blasting_deepslate_cobalt_ore");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModItems.RAW_COBALT), RecipeCategory.MISC, ModItems.COBALT_INGOT, 0.7F, 100)
            .unlockedBy("has_raw_cobalt", this.has(ModItems.RAW_COBALT))
            .save(this.output, "cobalt_ingot_from_blasting_raw_cobalt");

        // Kimberlite recipes 
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
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModItems.KIMBERLITE_ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_KIMBERLITE_SLAB, 2)
            .unlockedBy("has_kimberlite", this.has(ModItems.KIMBERLITE_ITEM))
            .save(output, "polished_kimberlite_slab_from_kimberlite_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModItems.KIMBERLITE_ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_KIMBERLITE_STAIRS, 1)
            .unlockedBy("has_kimberlite", this.has(ModItems.KIMBERLITE_ITEM))
            .save(output, "polished_kimberlite_stairs_from_kimberlite_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModItems.KIMBERLITE_ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_KIMBERLITE, 1)
            .unlockedBy("has_kimberlite", this.has(ModItems.KIMBERLITE_ITEM))
            .save(output, "polished_kimberlite_from_kimberlite_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModItems.POLISHED_KIMBERLITE_ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_KIMBERLITE_SLAB, 2)
            .unlockedBy("has_kimberlite", this.has(ModItems.KIMBERLITE_ITEM))
            .save(output, "polished_kimberlite_slab_from_polished_kimberlite_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModItems.POLISHED_KIMBERLITE_ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_KIMBERLITE_STAIRS, 1)
            .unlockedBy("has_kimberlite", this.has(ModItems.KIMBERLITE_ITEM))
            .save(output, "polished_kimberlite_stairs_from_polished_kimberlite_stonecutting");
        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_KIMBERLITE_SLAB, 6)
            .pattern("ZZZ")
            .define('Z', ModItems.POLISHED_KIMBERLITE_ITEM)
            .unlockedBy("has_kimberlite", this.has(ModItems.KIMBERLITE_ITEM))
            .save(this.output, "polished_kimberlite_slab_from_polished_kimberlite");
        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_KIMBERLITE_STAIRS, 4)
            .pattern("Z  ")
            .pattern("ZZ ")
            .pattern("ZZZ")
            .define('Z', ModItems.POLISHED_KIMBERLITE_ITEM)
            .unlockedBy("has_kimberlite", this.has(ModItems.KIMBERLITE_ITEM))
            .save(this.output, "polished_kimberlite_stairs_from_polished_kimberlite");
        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.POLISHED_KIMBERLITE, 4)
            .pattern("ZZ")
            .pattern("ZZ")
            .define('Z', ModItems.KIMBERLITE_ITEM)
            .unlockedBy("has_kimberlite", this.has(ModItems.KIMBERLITE_ITEM))
            .save(this.output, "polished_kimberlite_stairs_from_kimberlite");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModBlocks.KIMBERLITE_COAL_ORE), RecipeCategory.MISC, Items.COAL, 0.1F, 200)
            .unlockedBy("has_kimberlite_coal_ore", this.has(ModBlocks.KIMBERLITE_COAL_ORE))
            .save(this.output, "coal_from_smelting_kimberlite_coal_ore");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModBlocks.KIMBERLITE_COAL_ORE), RecipeCategory.MISC, Items.COAL, 0.1F, 100)
            .unlockedBy("has_kimberlite_coal_ore", this.has(ModBlocks.KIMBERLITE_COAL_ORE))
            .save(this.output, "coal_from_blasting_kimberlite_coal_ore");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModBlocks.KIMBERLITE_COPPER_ORE), RecipeCategory.MISC, Items.COPPER_INGOT, 0.7F, 200)
            .unlockedBy("has_kimberlite_copper_ore", this.has(ModBlocks.KIMBERLITE_COPPER_ORE))
            .save(this.output, "copper_from_smelting_kimberlite_copper_ore");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModBlocks.KIMBERLITE_COPPER_ORE), RecipeCategory.MISC, Items.COPPER_INGOT, 0.7F, 100)
            .unlockedBy("has_kimberlite_copper_ore", this.has(ModBlocks.KIMBERLITE_COPPER_ORE))
            .save(this.output, "copper_from_blasting_kimberlite_copper_ore");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModBlocks.KIMBERLITE_DIAMOND_ORE), RecipeCategory.MISC, Items.DIAMOND, 1.0F, 200)
            .unlockedBy("has_kimberlite_diamond_ore", this.has(ModBlocks.KIMBERLITE_DIAMOND_ORE))
            .save(this.output, "diamond_from_smelting_kimberlite_diamond_ore");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModBlocks.KIMBERLITE_DIAMOND_ORE), RecipeCategory.MISC, Items.DIAMOND, 1.0F, 100)
            .unlockedBy("has_kimberlite_diamond_ore", this.has(ModBlocks.KIMBERLITE_DIAMOND_ORE))
            .save(this.output, "diamond_from_blasting_kimberlite_diamond_ore");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModBlocks.KIMBERLITE_EMERALD_ORE), RecipeCategory.MISC, Items.EMERALD, 1.0F, 200)
            .unlockedBy("has_kimberlite_emerald_ore", this.has(ModBlocks.KIMBERLITE_EMERALD_ORE))
            .save(this.output, "emerald_from_smelting_kimberlite_emerald_ore");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModBlocks.KIMBERLITE_EMERALD_ORE), RecipeCategory.MISC, Items.EMERALD, 1.0F, 100)
            .unlockedBy("has_kimberlite_emerald_ore", this.has(ModBlocks.KIMBERLITE_EMERALD_ORE))
            .save(this.output, "emerald_from_blasting_kimberlite_emerald_ore");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModBlocks.KIMBERLITE_GOLD_ORE), RecipeCategory.MISC, Items.GOLD_INGOT, 1.0F, 200)
            .unlockedBy("has_kimberlite_gold_ore", this.has(ModBlocks.KIMBERLITE_GOLD_ORE))
            .save(this.output, "gold_from_smelting_kimberlite_gold_ore");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModBlocks.KIMBERLITE_GOLD_ORE), RecipeCategory.MISC, Items.GOLD_INGOT, 1.0F, 100)
            .unlockedBy("has_kimberlite_gold_ore", this.has(ModBlocks.KIMBERLITE_GOLD_ORE))
            .save(this.output, "gold_from_blasting_kimberlite_gold_ore");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModBlocks.KIMBERLITE_IRON_ORE), RecipeCategory.MISC, Items.IRON_INGOT, 0.7F, 200)
            .unlockedBy("has_kimberlite_iron_ore", this.has(ModBlocks.KIMBERLITE_IRON_ORE))
            .save(this.output, "iron_from_smelting_kimberlite_iron_ore");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModBlocks.KIMBERLITE_IRON_ORE), RecipeCategory.MISC, Items.IRON_INGOT, 0.7F, 100)
            .unlockedBy("has_kimberlite_iron_ore", this.has(ModBlocks.KIMBERLITE_IRON_ORE))
            .save(this.output, "iron_from_blasting_kimberlite_iron_ore");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModBlocks.KIMBERLITE_LAPIS_ORE), RecipeCategory.MISC, Items.LAPIS_LAZULI, 0.2F, 200)
            .unlockedBy("has_kimberlite_lapis_ore", this.has(ModBlocks.KIMBERLITE_LAPIS_ORE))
            .save(this.output, "lapis_from_smelting_kimberlite_lapis_ore");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModBlocks.KIMBERLITE_LAPIS_ORE), RecipeCategory.MISC, Items.LAPIS_LAZULI, 0.2F, 100)
            .unlockedBy("has_kimberlite_lapis_ore", this.has(ModBlocks.KIMBERLITE_LAPIS_ORE))
            .save(this.output, "lapis_from_blasting_kimberlite_lapis_ore");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModBlocks.KIMBERLITE_REDSTONE_ORE), RecipeCategory.MISC, Items.REDSTONE, 0.3F, 200)
            .unlockedBy("has_kimberlite_redstone_ore", this.has(ModBlocks.KIMBERLITE_REDSTONE_ORE))
            .save(this.output, "redstone_from_smelting_kimberlite_redstone_ore");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModBlocks.KIMBERLITE_REDSTONE_ORE), RecipeCategory.MISC, Items.REDSTONE, 0.3F, 100)
            .unlockedBy("has_kimberlite_redstone_ore", this.has(ModBlocks.KIMBERLITE_REDSTONE_ORE))
            .save(this.output, "redstone_from_blasting_kimberlite_redstone_ore");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModBlocks.KIMBERLITE_ZINC_ORE), RecipeCategory.MISC, ModItems.ZINC_INGOT.get(), 0.7F, 200)
            .unlockedBy("has_kimberlite_zinc_ore", this.has(ModBlocks.KIMBERLITE_ZINC_ORE))
            .save(this.output, "zinc_from_smelting_kimberlite_zinc_ore");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModBlocks.KIMBERLITE_ZINC_ORE), RecipeCategory.MISC, ModItems.ZINC_INGOT.get(), 0.7F, 100)
            .unlockedBy("has_kimberlite_zinc_ore", this.has(ModBlocks.KIMBERLITE_ZINC_ORE))
            .save(this.output, "zinc_from_blasting_kimberlite_zinc_ore");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModBlocks.KIMBERLITE_COBALT_ORE), RecipeCategory.MISC, ModItems.COBALT_INGOT.get(), 0.7F, 200)
            .unlockedBy("has_kimberlite_cobalt_ore", this.has(ModBlocks.KIMBERLITE_COBALT_ORE))
            .save(this.output, "cobalt_from_smelting_kimberlite_cobalt_ore");
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(ModBlocks.KIMBERLITE_COBALT_ORE), RecipeCategory.MISC, ModItems.COBALT_INGOT.get(), 0.7F, 100)
            .unlockedBy("has_kimberlite_cobalt_ore", this.has(ModBlocks.KIMBERLITE_COBALT_ORE))
            .save(this.output, "cobalt_from_blasting_kimberlite_cobalt_ore");

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.REDSTONE, ModBlocks.REDSTONE_GENERATOR, 1)
            .pattern("IZI")
            .pattern("SRS")
            .pattern("III")
            .define('I', Items.IRON_INGOT)
            .define('R', Items.REDSTONE_BLOCK)
            .define('S', Items.STICK)
            .define('Z', ModBlocks.ZINC_BLOCK)
            .unlockedBy("has_zinc_ingot", this.has(ModItems.ZINC_INGOT))
            .save(this.output, "redstone_generator_from_crafting");

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.REDSTONE, ModItems.ZINC_BATTERY, 1)
            .pattern(" C ")
            .pattern("ZRZ")
            .pattern(" C ")
            .define('R', Items.REDSTONE)
            .define('C', Items.COPPER_INGOT)
            .define('Z', ModItems.ZINC_INGOT)
            .unlockedBy("has_zinc_ingot", this.has(ModItems.ZINC_INGOT))
            .save(this.output, "zinc_battery_from_crafting");

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.REDSTONE, ModBlocks.BASIC_BATTERY, 1)
            .pattern("BB")
            .pattern("BB")
            .define('B', ModItems.ZINC_BATTERY)
            .unlockedBy("has_basic_battery", this.has(ModItems.BASIC_BATTERY_ITEM))
            .save(this.output, "basic_battery_from_zinc_battery");
        
        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.SILICA_SANDSTONE.get(), 4)
            .pattern("SS")
            .pattern("SS")
            .define('S', ModBlocks.SILICA_SAND.get())
            .unlockedBy("has_silica_sand", this.has(ModBlocks.SILICA_SAND.get()))
            .save(this.output, "silica_sandstone_from_silica_sand");
        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.SILICA_SANDSTONE_STAIRS.get(), 4)
            .pattern("Z  ")
            .pattern("ZZ ")
            .pattern("ZZZ")
            .define('Z', ModBlocks.SILICA_SANDSTONE.get())
            .unlockedBy("has_silica_sandstone", this.has(ModBlocks.SILICA_SANDSTONE.get()))
            .save(this.output, "silica_sandstone_stairs_from_silica_sandstone");
        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.SILICA_SANDSTONE_SLAB.get(), 6)
            .pattern("ZZZ")
            .define('Z', ModBlocks.SILICA_SANDSTONE.get())
            .unlockedBy("has_silica_sandstone", this.has(ModBlocks.SILICA_SANDSTONE.get()))
            .save(this.output, "silica_sandstone_slab_from_silica_sandstone");
        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.SILICA_SANDSTONE_WALL.get(), 6)
            .pattern("ZZZ")
            .pattern("ZZZ")
            .define('Z', ModBlocks.SILICA_SANDSTONE.get())
            .unlockedBy("has_silica_sandstone", this.has(ModBlocks.SILICA_SANDSTONE.get()))
            .save(this.output, "silica_sandstone_wall_from_silica_sandstone");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.SILICA_SANDSTONE.get()), RecipeCategory.BUILDING_BLOCKS, ModBlocks.SILICA_SANDSTONE_SLAB.get(), 2)
            .unlockedBy("has_silica_sandstone", this.has(ModBlocks.SILICA_SANDSTONE.get()))
            .save(this.output, "silica_sandstone_slab_from_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.SILICA_SANDSTONE.get()), RecipeCategory.BUILDING_BLOCKS, ModBlocks.SILICA_SANDSTONE_STAIRS.get(), 1)
            .unlockedBy("has_silica_sandstone", this.has(ModBlocks.SILICA_SANDSTONE.get()))
            .save(this.output, "silica_sandstone_stairs_from_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.SILICA_SANDSTONE.get()), RecipeCategory.BUILDING_BLOCKS, ModBlocks.SILICA_SANDSTONE_WALL.get(), 1)
            .unlockedBy("has_silica_sandstone", this.has(ModBlocks.SILICA_SANDSTONE.get()))
            .save(this.output, "silica_sandstone_walls_from_stonecutting");
        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.CHISELED_SILICA_SANDSTONE.get(), 1)
            .pattern("S")
            .pattern("S")
            .define('S', ModBlocks.SILICA_SANDSTONE_SLAB.get())
            .unlockedBy("has_silica_sandstone_slab", this.has(ModBlocks.SILICA_SANDSTONE_SLAB.get()))
            .save(this.output, "chiseled_silica_sandstone_from_slab");
        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.SMOOTH_SILICA_SANDSTONE_STAIRS.get(), 4)
            .pattern("Z  ")
            .pattern("ZZ ")
            .pattern("ZZZ")
            .define('Z', ModBlocks.SMOOTH_SILICA_SANDSTONE.get())
            .unlockedBy("has_smooth_silica_sandstone", this.has(ModBlocks.SMOOTH_SILICA_SANDSTONE.get()))
            .save(this.output, "smooth_silica_sandstone_stairs_from_smooth_silica_sandstone");
        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.SMOOTH_SILICA_SANDSTONE_SLAB.get(), 6)
            .pattern("ZZZ")
            .define('Z', ModBlocks.SMOOTH_SILICA_SANDSTONE.get())
            .unlockedBy("has_smooth_silica_sandstone", this.has(ModBlocks.SMOOTH_SILICA_SANDSTONE.get()))
            .save(this.output, "smooth_silica_sandstone_slab_from_smooth_silica_sandstone");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.SMOOTH_SILICA_SANDSTONE.get()), RecipeCategory.BUILDING_BLOCKS, ModBlocks.SMOOTH_SILICA_SANDSTONE_SLAB.get(), 2)
            .unlockedBy("has_smooth_silica_sandstone", this.has(ModBlocks.SMOOTH_SILICA_SANDSTONE.get()))
            .save(this.output, "smooth_silica_sandstone_slab_from_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.SMOOTH_SILICA_SANDSTONE.get()), RecipeCategory.BUILDING_BLOCKS, ModBlocks.SMOOTH_SILICA_SANDSTONE_STAIRS.get(), 1)
            .unlockedBy("has_smooth_silica_sandstone", this.has(ModBlocks.SMOOTH_SILICA_SANDSTONE.get()))
            .save(this.output, "smooth_silica_sandstone_stairs_from_stonecutting");
        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.CUT_SILICA_SANDSTONE.get(), 1)
            .pattern("SS")
            .pattern("SS")
            .define('S', ModBlocks.SILICA_SANDSTONE.get())
            .unlockedBy("has_silica_sandstone", this.has(ModBlocks.SILICA_SANDSTONE.get()))
            .save(this.output, "cut_silica_sandstone_from_silica_sandstone");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.CUT_SILICA_SANDSTONE.get()), RecipeCategory.BUILDING_BLOCKS, ModBlocks.CUT_SILICA_SANDSTONE_SLAB.get(), 2)
            .unlockedBy("has_cut_silica_sandstone", this.has(ModBlocks.CUT_SILICA_SANDSTONE.get()))
            .save(this.output, "cut_silica_sandstone_slab_from_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.SILICA_SANDSTONE.get()), RecipeCategory.BUILDING_BLOCKS, ModBlocks.CUT_SILICA_SANDSTONE_SLAB.get(), 2)
            .unlockedBy("has_cut_silica_sandstone", this.has(ModBlocks.CUT_SILICA_SANDSTONE.get()))
            .save(this.output, "cut_silica_sandstone_slab_from_silica_sandstone_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.SILICA_SANDSTONE.get()), RecipeCategory.BUILDING_BLOCKS, ModBlocks.CUT_SILICA_SANDSTONE.get(), 1)
            .unlockedBy("has_cut_silica_sandstone", this.has(ModBlocks.CUT_SILICA_SANDSTONE.get()))
            .save(this.output, "cut_silica_sandstone_from_silica_sandstone_stonecutting");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModBlocks.SILICA_SANDSTONE), RecipeCategory.BUILDING_BLOCKS, ModBlocks.SMOOTH_SILICA_SANDSTONE.get(), 0.1F, 200)
            .unlockedBy("has_silica_sandstone", this.has(ModBlocks.SILICA_SANDSTONE.get()))
            .save(this.output, "smooth_silica_sandstone_from_smelting");
        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.SILICA_SAND.get(), 1)
            .pattern("SS")
            .pattern("SS")
            .define('S', ModItems.SILICA_DUST.get())
            .unlockedBy("has_silica_dust", this.has(ModItems.SILICA_DUST.get()))
            .save(this.output, "silica_sand_from_silica_dust");
        ShapelessRecipeBuilder.shapeless(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC, ModItems.SILICA_DUST.get(), 4)
            .requires(ModBlocks.SILICA_SAND.get())
            .unlockedBy("has_silica_sand", this.has(ModBlocks.SILICA_SAND.get()))
            .save(this.output, "silica_dust_from_silica_sand");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItems.SILICA_DUST), RecipeCategory.MISC, ModItems.SILICON.get(), 0.5F, 200)
            .unlockedBy("has_silica_dust", this.has(ModItems.SILICA_DUST.get()))
            .save(this.output, "silcon_from_smelting");

        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModItems.BARREL.get(), 1)
            .pattern("SSS")
            .pattern("S S")
            .pattern("SSS")
            .define('S', ModItems.STEEL_INGOT.get())
            .unlockedBy("has_steel_ingot", this.has(ModItems.STEEL_INGOT.get()))
            .save(this.output, "barrel_from_steel_ingots");
        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.REDSTONE, ModBlocks.COPPER_CABLE.get(), 6)
            .pattern("ZZZ")
            .define('Z', Items.COPPER_INGOT)
            .unlockedBy("has_copper_ingot", this.has(Items.COPPER_INGOT))
            .save(this.output, "copper_cable_from_copper_ingots");

        // Rubberwood recipes
        ShapelessRecipeBuilder.shapeless(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.RUBBERWOOD_PLANKS, 4)
            .requires(ModBlocks.RUBBERWOOD_LOG)
            .unlockedBy("has_rubberwood_log", this.has(ModBlocks.RUBBERWOOD_LOG))
            .save(this.output, "rubberwood_planks_from_log");
        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.RUBBERWOOD_SLAB, 6)
            .pattern("ZZZ")
            .define('Z', ModBlocks.RUBBERWOOD_PLANKS)
            .unlockedBy("has_rubberwood_planks", this.has(ModBlocks.RUBBERWOOD_PLANKS))
            .save(this.output, "rubberwood_slab_from_planks");
        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.RUBBERWOOD_STAIRS, 4)
            .pattern("Z  ")
            .pattern("ZZ ")
            .pattern("ZZZ")
            .define('Z', ModBlocks.RUBBERWOOD_PLANKS)
            .unlockedBy("has_rubberwood_planks", this.has(ModBlocks.RUBBERWOOD_PLANKS))
            .save(this.output, "rubberwood_stairs_from_planks");
        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.RUBBERWOOD_HANGING_SIGN, 6)
            .pattern("C C")
            .pattern("SSS")
            .pattern("SSS")
            .define('C', Items.CHAIN)
            .define('S', ModBlocks.STRIPPED_RUBBERWOOD_LOG)
            .unlockedBy("has_rubberwood_planks", this.has(ModBlocks.RUBBERWOOD_LOG))
            .save(this.output, "rubberwood_hanging_sign_from_rubberwood_logs");
        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.RUBBERWOOD_SIGN, 3)
            .pattern("SSS")
            .pattern("SSS")
            .pattern(" I ")
            .define('S', ModBlocks.RUBBERWOOD_PLANKS)
            .define('I', Items.STICK)
            .unlockedBy("has_rubberwood_planks", this.has(ModBlocks.RUBBERWOOD_PLANKS))
            .save(this.output, "rubberwood_sign_from_rubberwood_logs");
        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.RUBBERWOOD_WOOD, 3)
            .pattern("SS")
            .pattern("SS")
            .define('S', ModBlocks.RUBBERWOOD_LOG)
            .unlockedBy("has_rubberwood_planks", this.has(ModBlocks.RUBBERWOOD_LOG))
            .save(this.output, "rubberwood_wood_from_rubberwood_logs");
        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.STRIPPED_RUBBERWOOD_WOOD, 3)
            .pattern("SS")
            .pattern("SS")
            .define('S', ModBlocks.STRIPPED_RUBBERWOOD_LOG)
            .unlockedBy("has_rubberwood_planks", this.has(ModBlocks.RUBBERWOOD_LOG))
            .save(this.output, "stripped_rubberwood_wood_from_stripped_rubberwood_logs");
        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.RUBBERWOOD_PLANKS, 1)
            .pattern("S")
            .pattern("S")
            .define('S', ModBlocks.RUBBERWOOD_SLAB)
            .unlockedBy("has_rubberwood_planks", this.has(ModBlocks.RUBBERWOOD_SLAB))
            .save(this.output, "rubberwood_planks_from_rubberwood_slab");
        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModItems.RUBBERWOOD_BOAT_ITEM, 1)
            .pattern("S S")
            .pattern("SSS")
            .define('S', ModBlocks.RUBBERWOOD_PLANKS)
            .unlockedBy("has_rubberwood_planks", this.has(ModBlocks.RUBBERWOOD_LOG))
            .save(this.output, "rubberwood_boat_from_rubberwood_planks");
        ShapelessRecipeBuilder.shapeless(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.MISC, ModItems.RUBBERWOOD_CHEST_BOAT_ITEM, 1)
            .requires(ModItems.RUBBERWOOD_BOAT_ITEM)
            .requires(Items.CHEST)
            .unlockedBy("has_rubberwood_planks", this.has(ModBlocks.RUBBERWOOD_LOG))
            .save(this.output, "rubberwood_chest_boat_from_rubberwood_boat_and_chest");
        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.RUBBERWOOD_FENCE, 3)
            .pattern("SIS")
            .pattern("SIS")
            .define('S', ModBlocks.RUBBERWOOD_PLANKS)
            .define('I', Items.STICK)
            .unlockedBy("has_rubberwood_planks", this.has(ModBlocks.RUBBERWOOD_PLANKS))
            .save(this.output, "rubberwood_fence_from_rubberwood_planks");
        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.RUBBERWOOD_FENCE_GATE, 1)
            .pattern("ISI")
            .pattern("ISI")
            .define('S', ModBlocks.RUBBERWOOD_PLANKS)
            .define('I', Items.STICK)
            .unlockedBy("has_rubberwood_planks", this.has(ModBlocks.RUBBERWOOD_PLANKS))
            .save(this.output, "rubberwood_fence_gate_from_rubberwood_planks");
        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.DECORATIONS, ModBlocks.RUBBERWOOD_DOOR, 3)
            .pattern("SS")
            .pattern("SS")
            .pattern("SS")
            .define('S', ModBlocks.RUBBERWOOD_PLANKS)
            .unlockedBy("has_rubberwood_planks", this.has(ModBlocks.RUBBERWOOD_PLANKS))
            .save(this.output, "rubberwood_door_from_rubberwood_planks");
        ShapelessRecipeBuilder.shapeless(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.REDSTONE, ModBlocks.RUBBERWOOD_BUTTON, 1)
            .requires(ModBlocks.RUBBERWOOD_PLANKS)
            .unlockedBy("has_rubberwood_planks", this.has(ModBlocks.RUBBERWOOD_PLANKS))
            .save(this.output, "rubberwood_button_from_rubberwood_planks");
        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.REDSTONE, ModBlocks.RUBBERWOOD_PRESSURE_PLATE, 1)
            .pattern("SS")
            .define('S', ModBlocks.RUBBERWOOD_PLANKS)
            .unlockedBy("has_rubberwood_planks", this.has(ModBlocks.RUBBERWOOD_PLANKS))
            .save(this.output, "rubberwood_pressure_plate_from_rubberwood_planks");
        ShapedRecipeBuilder.shaped(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.BUILDING_BLOCKS, ModBlocks.RUBBERWOOD_TRAPDOOR, 2)
            .pattern("SSS")
            .pattern("SSS")
            .define('S', ModBlocks.RUBBERWOOD_PLANKS)
            .unlockedBy("has_rubberwood_planks", this.has(ModBlocks.RUBBERWOOD_PLANKS))
            .save(this.output, "rubberwood_trapdoor_from_rubberwood_planks");
        ShapelessRecipeBuilder.shapeless(this.registries.lookupOrThrow(Registries.ITEM), RecipeCategory.REDSTONE, ModBlocks.INSULATED_COPPER_CABLE, 1)
            .requires(ModBlocks.COPPER_CABLE)
            .requires(ModItems.RUBBER.get())
            .unlockedBy("has_copper_cable", this.has(ModBlocks.COPPER_CABLE))
            .save(this.output, "insulated_copper_cable_from_copper_cable_and_rubber");
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
