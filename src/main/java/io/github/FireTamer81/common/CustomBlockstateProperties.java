package io.github.FireTamer81.common;

import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;

public class CustomBlockstateProperties
{
    public static final IntegerProperty CRACKED_DIRTY_CLEAN_POLISHED = IntegerProperty.create("block_condition", 2, 4);

    //There are actually only cracked block models, the zero is so it isn't always applied and will be the default state after finished with testing.
    public static final IntegerProperty LEVEL_OF_CRACKED = IntegerProperty.create("level_of_cracked", 0, 4);
}
