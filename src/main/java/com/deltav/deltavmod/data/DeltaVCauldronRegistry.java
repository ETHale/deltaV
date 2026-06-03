package com.deltav.deltavmod.data;

import com.deltav.deltavmod.DeltaV;
import com.deltav.deltavmod.block.ModBlocks;
import com.deltav.deltavmod.fluid.ModFluids;
import com.deltav.deltavmod.item.ModItems;

import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.fluids.RegisterCauldronFluidContentEvent;

@EventBusSubscriber(modid = DeltaV.MODID, value = Dist.CLIENT)
public class DeltaVCauldronRegistry {
    @SubscribeEvent
    public static void registerCauldronContent(RegisterCauldronFluidContentEvent event) {
        // Registers your custom liquid for the vanilla cauldron block.
        event.register(
                ModBlocks.LATEX_CAULDRON.get(), 
                ModFluids.LATEX_SOURCE.get(), 
                FluidType.BUCKET_VOLUME,
                LayeredCauldronBlock.LEVEL
        );
    }

    public static final CauldronInteraction.InteractionMap LATEX_INTERACTIONS = CauldronInteraction.newInteractionMap("latex");
    
    public static void bootStrap() {
        var latex_map = LATEX_INTERACTIONS.map();
        var empty_map = CauldronInteraction.EMPTY.map();
        
        latex_map.put(Items.BUCKET, (state, level, pos, player, hand, stack) -> 
            CauldronInteraction.fillBucket(
                state, 
                level, 
                pos, 
                player, 
                hand, 
                stack, 
                new ItemStack(ModItems.LATEX_BUCKET.get()),
                val -> val.getValue(LayeredCauldronBlock.LEVEL) == 3,
                SoundEvents.BUCKET_FILL
            )
        );
        latex_map.put(ModItems.LATEX_BUCKET.get(), (state, level, pos, player, hand, stack) -> 
            CauldronInteraction.emptyBucket(
                level, 
                pos,  
                player, 
                hand, 
                stack,
                ModBlocks.LATEX_CAULDRON.get().defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3),
                SoundEvents.BUCKET_EMPTY
            )
        );

        // Register latex bucket interaction with vanilla cauldron
        empty_map.put(Items.BUCKET, (state, level, pos, player, hand, stack) -> 
            CauldronInteraction.fillBucket(
                state, 
                level, 
                pos, 
                player, 
                hand, 
                stack, 
                new ItemStack(ModItems.LATEX_BUCKET.get()),
                val -> val.getValue(LayeredCauldronBlock.LEVEL) == 3,
                SoundEvents.BUCKET_FILL
            )
        );
        empty_map.put(ModItems.LATEX_BUCKET.get(), (state, level, pos, player, hand, stack) -> 
            CauldronInteraction.emptyBucket(
                level, 
                pos,  
                player, 
                hand, 
                stack,
                ModBlocks.LATEX_CAULDRON.get().defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3),
                SoundEvents.BUCKET_EMPTY
            )
        );
    }
}
