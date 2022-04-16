package io.github.FireTamer81.GeoPlayerModelTest.server.item;

import io.github.FireTamer81.GeoPlayerModelTest.server.ability.Ability;
import io.github.FireTamer81.GeoPlayerModelTest.server.ability.AbilityHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.capability.AbilityCapability;
import io.github.FireTamer81.GeoPlayerModelTest.server.capability.CapabilityHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.capability.PlayerCapability;
import io.github.FireTamer81.GeoPlayerModelTest.server.config.ConfigHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.EntityHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.effects.EntityIceBall;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.effects.EntityIceBreath;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemIceCrystal extends Item {
    public ItemIceCrystal(Item.Properties properties) {
        super(properties);
    }

    @Override
    public boolean isDamageable() {
        return true;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        AbilityCapability.IAbilityCapability abilityCapability = AbilityHandler.INSTANCE.getAbilityCapability(playerIn);
        if (abilityCapability != null) {
            playerIn.setActiveHand(handIn);
            if (stack.getDamage() + 5 < stack.getMaxDamage() || ConfigHandler.COMMON.TOOLS_AND_ABILITIES.ICE_CRYSTAL.breakable.get()) {
                if (!worldIn.isRemote()) AbilityHandler.INSTANCE.sendAbilityMessage(playerIn, AbilityHandler.ICE_BREATH_ABILITY);
                stack.damageItem(5, playerIn, p -> p.sendBreakAnimation(handIn));
                showDurabilityBar(playerIn.getHeldItem(handIn));
                playerIn.setActiveHand(handIn);
                return new ActionResult<ItemStack>(ActionResultType.SUCCESS, playerIn.getHeldItem(handIn));
            } else {
                abilityCapability.getAbilityMap().get(AbilityHandler.ICE_BREATH_ABILITY).end();
            }
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof PlayerEntity) {
            Ability iceBreathAbility = AbilityHandler.INSTANCE.getAbility(entityLiving, AbilityHandler.ICE_BREATH_ABILITY);
            if (iceBreathAbility != null && iceBreathAbility.isUsing()) {
                iceBreathAbility.end();
            }
        }
        super.onPlayerStoppedUsing(stack, worldIn, entityLiving, timeLeft);
    }

    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return ConfigHandler.COMMON.TOOLS_AND_ABILITIES.ICE_CRYSTAL.durability.get();
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent(getTranslationKey() + ".text.0").setStyle(ItemHandler.TOOLTIP_STYLE));
        if (!ConfigHandler.COMMON.TOOLS_AND_ABILITIES.ICE_CRYSTAL.breakable.get()) {
            tooltip.add(new TranslationTextComponent(getTranslationKey() + ".text.1").setStyle(ItemHandler.TOOLTIP_STYLE));
        }
    }
}
