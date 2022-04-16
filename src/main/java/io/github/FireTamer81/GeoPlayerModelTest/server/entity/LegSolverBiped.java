package io.github.FireTamer81.GeoPlayerModelTest.server.entity;

public final class LegSolverBiped extends LegSolver {
    public final Leg left, right;

    public LegSolverBiped(float forward, float side) {
        super(new Leg(forward, side), new Leg(forward, -side));
        this.left = this.legs[0];
        this.right = this.legs[1];
    }
}