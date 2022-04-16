package io.github.FireTamer81.GeoPlayerModelTest.server.ability.abilities;

import io.github.FireTamer81.GeoPlayerModelTest.server.ability.Ability;
import io.github.FireTamer81.GeoPlayerModelTest.server.ability.AbilitySection;
import io.github.FireTamer81.GeoPlayerModelTest.server.ability.AbilityType;
import io.github.FireTamer81.GeoPlayerModelTest.server.config.ConfigHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.EntityHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.effects.EntityIceBreath;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.effects.EntitySolarBeam;
import io.github.FireTamer81.GeoPlayerModelTest.server.item.ItemHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.potion.EffectHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Hand;

public class IceBreathAbility extends Ability {
    protected EntityIceBreath iceBreath;

    public IceBreathAbility(AbilityType<IceBreathAbility> abilityType, LivingEntity user) {
        super(abilityType, user, new AbilitySection[] {
                new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.STARTUP, 6),
                new AbilitySection.AbilitySectionInfinite(AbilitySection.AbilitySectionType.ACTIVE),
                new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.RECOVERY, 6)
        });
    }

    @Override
    public void start() {
        super.start();
        LivingEntity user = getUser();
        if (!getUser().world.isRemote()) {
            EntityIceBreath iceBreath = new EntityIceBreath(EntityHandler.ICE_BREATH.get(), user.world, user);
            iceBreath.setPositionAndRotation(user.getPosX(), user.getPosY() + user.getEyeHeight() - 0.5f, user.getPosZ(), user.rotationYaw, user.rotationPitch);
            user.world.addEntity(iceBreath);
            this.iceBreath = iceBreath;
        }
        playAnimation("ice_breath_start", false);

        if (getUser().getActiveHand() == Hand.MAIN_HAND) {
            heldItemMainHandVisualOverride = getUser().getHeldItemMainhand();
            heldItemOffHandVisualOverride = ItemStack.EMPTY;
            firstPersonOffHandDisplay = Ability.HandDisplay.DONT_RENDER;
        }
        else {
            heldItemOffHandVisualOverride = getUser().getHeldItemOffhand();
            heldItemMainHandVisualOverride = ItemStack.EMPTY;
            firstPersonMainHandDisplay = Ability.HandDisplay.DONT_RENDER;
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (getCurrentSection().sectionType != AbilitySection.AbilitySectionType.RECOVERY && !checkIceCrystal()) jumpToSection(2);
    }

    @Override
    public void end() {
        super.end();
        if (iceBreath != null) iceBreath.remove();
    }

    private boolean checkIceCrystal() {
        ItemStack stack = getUser().getActiveItemStack();
        if (getTicksInUse() <= 1) return true;
        if (stack.getItem() != ItemHandler.ICE_CRYSTAL) return false;
        return stack.getDamage() + 5 < stack.getMaxDamage() || ConfigHandler.COMMON.TOOLS_AND_ABILITIES.ICE_CRYSTAL.breakable.get();
    }

    @Override
    public boolean canUse() {
        return checkIceCrystal() && super.canUse();
    }

    @Override
    protected void beginSection(AbilitySection section) {
        super.beginSection(section);
        if (section.sectionType == AbilitySection.AbilitySectionType.STARTUP) {
            playAnimation("ice_breath_start", false);
        }
        else if (section.sectionType == AbilitySection.AbilitySectionType.ACTIVE) {
            playAnimation("ice_breath_loop", true);
        }
        else if (section.sectionType == AbilitySection.AbilitySectionType.RECOVERY) {
            playAnimation("ice_breath_end", false);
        }
    }
}