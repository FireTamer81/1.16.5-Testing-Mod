package io.github.FireTamer81.blockColorignStuff;

import net.minecraft.util.IStringSerializable;

public enum ColorsEnum implements IStringSerializable {
    BLACK("black"),
    BROWN("brown"),
    BLUE("blue"),
    CYAN("cyan"),
    GRAY("gray"),
    GREEN("green"),
    LIGHTBLUE("lightblue"),
    LIGHTGRAY("lightgray"),
    LIME("lime"),
    MAGENTA("magenta"),
    ORANGE("orange"),
    PURPLE("purple"),
    PINK("pink"),
    RED("red"),
    WHITE("white"),
    YELLOW("yellow");

    private final String name;

    ColorsEnum(String pName) {
        name = pName;
    }

    @Override
    public String getSerializedName() {
        return name;
    }
}
