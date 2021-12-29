package io.github.FireTamer81.api;

import net.minecraftforge.eventbus.api.IEventBus;

public class TestMod_AnnotationProcessor extends com.matyrobbrt.lib.registry.annotation.AnnotationProcessor {
    /**
     * Creates a new {@link AnnotationProcessor} which will be used in order to
     * process annotations. It is recommended to store this statically somewhere.
     *
     * @param modid the mod id to process the annotations for
     */
    public TestMod_AnnotationProcessor(String modid) {
        super(modid);
    }

    @Override
    public void register(IEventBus modBus) {
        super.register(modBus);
    }
}
