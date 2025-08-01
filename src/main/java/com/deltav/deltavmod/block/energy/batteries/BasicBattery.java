package com.deltav.deltavmod.block.energy.batteries;

import javax.annotation.Nullable;

import com.deltav.deltavmod.block.entity.BasicBatteryBlockEntity;
import com.deltav.deltavmod.menu.BasicBatteryMenu;

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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class BasicBattery extends Block implements EntityBlock{

    public BasicBattery(Properties properties) {
        super(properties);
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BasicBatteryBlockEntity(pos, state);
    }
    
    
    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide() && player instanceof ServerPlayer sp) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof BasicBatteryBlockEntity battery) {
                sp.openMenu(new SimpleMenuProvider(
                    (id, inv, pl) -> new BasicBatteryMenu(id, inv, battery),
                    Component.translatable("gui.deltav.basic_battery")),
                    pos
                );
            }
            return InteractionResult.CONSUME;
        }
        return InteractionResult.SUCCESS;
    }
}
