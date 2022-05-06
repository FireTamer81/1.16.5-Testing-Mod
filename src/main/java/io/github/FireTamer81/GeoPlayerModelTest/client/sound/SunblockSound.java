package io.github.FireTamer81.GeoPlayerModelTest.client.sound;

import io.github.FireTamer81.GeoPlayerModelTest._library.client.model.tools.ControlledAnimation;
import io.github.FireTamer81.GeoPlayerModelTest.server.potion.EffectHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.sound.MMSounds;
import net.minecraft.client.audio.TickableSound;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SunblockSound extends TickableSound {
    private final LivingEntity entity;
    int ticksExisted = 0;
    ControlledAnimation volumeControl;
    boolean active = true;

    public SunblockSound(LivingEntity entity) {
        super(MMSounds.ENTITY_BARAKOA_HEAL_LOOP.get(), SoundCategory.NEUTRAL);
        this.entity = entity;
        volume = 4F;
        pitch = 1f;
        x = (float) entity.getPosX();
        y = (float) entity.getPosY();
        z = (float) entity.getPosZ();
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
        if (entity != null) {
            active = true;
            x = (float) entity.getPosX();
            y = (float) entity.getPosY();
            z = (float) entity.getPosZ();
            boolean barakoaHealing = false;

            boolean hasSunblock = entity.isPotionActive(EffectHandler.SUNBLOCK);
            active = barakoaHealing || hasSunblock;
            if (!entity.isAlive()) {
                active = false;
            }
        }
        else {
            active = false;
        }
        ticksExisted++;
    }
}
