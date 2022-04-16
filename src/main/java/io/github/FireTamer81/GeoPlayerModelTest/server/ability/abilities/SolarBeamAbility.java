package io.github.FireTamer81.GeoPlayerModelTest.server.ability.abilities;

import io.github.FireTamer81.GeoPlayerModelTest.server.ability.AbilityType;
import io.github.FireTamer81.GeoPlayerModelTest.server.ability.Ability;
import io.github.FireTamer81.GeoPlayerModelTest.server.ability.AbilitySection;
import io.github.FireTamer81.GeoPlayerModelTest.server.config.ConfigHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.EntityHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.effects.EntitySolarBeam;
import io.github.FireTamer81.GeoPlayerModelTest.server.potion.EffectHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;

public class SolarBeamAbility extends Ability {
    protected EntitySolarBeam solarBeam;

    public SolarBeamAbility(AbilityType<SolarBeamAbility> abilityType, LivingEntity user) {
        super(abilityType, user, new AbilitySection[] {
                new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.STARTUP, 20),
                new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.ACTIVE, 55),
                new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.RECOVERY, 20)
        });
    }

    @Override
    public void start() {
        super.start();
        LivingEntity user = getUser();
        if (!getUser().world.isRemote()) {
            EntitySolarBeam solarBeam = new EntitySolarBeam(EntityHandler.SOLAR_BEAM.get(), user.world, user, user.getPosX(), user.getPosY() + 1.2f, user.getPosZ(), (float) ((user.rotationYawHead + 90) * Math.PI / 180), (float) (-user.rotationPitch * Math.PI / 180), 55);
            solarBeam.setHasPlayer(true);
            user.world.addEntity(solarBeam);
            user.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 80, 2, false, false));
            EffectInstance sunsBlessingInstance = user.getActivePotionEffect(EffectHandler.SUNS_BLESSING);
            if (sunsBlessingInstance != null) {
                int duration = sunsBlessingInstance.getDuration();
                user.removePotionEffect(EffectHandler.SUNS_BLESSING);
                int solarBeamCost = ConfigHandler.COMMON.TOOLS_AND_ABILITIES.SUNS_BLESSING.solarBeamCost.get() * 60 * 20;
                if (duration - solarBeamCost > 0) {
                    user.addPotionEffect(new EffectInstance(EffectHandler.SUNS_BLESSING, duration - solarBeamCost, 0, false, false));
                }
            }

            this.solarBeam = solarBeam;
        }
        else {
            heldItemMainHandVisualOverride = ItemStack.EMPTY;
            heldItemOffHandVisualOverride = ItemStack.EMPTY;
            firstPersonOffHandDisplay = HandDisplay.FORCE_RENDER;
            firstPersonMainHandDisplay = HandDisplay.FORCE_RENDER;
        }
        playAnimation("solar_beam_charge", false);
    }

    @Override
    public void end() {
        super.end();
        if (solarBeam != null) solarBeam.remove();
    }

    @Override
    public boolean canUse() {
        if (getUser() instanceof PlayerEntity && !((PlayerEntity)getUser()).inventory.getCurrentItem().isEmpty()) return false;
        return getUser().isPotionActive(EffectHandler.SUNS_BLESSING) && super.canUse();
    }
}