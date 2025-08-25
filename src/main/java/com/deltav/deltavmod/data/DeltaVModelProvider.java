package com.deltav.deltavmod.data;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.stream.Stream;

import com.deltav.deltavmod.DeltaV;
import com.deltav.deltavmod.block.ModBlocks;
import com.deltav.deltavmod.block.energy.generators.RedstoneGenerator;
import com.deltav.deltavmod.item.ModItems;
import com.google.common.collect.ImmutableMap;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.BlockModelGenerators.BlockFamilyProvider;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.client.renderer.block.model.Variant;
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
        blockModels.createTrivialCube(ModBlocks.COBALT_BLOCK.get());
        blockModels.createTrivialCube(ModBlocks.COBALT_ORE.get());
        blockModels.createTrivialCube(ModBlocks.DEEPSLATE_COBALT_ORE.get());
        blockModels.createTrivialCube(ModBlocks.RAW_COBALT_BLOCK.get());
        blockModels.createTrivialCube(ModBlocks.PRISMIUM_BLOCK.get());

        blockModels.createTrivialCube(ModBlocks.FRACTIONATOR.get());

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
        blockModels.createTrivialCube(ModBlocks.KIMBERLITE_COBALT_ORE.get());        

        blockModels.createTrivialCube(ModBlocks.MOLTEN_BEDROCK.get());

        blockModels.createTrivialCube(ModBlocks.CRUSHER.get());

        TexturedModel.Provider bbTextProvider = TexturedModel.ORIENTABLE.updateTexture(mapping ->
            mapping.put(TextureSlot.SIDE, this.modLocation("block/basic_battery_side"))
            .put(TextureSlot.FRONT, this.modLocation("block/basic_battery_side"))
            .put(TextureSlot.TOP, this.modLocation("block/basic_battery_top"))
            .put(TextureSlot.BOTTOM, this.modLocation("block/basic_battery_top"))
        );
        MultiVariant basic_battery_variant = blockModels.plainVariant(bbTextProvider.create(ModBlocks.BASIC_BATTERY.get(), blockModels.modelOutput));
        blockModels.blockStateOutput.accept(blockModels.createSimpleBlock(ModBlocks.BASIC_BATTERY.get(), basic_battery_variant));

        blockModels.createTrivialCube(ModBlocks.SILICA_SAND.get());
        Block silica_sandstone = ModBlocks.SILICA_SANDSTONE.get();
        // dont ask how I found this 
        Map<Block, TexturedModel> TEXTURED_MODELS = ImmutableMap.<Block, TexturedModel>builder()
            .put(ModBlocks.SILICA_SANDSTONE.get(), TexturedModel.TOP_BOTTOM_WITH_WALL.get(ModBlocks.SILICA_SANDSTONE.get()))
            .put(ModBlocks.SMOOTH_SILICA_SANDSTONE.get(), 
                TexturedModel.createAllSame(TextureMapping.getBlockTexture(ModBlocks.SILICA_SANDSTONE.get(), "_top"))
            )
            .put(ModBlocks.CUT_SILICA_SANDSTONE.get(),
                TexturedModel.COLUMN
                    .get(ModBlocks.SILICA_SANDSTONE.get())
                    .updateTextures(map -> map.put(
                        TextureSlot.SIDE, TextureMapping.getBlockTexture(ModBlocks.CUT_SILICA_SANDSTONE.get()))
                    )
            )
            .put(ModBlocks.CHISELED_SILICA_SANDSTONE.get(),
                TexturedModel.COLUMN
                    .get(ModBlocks.CHISELED_SILICA_SANDSTONE.get())
                    .updateTextures(map -> {
                        map.put(TextureSlot.END, TextureMapping.getBlockTexture(ModBlocks.SILICA_SANDSTONE.get(), "_top"));
                        map.put(TextureSlot.SIDE, TextureMapping.getBlockTexture(ModBlocks.CHISELED_SILICA_SANDSTONE.get()));
                    })
            )

            .build();
        TEXTURED_MODELS.forEach((block, textureModel) -> {
            MultiVariant multivariant = blockModels.plainVariant(textureModel.create(block, blockModels.modelOutput));
            blockModels.blockStateOutput.accept(blockModels.createSimpleBlock(block, multivariant));
        });
        TextureMapping silicaSandstoneMapping = new TextureMapping()
            .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(ModBlocks.SILICA_SANDSTONE.get()))
            .put(TextureSlot.TOP, TextureMapping.getBlockTexture(ModBlocks.SILICA_SANDSTONE.get(), "_top"))
            .put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(ModBlocks.SILICA_SANDSTONE.get()));
        BlockFamilyProvider silicaSandstoneFamilyProvider = createTempFamilyProvider(silicaSandstoneMapping, ModBlocks.SILICA_SANDSTONE.get(), blockModels);

        silicaSandstoneFamilyProvider.stairs(ModBlocks.SILICA_SANDSTONE_STAIRS.get());
        silicaSandstoneFamilyProvider.slab(ModBlocks.SILICA_SANDSTONE_SLAB.get());
        blockModels.familyWithExistingFullBlock(silica_sandstone).wall(ModBlocks.SILICA_SANDSTONE_WALL.get());

        TextureMapping cutSilicaSandstoneMapping = new TextureMapping()
            .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(ModBlocks.CUT_SILICA_SANDSTONE.get()))
            .put(TextureSlot.END, TextureMapping.getBlockTexture(ModBlocks.SILICA_SANDSTONE.get(), "_top"));
        BlockFamilyProvider cutSilicaSandstoneFamilyProvider = createTempFamilyProvider(cutSilicaSandstoneMapping, ModBlocks.CUT_SILICA_SANDSTONE.get(), blockModels);
        cutSilicaSandstoneFamilyProvider.slab(ModBlocks.CUT_SILICA_SANDSTONE_SLAB.get());

        TextureMapping smoothSilicaSandstoneMapping = new TextureMapping()
            .put(TextureSlot.ALL, TextureMapping.getBlockTexture(ModBlocks.SILICA_SANDSTONE.get(), "_top"));
        BlockFamilyProvider smoothSilicaSandstoneFamilyProvider = createTempFamilyProvider(smoothSilicaSandstoneMapping, ModBlocks.SMOOTH_SILICA_SANDSTONE.get(), blockModels);
        smoothSilicaSandstoneFamilyProvider.stairs(ModBlocks.SMOOTH_SILICA_SANDSTONE_STAIRS.get());
        smoothSilicaSandstoneFamilyProvider.slab(ModBlocks.SMOOTH_SILICA_SANDSTONE_SLAB.get());
        
        // ITEMS
        itemModels.generateFlatItem(ModItems.STEEL_INGOT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.RAW_ZINC.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.ZINC_INGOT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.RAW_COBALT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.COBALT_INGOT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.ZINC_BATTERY.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.OIL_BUCKET.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.BARREL.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.GLOOPY_RESIDUE.get(), ModelTemplates.FLAT_ITEM);

        // FLUIDS
        Block oil = ModBlocks.OIL_FLUID.get();
        blockModels.createNonTemplateModelBlock(oil);
        itemModels.generateFlatItem(ModItems.SILICA_DUST.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.SILICON.get(), ModelTemplates.FLAT_ITEM);
    }

    private BlockFamilyProvider createTempFamilyProvider(TextureMapping mapping, Block block, BlockModelGenerators blockModels) {
        BlockFamilyProvider famProv = blockModels.new BlockFamilyProvider(mapping);
        try {
            Field field = BlockFamilyProvider.class.getDeclaredField("fullBlock");
            field.setAccessible(true);
            Variant variant = new Variant(ModelLocationUtils.getModelLocation(block));
            field.set(famProv, variant);
            return famProv;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
