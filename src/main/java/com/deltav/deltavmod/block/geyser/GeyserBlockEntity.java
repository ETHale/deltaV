package com.deltav.deltavmod.block.geyser;

import com.deltav.deltavmod.block.entity.ModBlockEntities;
import com.deltav.deltavmod.block.geyser.GeyserBlock.GeyserMode;
import com.deltav.deltavmod.sound.ModSounds;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;

public class GeyserBlockEntity extends BlockEntity {
    private int cooldown = 0;
    private int eruptionRemaining = 0;
    private GeyserMode activeMode = null;
    
    public GeyserBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.GEYSER_BE.get(), pos, state); // adapt registration reference
        this.cooldown = 0;
    }

    public void serverTick(Level level) {
        if (level == null || level.isClientSide()) return;

        GeyserBlock block = (GeyserBlock) this.getBlockState().getBlock();

        if (eruptionRemaining > 0) {
            // currently erupting
            handleEruptionTick(block, (ServerLevel) level, worldPosition);
            eruptionRemaining--;
            if (eruptionRemaining <= 0) {
                activeMode = null;
                cooldown = block.getCooldownTicks();
            }
            setChanged();
            return;
        }

        // not erupting: decrement cooldown then try to start
        if (cooldown > 0) {
            cooldown--;
            setChanged();
            return;
        }

        // cooldown finished, try to start eruption using chance
        if (level.getRandom().nextDouble() <= block.getEruptionChance()) {
            startEruption((ServerLevel) level, worldPosition, block);
            setChanged();
        } else {
            // failed to trigger; set small cooldown to retry later to avoid continuous checks
            cooldown = Math.max(20, block.getCooldownTicks() / 10);
            setChanged();
        }
    }

    private void startEruption(ServerLevel server, BlockPos pos, GeyserBlock block) {
        // decide mode
        if (block.getDefaultMode() == GeyserMode.RANDOM) {
            activeMode = (server.getRandom().nextDouble() < block.getParticleModeChance())
                ? GeyserMode.GAS : GeyserMode.LIQUID;
        } else {
            activeMode = block.getDefaultMode();
        }

        eruptionRemaining = block.getEruptionDurationTicks();

        // if liquid mode, attempt to place fluid once at start
        if (activeMode == GeyserMode.LIQUID) {
            BlockPos target = pos.above();
            BlockState targetState = server.getBlockState(target);
            if (canPlaceFluidAt(targetState)) {
                server.setBlock(target, block.getFluidState(), 3);
            }
        } else {
            if (level.getBlockState(pos.above()).getCollisionShape(server, pos.above()).isEmpty()) {
                server.playSound(
                    null,
                    pos,
                    ModSounds.GEYSER.value(),
                    SoundSource.BLOCKS,
                    0.8F,
                    1.0F
                );
            }
        }
    }

    private void handleEruptionTick(GeyserBlock block, ServerLevel server, BlockPos pos) {
        if (activeMode != GeyserMode.GAS) return;

        int startY = pos.getY() + 1;

        BlockPos checkPos = new BlockPos(pos.getX(), startY, pos.getZ());
        BlockState startState = server.getBlockState(checkPos);

        // if directly above is solid/has collision -> no particles
        if (!startState.getCollisionShape(server, checkPos).isEmpty()) return;

        // base burst that rises
        double cx = pos.getX() + 0.5;
        double cz = pos.getZ() + 0.5;
        double baseY = startY + 0.15;

        // strong burst at base that has upward motion
        server.sendParticles(
            block.getParticleType(),
            cx, baseY, cz,
            Math.max(1, block.getParticleCountPerTick()), // count
            0, 0.2, 0,   // small spread in X,Y,Z (tight column)
            0.1                // speed -> gives upward motion for many particles
        );
        applySteamDamagePerColumn(block, server, checkPos, startY);
    }

    private boolean canPlaceFluidAt(BlockState state) {
        return state.isAir() && state.getFluidState().isEmpty();
    }

    private int damageTickCounter = 0;

    private void applySteamDamagePerColumn(GeyserBlock block, ServerLevel server, BlockPos pos, int startY) {
        float dmg = block.getGasDamage();
        if (dmg <= 0f) return;
        int interval = Math.max(1, block.getGasDamageInterval());
        if (++damageTickCounter % interval != 0 && damageTickCounter != 0) return;

        float maxHeight = block.getEruptionHeight();
        int worldMaxY = server.getHeight() - 1;
        int radius = (int) Math.ceil(block.getGasRadius());

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                // keep circular area
                if (dx * dx + dz * dz > block.getGasRadius() * block.getGasRadius()) continue;

                int bx = pos.getX() + dx;
                int bz = pos.getZ() + dz;
                BlockPos colBase = new BlockPos(bx, startY, bz);

                // if block directly above this column is solid, skip this column
                if (!server.getBlockState(colBase).getCollisionShape(server, colBase).isEmpty()) continue;

                // find highest free Y in this column (stop when next block has collision or hit height/world limit)
                int topY = startY;
                while (topY < startY + maxHeight && topY < worldMaxY) {
                    BlockPos above = new BlockPos(bx, topY + 1, bz);
                    if (!server.getBlockState(above).getCollisionShape(server, above).isEmpty()) break;
                    topY++;
                }

                // build per-column AABB covering the block column area
                double minX = bx;
                double maxX = bx + 1.0;
                double minZ = bz;
                double maxZ = bz + 1.0;
                double minY = startY;
                double maxY = topY + 1.0; // include top block space

                if (maxY <= minY) continue; // nothing to affect

                AABB box = new AABB(minX, minY, minZ, maxX, maxY, maxZ);

                // collect players (or any LivingEntity class) and apply effects
                var players = server.getEntitiesOfClass(Player.class, box,
                    p -> p.isAlive() && !p.isSpectator());

                for (Player player : players) {
                    player.hurtServer((ServerLevel) level, level.damageSources().hotFloor(), dmg);
                    var motion = player.getDeltaMovement();
                    double newY = Math.max(motion.y, block.getGasKnockup());
                    player.setDeltaMovement(motion.x, newY, motion.z);
                }
            }
        }
    }

    @Override
    protected void loadAdditional(ValueInput in) {
        // read our stored CompoundTag and extract fields
        in.read("NeoForgeData", CompoundTag.CODEC).ifPresent(neo -> {
            if (neo.contains("Cooldown")) this.cooldown = neo.getInt("Cooldown").orElse(0);
            if (neo.contains("EruptionRemaining")) this.eruptionRemaining = neo.getInt("EruptionRemaining").orElse(0);

            if (neo.contains("ActiveMode")) {
                try {
                    this.activeMode = GeyserMode.valueOf(neo.getString("ActiveMode").orElse(""));
                } catch (IllegalArgumentException e) {
                    this.activeMode = null;
                }
            } else {
                this.activeMode = null;
            }
        });

        // keep existing attachments handling
        in.child(ATTACHMENTS_NBT_KEY).ifPresent(this::deserializeAttachments);
    }

    @Override
    protected void saveAdditional(ValueOutput out) {
        // pack our fields into a CompoundTag then store it using the CompoundTag codec
        CompoundTag neo = new CompoundTag();
        neo.putInt("Cooldown", this.cooldown);
        neo.putInt("EruptionRemaining", this.eruptionRemaining);
        neo.putString("ActiveMode", this.activeMode == null ? "NONE" : this.activeMode.name());

        out.store("NeoForgeData", CompoundTag.CODEC, neo);

        // keep existing attachments handling
        var attachments = out.child(ATTACHMENTS_NBT_KEY);
        serializeAttachments(attachments);
        if (attachments.isEmpty()) out.discard(ATTACHMENTS_NBT_KEY);
    }

}
