package com.deltav.deltavmod.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;

public class AlloyFurnaceBlockEntity extends BlockEntity{

    public final ItemStackHandler inventory = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    public AlloyFurnaceBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.ALLOY_FURNACE_BE.get(), pos, blockState);
    }

}
