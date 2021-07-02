package io.github.FireTamer81.events;

import io.github.FireTamer81.TestModMain;
import io.github.FireTamer81.init.BlockInit;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@SuppressWarnings("deprecation")
@EventBusSubscriber(modid = TestModMain.MOD_ID, bus = Bus.FORGE)
public class PlayerEvents 
{
	//The only weird thing about this event I have done is that you cannot just click any side of the Namek Dirt/Grass and it function, it has to be the top unlike the vanilla functionality. 
	@SubscribeEvent
	public static void onHoeUse(UseHoeEvent event) {
		RayTraceResult lookingAt = Minecraft.getInstance().hitResult;
		if (lookingAt != null && lookingAt.getType() == RayTraceResult.Type.BLOCK) {
			double x = lookingAt.getLocation().x();
			double y = lookingAt.getLocation().y();
			double z = lookingAt.getLocation().z();
			
			BlockPos pos = new BlockPos(x, y, z).below();
			
			PlayerEntity player = event.getPlayer();
			World world = player.getCommandSenderWorld();
			BlockState state = world.getBlockState(pos);
			
			if (state == BlockInit.NAMEK_DIRT.defaultBlockState()) {	
				world.playSound(player, pos, SoundEvents.HOE_TILL, SoundCategory.BLOCKS, 1, 1);
				world.setBlockAndUpdate(pos, BlockInit.TILLED_NAMEK_DIRT.defaultBlockState());
			}
			
			if (state == BlockInit.NAMEK_GRASS_BLOCK.defaultBlockState()) {
				world.playSound(player, pos, SoundEvents.HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
				world.setBlockAndUpdate(pos, BlockInit.TILLED_NAMEK_DIRT.defaultBlockState());
			}
		}
	}
}
