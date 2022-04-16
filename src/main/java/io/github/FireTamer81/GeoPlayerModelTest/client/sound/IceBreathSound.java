package io.github.FireTamer81.GeoPlayerModelTest.client.sound;

import io.github.FireTamer81.GeoPlayerModelTest._library.client.model.tools.ControlledAnimation;
import io.github.FireTamer81.GeoPlayerModelTest.server.sound.MMSounds;
import net.minecraft.client.audio.TickableSound;
import net.minecraft.entity.Entity;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class IceBreathSound extends TickableSound {
    private final Entity iceBreath;
    int ticksExisted = 0;
    ControlledAnimation volumeControl;
    boolean active = true;

    public IceBreathSound(Entity icebreath) {
        super(MMSounds.ENTITY_FROSTMAW_ICEBREATH.get(), SoundCategory.NEUTRAL);
        this.iceBreath = icebreath;
        volume = 3F;
        pitch = 1f;
        x = (float) icebreath.getPosX();
        y = (float) icebreath.getPosY();
        z = (float) icebreath.getPosZ();
        volumeControl = new ControlledAnimation(10);
        repeat = true;
    }

    @Override
    public void tick() {
        if (active) volumeControl.increaseTimer();
        else volumeControl.decreaseTimer();
        volume = volumeControl.getAnimationFraction();
        if (volumeControl.getAnimationFraction() <= 0.05)
            finishPlaying();
        if (iceBreath != null) {
            active = true;
            x = (float) iceBreath.getPosX();
            y = (float) iceBreath.getPosY();
            z = (float) iceBreath.getPosZ();
            if (!iceBreath.isAlive()) {
                active = false;
            }
        }
        else {
            active = false;
        }
        ticksExisted++;
    }
}
