package com.deltav.deltavmod.block.custom;

import javax.annotation.Nullable;

import com.deltav.deltavmod.block.entity.FractionatorBlockEntity;
import com.deltav.deltavmod.block.entity.ModBlockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

/**
 * Block representing a fractionator.
 * 
 * @author Adam Crawley
 */
public class FractionatorBlock extends Block implements EntityBlock {
    public FractionatorBlock(Properties properties) {
        super(properties);
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new FractionatorBlockEntity(pos, state);
    }
    
    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide() && player instanceof ServerPlayer sp) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof FractionatorBlockEntity fractionator) {
                sp.openMenu(new SimpleMenuProvider(fractionator, Component.translatable("gui.deltav.fractionator")), pos);
            }
            return InteractionResult.CONSUME;
        }
        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide()) {
            return null;
        }

        // This outer if statement mimicks BaseEntityBlock::createTickerHelper
        // It checks that the server and client BE type are the same
        if (type == ModBlockEntities.FRACTIONATOR_BE.get()) {
            return (level1, pos, state1, blockEntity) -> {
                if (blockEntity instanceof FractionatorBlockEntity fractionator) {
                    fractionator.tick();
                }
            };
        }
        return null;
    }
}
