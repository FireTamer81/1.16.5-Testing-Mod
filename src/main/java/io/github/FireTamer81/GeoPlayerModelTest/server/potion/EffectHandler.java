package io.github.FireTamer81.GeoPlayerModelTest.server.potion;

import io.github.FireTamer81.GeoPlayerModelTest.server.potion.effects.*;
import io.github.FireTamer81.TestModMain;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TestModMain.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EffectHandler {
    private EffectHandler() {
    }

    public static final EffectSunsBlessing SUNS_BLESSING = (EffectSunsBlessing) new EffectSunsBlessing().setRegistryName(TestModMain.MODID, "suns_blessing");
    public static final EffectGeomancy GEOMANCY = (EffectGeomancy) new EffectGeomancy().setRegistryName(TestModMain.MODID, "geomancy");
    public static final EffectFrozen FROZEN = (EffectFrozen) new EffectFrozen().setRegistryName(TestModMain.MODID, "frozen");
    public static final EffectPoisonResist POISON_RESIST = (EffectPoisonResist) new EffectPoisonResist().setRegistryName(TestModMain.MODID, "poison_resist");
    public static final EffectSunblock SUNBLOCK = (EffectSunblock) new EffectSunblock().setRegistryName(TestModMain.MODID, "sunblock");

    @SubscribeEvent
    public static void register(RegistryEvent.Register<Effect> event) {
        event.getRegistry().registerAll(
                SUNS_BLESSING,
                GEOMANCY,
                FROZEN,
                POISON_RESIST,
                SUNBLOCK
        );
    }

    public static void addOrCombineEffect(LivingEntity entity, Effect effect, int duration, int amplifier, boolean ambient, boolean showParticles) {
        EffectInstance effectInst = entity.getActivePotionEffect(effect);
        EffectInstance newEffect = new EffectInstance(effect, duration, amplifier, ambient, showParticles);
        if (effectInst != null) effectInst.combine(newEffect);
        else entity.addPotionEffect(newEffect);
    }
}
