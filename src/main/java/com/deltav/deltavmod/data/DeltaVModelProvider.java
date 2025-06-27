package com.deltav.deltavmod.data;

import com.deltav.deltavmod.DeltaV;
import com.deltav.deltavmod.block.ModBlocks;
import com.deltav.deltavmod.block.custom.AlloyFurnaceBlock;
import com.deltav.deltavmod.item.ModItems;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.client.renderer.block.model.Variant;
import net.minecraft.client.renderer.block.model.VariantMutator;
import net.minecraft.client.renderer.block.model.VariantMutator.VariantProperty;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.Weighted;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class DeltaVModelProvider extends ModelProvider{
    public DeltaVModelProvider(PackOutput output) {
        super(output, DeltaV.MODID);
    }
    
    // Generate models and associated files here
    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {

        // BLOCKS
        blockModels.createTrivialCube(ModBlocks.STEEL_BLOCK.get());


        // alloy furnace - this was mildly painful

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

        // ITEMS
        itemModels.generateFlatItem(ModItems.STEEL_INGOT.get(), ModelTemplates.FLAT_ITEM);
    }
}
