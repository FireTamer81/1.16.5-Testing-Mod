package io.github.FireTamer81.GeoPlayerModelTest.server.sound;

import io.github.FireTamer81.TestModMain;
import net.minecraftforge.fml.common.Mod;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = TestModMain.MODID)
public class MMSounds {
    private MMSounds() { }

    public static final DeferredRegister<SoundEvent> REG = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, TestModMain.MODID);

    // Generic
    public static final RegistryObject<SoundEvent> LASER = create("laser");
    public static final RegistryObject<SoundEvent> SUNSTRIKE = create("sunstrike");

    // Wroughtnaut
    public static final RegistryObject<SoundEvent> ENTITY_WROUGHT_WHOOSH = create("wroughtnaut.whoosh");
    public static final RegistryObject<SoundEvent> ENTITY_WROUGHT_AXE_LAND = create("wroughtnaut.axe_land");
    public static final RegistryObject<SoundEvent> ENTITY_WROUGHT_AXE_HIT = create("wroughtnaut.axe_hit");

    // Barakoa
    public static final RegistryObject<SoundEvent> ENTITY_BARAKOA_BLOWDART = create("barakoa.blowdart");

    public static final RegistryObject<SoundEvent> ENTITY_BARAKOA_HEAL_LOOP = create("barakoa.healloop");

    public static final RegistryObject<SoundEvent> ENTITY_BARAKO_BELLY = create("barako.belly");
    public static final RegistryObject<SoundEvent> ENTITY_SUPERNOVA_END = create("supernova.end");

    public static final RegistryObject<SoundEvent> ENTITY_FROSTMAW_ICEBREATH = create("frostmaw.icebreath");
    public static final RegistryObject<SoundEvent> ENTITY_FROSTMAW_ICEBREATH_START = create("frostmaw.icebreathstart");
    public static final RegistryObject<SoundEvent> ENTITY_FROSTMAW_FROZEN_CRASH = create("frostmaw.frozencrash");

    public static final RegistryObject<SoundEvent> ENTITY_NAGA_ACID_HIT = create("naga.acidhit");
    public static final RegistryObject<SoundEvent> ENTITY_NAGA_SWOOP = create("naga.swoop");

    public static final RegistryObject<SoundEvent> EFFECT_GEOMANCY_SMALL_CRASH = create("geomancy.smallcrash");
    public static final RegistryObject<SoundEvent> EFFECT_GEOMANCY_MAGIC_SMALL = create("geomancy.hitsmall");
    public static final RegistryObject<SoundEvent> EFFECT_GEOMANCY_MAGIC_BIG = create("geomancy.hitbig");
    public static final RegistryObject<SoundEvent> EFFECT_GEOMANCY_BREAK_LARGE_1 = create("geomancy.breaklarge");
    public static final RegistryObject<SoundEvent> EFFECT_GEOMANCY_BREAK_LARGE_2 = create("geomancy.breaklarge2");
    public static final RegistryObject<SoundEvent> EFFECT_GEOMANCY_BREAK_MEDIUM_1 = create("geomancy.breakmedium");
    public static final RegistryObject<SoundEvent> EFFECT_GEOMANCY_BREAK_MEDIUM_2 = create("geomancy.breakmedium2");
    public static final RegistryObject<SoundEvent> EFFECT_GEOMANCY_BREAK_MEDIUM_3 = create("geomancy.breakmedium3");
    public static final ImmutableList<Supplier<SoundEvent>> EFFECT_GEOMANCY_BREAK_MEDIUM = ImmutableList.of(
            EFFECT_GEOMANCY_BREAK_MEDIUM_1::get,
            EFFECT_GEOMANCY_BREAK_MEDIUM_2::get,
            EFFECT_GEOMANCY_BREAK_MEDIUM_3::get
    );

    public static final RegistryObject<SoundEvent> EFFECT_GEOMANCY_BREAK = create("geomancy.rockbreak");
    public static final RegistryObject<SoundEvent> EFFECT_GEOMANCY_CRASH = create("geomancy.rockcrash1");
    public static final RegistryObject<SoundEvent> EFFECT_GEOMANCY_CRUMBLE = create("geomancy.rockcrumble");
    public static final RegistryObject<SoundEvent> EFFECT_GEOMANCY_RUMBLE_1 = create("geomancy.rumble1");
    public static final RegistryObject<SoundEvent> EFFECT_GEOMANCY_RUMBLE_2 = create("geomancy.rumble2");
    public static final RegistryObject<SoundEvent> EFFECT_GEOMANCY_RUMBLE_3 = create("geomancy.rumble3");
    public static final ImmutableList<Supplier<SoundEvent>> EFFECT_GEOMANCY_RUMBLE = ImmutableList.of(
            EFFECT_GEOMANCY_RUMBLE_1::get,
            EFFECT_GEOMANCY_RUMBLE_2::get,
            EFFECT_GEOMANCY_RUMBLE_3::get
    );
    public static final RegistryObject<SoundEvent> EFFECT_GEOMANCY_HIT_SMALL = create("geomancy.smallrockhit");
    public static final RegistryObject<SoundEvent> EFFECT_GEOMANCY_BOULDER_CHARGE = create("geomancy.bouldercharge");

    // Music
    public static final RegistryObject<SoundEvent> MUSIC_BLACK_PINK = create("music.black_pink");
    public static final RegistryObject<SoundEvent> MUSIC_PETIOLE = create("music.petiole");

    private static RegistryObject<SoundEvent> create(String name) {
        return REG.register(name, () -> new SoundEvent(new ResourceLocation(TestModMain.MODID, name)));
    }
}
