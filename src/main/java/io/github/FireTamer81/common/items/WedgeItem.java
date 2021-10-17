package io.github.FireTamer81.common.items;

import io.github.FireTamer81.common.blocks.*;
import io.github.FireTamer81.common.tileEntities.StrongBlockTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nullable;
import java.util.List;

public class WedgeItem extends Item
{
    public WedgeItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType useOn(ItemUseContext context) {
        World worldIn = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        Block thisBlock = worldIn.getBlockState(blockPos).getBlock();
        IWorld iWorld = context.getLevel();

        if (thisBlock instanceof WarenaiBlock ||
                thisBlock instanceof WarenaiBlockStairs ||
                thisBlock instanceof WarenaiBlockSlab ||
                thisBlock instanceof WarenaiBlockFence ||
                thisBlock instanceof WarenaiBlockWall)
        {
            if (!worldIn.isClientSide) {
                iWorld.destroyBlock(blockPos, true);

                return ActionResultType.SUCCESS;
            } else {
                return ActionResultType.FAIL;
            }
        } else {
            return ActionResultType.FAIL;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack itemStack, @Nullable World world, List<ITextComponent> textComponent, ITooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, world, textComponent, tooltipFlag);

        //TranslationTextComponent translatableText = new TranslationTextComponent("tooltip.this_item_tooltip.test_tooltip");
        StringTextComponent mainTooltip = new StringTextComponent("This wedge is made to separate Warenai Blocks from each other to be picked up");
        StringTextComponent shiftingTooltip = new StringTextComponent("Warenai Blocks are much to strong to break and pick up like most blocks, not that you'd want to try either. That would probably take as long as bedrock");

        textComponent.add(mainTooltip);
        textComponent.add(new StringTextComponent("Hold Left-Shift for more Info"));

        if (InputMappings.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_LEFT_SHIFT)) {
            textComponent.add(shiftingTooltip);
        }
    }
}
