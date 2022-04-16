package io.github.FireTamer81.GeoPlayerModelTest.server.advancement;

import com.google.gson.JsonObject;
import io.github.FireTamer81.TestModMain;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.advancements.criterion.CriterionInstance;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.loot.ConditionArrayParser;
import net.minecraft.util.ResourceLocation;

public class StealIceCrystalTrigger extends MMTrigger<CriterionInstance, StealIceCrystalTrigger.Listener> {
    public static final ResourceLocation ID = new ResourceLocation(TestModMain.MODID, "steal_ice_crystal");

    public StealIceCrystalTrigger() {
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public CriterionInstance deserialize(JsonObject object, ConditionArrayParser conditions) {
        EntityPredicate.AndPredicate player = EntityPredicate.AndPredicate.deserializeJSONObject(object, "player", conditions);
        return new StealIceCrystalTrigger.Instance(player);
    }

    @Override
    public StealIceCrystalTrigger.Listener createListener(PlayerAdvancements playerAdvancements) {
        return new StealIceCrystalTrigger.Listener(playerAdvancements);
    }

    public void trigger(ServerPlayerEntity player) {
        StealIceCrystalTrigger.Listener listeners = this.listeners.get(player.getAdvancements());

        if (listeners != null) {
            listeners.trigger();
        }
    }

    static class Listener extends MMTrigger.Listener<CriterionInstance> {

        public Listener(PlayerAdvancements playerAdvancementsIn) {
            super(playerAdvancementsIn);
        }

        public void trigger() {
            this.listeners.stream().findFirst().ifPresent(listener -> listener.grantCriterion(this.playerAdvancements));
        }
    }

    public static class Instance extends CriterionInstance {
        public Instance(EntityPredicate.AndPredicate player) {
            super(StealIceCrystalTrigger.ID, player);
        }
    }
}
