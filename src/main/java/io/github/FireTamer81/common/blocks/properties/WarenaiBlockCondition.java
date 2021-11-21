package io.github.FireTamer81.common.blocks.properties;

import net.minecraft.util.IStringSerializable;

public enum WarenaiBlockCondition implements IStringSerializable {
    POLISHED("polished"),
    NORMAL("normal"),
    SCUFFED("scuffed"),
    CRACKED1("cracked1"),
    CRACKED2("cracked2"),
    CRACKED3("cracked3"),
    CRACKED4("cracked4");

    private final String name;

    WarenaiBlockCondition(String pName) {
        name = pName;
    }

    @Override
    public String getSerializedName() {
        return null;
    }
}
