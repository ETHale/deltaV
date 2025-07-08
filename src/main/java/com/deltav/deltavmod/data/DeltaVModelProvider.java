package com.deltav.deltavmod.data;

import java.util.stream.Stream;

import com.deltav.deltavmod.DeltaV;
import com.deltav.deltavmod.block.ModBlocks;
import com.deltav.deltavmod.block.energy.generators.RedstoneGenerator;
import com.deltav.deltavmod.item.ModItems;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;

public class DeltaVModelProvider extends ModelProvider{
    public DeltaVModelProvider(PackOutput output) {
        super(output, DeltaV.MODID);
    }

    // CUSTOM ITEMS AND BLOCKS
    // FOR CUSTOM BLOCKSTATES OR MODELS EDIT THIS TO FILTER THEM OUT
    @Override
    protected Stream<? extends Holder<Block>> getKnownBlocks() {
        Stream<? extends Holder<Block>> list = BuiltInRegistries.BLOCK
            .listElements()
            .filter(holder -> holder.getKey().location().getNamespace().equals(modId))
            .filter(holder -> !(holder.value() instanceof RedstoneGenerator));
        return list;
    }
    
    // Generate models and associated files here
    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {

        // BLOCKS
        blockModels.createTrivialCube(ModBlocks.STEEL_BLOCK.get());
        blockModels.createTrivialCube(ModBlocks.ZINC_BLOCK.get());
        blockModels.createTrivialCube(ModBlocks.ZINC_ORE.get());
        blockModels.createTrivialCube(ModBlocks.DEEPSLATE_ZINC_ORE.get());
        blockModels.createTrivialCube(ModBlocks.RAW_ZINC_BLOCK.get());


        // alloy furnace
        blockModels.createFurnace(ModBlocks.ALLOY_FURNACE.get(), TexturedModel.ORIENTABLE.updateTexture(mapping ->
            mapping.put(TextureSlot.SIDE, this.modLocation("block/alloy_furnace_side"))
            .put(TextureSlot.FRONT, this.modLocation("block/alloy_furnace_front"))
            .put(TextureSlot.TOP, this.modLocation("block/alloy_furnace_top"))
            .put(TextureSlot.BOTTOM, this.modLocation("block/alloy_furnace_bottom"))
        ));

        // Kimberlite models
        Block kimberlite = ModBlocks.KIMBERLITE.get();
        blockModels.createTrivialCube(kimberlite);
        blockModels.familyWithExistingFullBlock(kimberlite).button(ModBlocks.KIMBERLITE_BUTTON.get());
        blockModels.familyWithExistingFullBlock(kimberlite).stairs(ModBlocks.KIMBERLITE_STAIRS.get());
        blockModels.familyWithExistingFullBlock(kimberlite).slab(ModBlocks.KIMBERLITE_SLAB.get());
        blockModels.familyWithExistingFullBlock(kimberlite).wall(ModBlocks.KIMBERLITE_WALL.get());
        blockModels.familyWithExistingFullBlock(kimberlite).pressurePlate(ModBlocks.KIMBERLITE_PRESSURE_PLATE.get());
        Block polished_kimberlite = ModBlocks.POLISHED_KIMBERLITE.get();
        blockModels.createTrivialCube(polished_kimberlite);
        blockModels.familyWithExistingFullBlock(polished_kimberlite).stairs(ModBlocks.POLISHED_KIMBERLITE_STAIRS.get());
        blockModels.familyWithExistingFullBlock(polished_kimberlite).slab(ModBlocks.POLISHED_KIMBERLITE_SLAB.get());
        blockModels.createTrivialCube(ModBlocks.KIMBERLITE_COAL_ORE.get());
        blockModels.createTrivialCube(ModBlocks.KIMBERLITE_COPPER_ORE.get());
        blockModels.createTrivialCube(ModBlocks.KIMBERLITE_DIAMOND_ORE.get());
        blockModels.createTrivialCube(ModBlocks.KIMBERLITE_EMERALD_ORE.get());
        blockModels.createTrivialCube(ModBlocks.KIMBERLITE_GOLD_ORE.get());
        blockModels.createTrivialCube(ModBlocks.KIMBERLITE_LAPIS_ORE.get());
        blockModels.createTrivialCube(ModBlocks.KIMBERLITE_REDSTONE_ORE.get());
        blockModels.createTrivialCube(ModBlocks.KIMBERLITE_ZINC_ORE.get());
        blockModels.createTrivialCube(ModBlocks.KIMBERLITE_IRON_ORE.get());

        blockModels.createTrivialCube(ModBlocks.MOLTEN_BEDROCK.get());
        
        // ITEMS
        itemModels.generateFlatItem(ModItems.STEEL_INGOT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.RAW_ZINC.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.ZINC_INGOT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.ZINC_BATTERY.get(), ModelTemplates.FLAT_ITEM);
    }
}
