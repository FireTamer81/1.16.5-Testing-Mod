package io.github.FireTamer81.GeoPlayerModelTest._library.server.animation;

public class NamedAnimation extends Animation {

    /**
     * The animation name
     */
    private final String name;

    private NamedAnimation(String animationName, int duration) {
        super(duration);
        this.name = animationName;
    }

    /**
     * The animation name
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Creates a new NamedAnimation with the given name and duration
     * @param name the name of the animation (for Tabula animations, use the name entered for the animation)
     * @param duration the duration, in ticks, of your animation
     * @return a newly created NamedAnimation
     */
    public static NamedAnimation create(String name, int duration) {
        return new NamedAnimation(name, duration);
    }
}
