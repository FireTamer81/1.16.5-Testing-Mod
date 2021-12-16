package io.github.FireTamer81;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BlockModels {
    @OnlyIn(Dist.CLIENT)
    public static void initModels() {
        ColorableFullBlock.initModel();
    }
}
