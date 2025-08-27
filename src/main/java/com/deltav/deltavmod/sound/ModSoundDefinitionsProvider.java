package com.deltav.deltavmod.sound;


import com.deltav.deltavmod.DeltaV;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

public class ModSoundDefinitionsProvider extends SoundDefinitionsProvider{
    public ModSoundDefinitionsProvider(PackOutput output) {
        super(output, DeltaV.MODID);
    }

    // see https://docs.neoforged.net/docs/resources/client/sounds#datagen
    @Override
    public void registerSounds() {
        add(ModSounds.GEYSER.value(), SoundDefinition.definition()
            .with(
                sound(DeltaV.MODID + ":geyser", SoundDefinition.SoundType.SOUND)
                    .volume(0.8f)
                    .pitch(1f)
                    .weight(2)
                    .attenuationDistance(8)
                    .stream(true)
                    .preload(true)
            )
            .subtitle("sound."+DeltaV.MODID+"geyser")
            .replace(true)
        );
    }
    
}
