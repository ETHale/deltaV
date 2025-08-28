package com.deltav.deltavmod.sound;

import com.deltav.deltavmod.DeltaV;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModSounds {
        // Assuming that your mod id is examplemod
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, DeltaV.MODID);
    
    // All vanilla sounds use variable range events.
    public static final Holder<SoundEvent> GEYSER = SOUND_EVENTS.register(
            "geyser",
            // Takes in the registry name
            SoundEvent::createVariableRangeEvent
    );

}
