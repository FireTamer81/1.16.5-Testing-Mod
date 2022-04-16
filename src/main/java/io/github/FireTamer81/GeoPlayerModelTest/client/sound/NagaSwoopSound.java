package io.github.FireTamer81.GeoPlayerModelTest.client.sound;

import io.github.FireTamer81.GeoPlayerModelTest.server.sound.MMSounds;
import net.minecraft.client.audio.TickableSound;
import net.minecraft.entity.Entity;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Created by BobMowzie on 1/9/2019.
 */
@OnlyIn(Dist.CLIENT)
public class NagaSwoopSound extends TickableSound {
    private final Entity naga;
    int ticksExisted = 0;
    boolean active = true;

    public NagaSwoopSound(Entity naga) {
        super(MMSounds.ENTITY_NAGA_SWOOP.get(), SoundCategory.HOSTILE);
        this.naga = naga;
        volume = 2F;
        pitch = 1.2f;
        x = (float) naga.getPosX();
        y = (float) naga.getPosY();
        z = (float) naga.getPosZ();
        repeat = false;
    }

    @Override
    public void tick() {
        if (naga != null) {
            active = true;
            x = (float) naga.getPosX();
            y = (float) naga.getPosY();
            z = (float) naga.getPosZ();
            if (!naga.isAlive()) {
                active = false;
                finishPlaying();
            }
        }
        ticksExisted++;
    }
}
