package io.github.FireTamer81.TeleportCommandStuffTest1;

import net.minecraft.entity.Entity;
import net.minecraft.world.server.ServerWorld;


@FunctionalInterface
interface TargetListener {
  ServerWorld getTargetDimension(Entity paramEntity);
}
