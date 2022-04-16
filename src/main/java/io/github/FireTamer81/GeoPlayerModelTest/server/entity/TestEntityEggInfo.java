package io.github.FireTamer81.GeoPlayerModelTest.server.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;

public class TestEntityEggInfo {
    public final EntityType<? extends MobEntity> type;

    public final Class<? extends MobEntity> clazz;

    public final int primaryColor;

    public final int secondaryColor;

    public TestEntityEggInfo(EntityType<? extends MobEntity> type, Class<? extends MobEntity> clazz, int primaryColor, int secondaryColor) {
        this.type = type;
        this.clazz = clazz;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
    }
}
