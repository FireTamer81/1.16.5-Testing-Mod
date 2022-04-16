package io.github.FireTamer81.GeoPlayerModelTest.server.creativetab;

import io.github.FireTamer81.GeoPlayerModelTest.server.item.ItemHandler;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public enum CreativeTabHandler {
    INSTANCE;

    public ItemGroup creativeTab;

    public void onInit() {
        creativeTab = new ItemGroup("mowziesmobs.creativeTab") {
            @Override
            @OnlyIn(Dist.CLIENT)
            public ItemStack createIcon() {
                return new ItemStack(ItemHandler.LOGO);
            }
        };
    }
}