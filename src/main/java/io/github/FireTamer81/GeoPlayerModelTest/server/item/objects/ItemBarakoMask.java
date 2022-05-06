package io.github.FireTamer81.GeoPlayerModelTest.server.item.objects;

import io.github.FireTamer81.GeoPlayerModelTest.client.model.armour.SolVisageModel;
import io.github.FireTamer81.GeoPlayerModelTest.server.config.ConfigHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.item.ItemHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.item.TestArmorItem;
import io.github.FireTamer81.TestModMain;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class ItemBarakoMask extends TestArmorItem implements BarakoaMask {
    private static final SolVisageMaterial SOL_VISAGE_MATERIAL = new SolVisageMaterial();

    public ItemBarakoMask(Item.Properties properties) {
        super(SOL_VISAGE_MATERIAL, EquipmentSlotType.HEAD, properties);
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        if (ConfigHandler.COMMON.TOOLS_AND_ABILITIES.SOL_VISAGE.breakable.get()) return super.getIsRepairable(toRepair, repair);
        return false;
    }

    @Override
    public boolean isEnchantable(ItemStack p_77616_1_) {
        return true;
    }

    @Override
    public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer) {
        return true;
    }

    @Override
    public boolean isDamageable() {
        return ConfigHandler.COMMON.TOOLS_AND_ABILITIES.SOL_VISAGE.breakable.get();
    }

    @Override
    public int getDamage(ItemStack stack) {
        return ConfigHandler.COMMON.TOOLS_AND_ABILITIES.SOL_VISAGE.breakable.get() ? super.getDamage(stack): 0;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return ConfigHandler.COMMON.TOOLS_AND_ABILITIES.SOL_VISAGE.breakable.get() ? super.getDamage(stack): 0;
    }

    @Override
    public void setDamage(ItemStack stack, int damage) {
        if (ConfigHandler.COMMON.TOOLS_AND_ABILITIES.SOL_VISAGE.breakable.get()) super.setDamage(stack, damage);
    }

    @OnlyIn(Dist.CLIENT)
    @Nullable
    @Override
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default) {
        SolVisageModel<?> model = TestModMain.PROXY.getSolVisageModel();
        model.bipedHeadwear.showModel = armorSlot == EquipmentSlotType.HEAD;

        if (_default != null) {
            model.isChild = _default.isChild;
            model.isSneak = _default.isSneak;
            model.isSitting = _default.isSitting;
            model.rightArmPose = _default.rightArmPose;
            model.leftArmPose = _default.leftArmPose;
        }

        return (A) model;
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        return new ResourceLocation(TestModMain.MODID, "textures/item/barako_mask.png").toString();
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent(getTranslationKey() + ".text.0").setStyle(ItemHandler.TOOLTIP_STYLE));
        tooltip.add(new TranslationTextComponent(getTranslationKey() + ".text.1").setStyle(ItemHandler.TOOLTIP_STYLE));
        tooltip.add(new TranslationTextComponent(getTranslationKey() + ".text.2").setStyle(ItemHandler.TOOLTIP_STYLE));
    }

    @Override
    public ConfigHandler.ArmorConfig getConfig() {
        return ConfigHandler.COMMON.TOOLS_AND_ABILITIES.SOL_VISAGE.armorConfig;
    }

    private static class SolVisageMaterial implements IArmorMaterial {

        @Override
        public int getDurability(EquipmentSlotType equipmentSlotType) {
            return ArmorMaterial.GOLD.getDamageReductionAmount(equipmentSlotType);
        }

        @Override
        public int getDamageReductionAmount(EquipmentSlotType equipmentSlotType) {
            return ConfigHandler.COMMON.TOOLS_AND_ABILITIES.SOL_VISAGE.armorConfig.damageReduction.get();
        }

        @Override
        public int getEnchantability() {
            return ArmorMaterial.GOLD.getEnchantability();
        }

        @Override
        public SoundEvent getSoundEvent() {
            return ArmorMaterial.GOLD.getSoundEvent();
        }

        @Override
        public Ingredient getRepairMaterial() {
            return ArmorMaterial.GOLD.getRepairMaterial();
        }

        @Override
        public String getName() {
            return "sol_visage";
        }

        @Override
        public float getToughness() {
            return ConfigHandler.COMMON.TOOLS_AND_ABILITIES.SOL_VISAGE.armorConfig.toughness.get().floatValue();
        }

        @Override
        public float getKnockbackResistance() {
            return ArmorMaterial.GOLD.getKnockbackResistance();
        }
    }
}
