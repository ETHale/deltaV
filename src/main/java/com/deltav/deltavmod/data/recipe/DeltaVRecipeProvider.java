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
            .save(this.output);
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
