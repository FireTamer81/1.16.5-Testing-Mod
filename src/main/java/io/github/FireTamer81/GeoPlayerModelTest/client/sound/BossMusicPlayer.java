package io.github.FireTamer81.GeoPlayerModelTest.client.sound;

import io.github.FireTamer81.GeoPlayerModelTest.server.config.ConfigHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.MowzieEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

public class BossMusicPlayer {
    public static BossMusicSound bossMusic;

    public static void playBossMusic(MowzieEntity entity) {
        if (!ConfigHandler.CLIENT.playBossMusic.get()) return;

        SoundEvent soundEvent = entity.getBossMusic();
        if (soundEvent != null && entity.isAlive()) {
            PlayerEntity player = Minecraft.getInstance().player;
            if (bossMusic != null) {
                float f2 = Minecraft.getInstance().gameSettings.getSoundLevel(SoundCategory.MUSIC);
                if (f2 <= 0) {
                    bossMusic = null;
                }
                else if (bossMusic.getBoss() == entity && !entity.canPlayerHearMusic(player)) {
                    bossMusic.setBoss(null);
                }
                else if (bossMusic.getBoss() == null && bossMusic.getSoundEvent() == soundEvent) {
                    bossMusic.setBoss(entity);
                }
            }
            else {
                if (entity.canPlayerHearMusic(player)) {
                    bossMusic = new BossMusicSound(entity.getBossMusic(), entity);
                }
            }
            if (bossMusic != null && !Minecraft.getInstance().getSoundHandler().isPlaying(bossMusic)) {
                Minecraft.getInstance().getSoundHandler().play(bossMusic);
            }
        }
    }

    public static void stopBossMusic(MowzieEntity entity) {
        if (!ConfigHandler.CLIENT.playBossMusic.get()) return;

        if (bossMusic != null && bossMusic.getBoss() == entity)
            bossMusic.setBoss(null);
    }
}
