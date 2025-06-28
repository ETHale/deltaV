package com.deltav.deltavmod.recipe;

import com.deltav.deltavmod.DeltaV;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = 
        DeferredRegister.create(Registries.RECIPE_SERIALIZER, DeltaV.MODID);
    public static final DeferredRegister<RecipeType<?>> TYPES = 
        DeferredRegister.create(Registries.RECIPE_TYPE, DeltaV.MODID);
    
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<AlloyFurnaceRecipe>> ALLOY_FURNACE_SERIALIZER =
        SERIALIZERS.register("alloy_furnace", AlloyFurnaceRecipe.Serializer::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<AlloyFurnaceRecipe>> ALLOY_FURNACE_TYPE =
        TYPES.register("alloy_furnace", () -> new RecipeType<AlloyFurnaceRecipe>() {
            @Override
            public String toString() {
                return "alloy_furnace";
            }
        });

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
        TYPES.register(eventBus);
    }
}
