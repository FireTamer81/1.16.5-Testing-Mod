package io.github.FireTamer81.GeoPlayerModelTest.server.potion;

import io.github.FireTamer81.TestModMain;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.ResourceLocation;

public class TestEffect extends Effect {
    private static final ResourceLocation TEXTURE = new ResourceLocation(TestModMain.MODID, "textures/gui/container/potions.png");

    public TestEffect(EffectType type, int liquidColor) {
        super(type, liquidColor);
    }

    public boolean isDurationEffectTick(int id, int amplifier) {
        return true;
    }
}
