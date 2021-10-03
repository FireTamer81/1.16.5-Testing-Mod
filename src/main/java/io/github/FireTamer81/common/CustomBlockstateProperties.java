package io.github.FireTamer81.common;

import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;

public class CustomBlockstateProperties
{
    public static final IntegerProperty CRACKED_DIRTY_CLEAN_POLISHED = IntegerProperty.create("block_condition", 1, 4);
}
