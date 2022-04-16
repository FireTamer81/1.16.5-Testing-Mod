package io.github.FireTamer81.GeoPlayerModelTest._library.server.world;

import io.github.FireTamer81.TestModMain;

public enum TickRateHandler {
    INSTANCE;

    public static final float DEFAULT_TPS = 20.0F;

    private float tps = DEFAULT_TPS;

    public float getTPS() {
        return this.tps;
    }

    public void setTPS(float tps) {
        if (this.tps != tps) {
            TestModMain.PROXY.setTPS(tps);
        }
        this.tps = tps;
    }

    public void resetTPS() {
        this.setTPS(DEFAULT_TPS);
    }

    public long getTickRate() {
        return (long) (this.tps / DEFAULT_TPS * 50);
    }
}
