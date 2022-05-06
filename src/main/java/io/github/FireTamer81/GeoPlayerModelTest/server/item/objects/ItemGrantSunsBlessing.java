package io.github.FireTamer81.GeoPlayerModelTest.server.item.objects;

import io.github.FireTamer81.GeoPlayerModelTest.server.config.ConfigHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.item.ItemHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.potion.EffectHandler;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemGrantSunsBlessing extends Item {
    public ItemGrantSunsBlessing(Item.Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand hand) {
        playerIn.addPotionEffect(new EffectInstance(EffectHandler.SUNS_BLESSING, ConfigHandler.COMMON.TOOLS_AND_ABILITIES.SUNS_BLESSING.effectDuration.get() * 60 * 20, 0, false, false));
        return super.onItemRightClick(worldIn, playerIn, hand);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        int effectDuration = ConfigHandler.COMMON.TOOLS_AND_ABILITIES.SUNS_BLESSING.effectDuration.get();
        int solarBeamCost = ConfigHandler.COMMON.TOOLS_AND_ABILITIES.SUNS_BLESSING.solarBeamCost.get();
        tooltip.add(
                new TranslationTextComponent(getTranslationKey() + ".text.0")
                        .appendString(" " + effectDuration + " ")
                        .appendSibling(new TranslationTextComponent(getTranslationKey() + ".text.1")).setStyle(ItemHandler.TOOLTIP_STYLE)
        );
        tooltip.add(new TranslationTextComponent(getTranslationKey() + ".text.2").setStyle(ItemHandler.TOOLTIP_STYLE));
        tooltip.add(
                new TranslationTextComponent(getTranslationKey() + ".text.3")
                        .appendString(" " + solarBeamCost + " ")
                        .appendSibling(new TranslationTextComponent(getTranslationKey() + ".text.4")).setStyle(ItemHandler.TOOLTIP_STYLE)
        );
    }
}
