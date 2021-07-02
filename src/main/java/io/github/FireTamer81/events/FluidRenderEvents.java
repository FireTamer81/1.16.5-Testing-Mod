package io.github.FireTamer81.events;


import io.github.FireTamer81.TestModMain;
import io.github.FireTamer81.init.FluidInit;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = TestModMain.MOD_ID, bus = Bus.FORGE)
public class FluidRenderEvents 
{

	@SubscribeEvent
	public static void greenFogInNamekWater(EntityViewRenderEvent.FogColors event) 
	{
		PlayerEntity player = Minecraft.getInstance().player;
		double eyeHeight = player.getEyeY() - 1 / 9d;
		FluidState fluidstate = player.level.getFluidState(new BlockPos(player.getX(), eyeHeight, player.getZ()));
		
		if (fluidstate.getType() == FluidInit.NAMEK_FLUID_FLOWING.get() || fluidstate.getType() == FluidInit.NAMEK_FLUID_SOURCE.get()) 
		{
			event.setBlue(0.09F);
			event.setGreen(0.45F); //As it stands, what I have done is match the ratios shown in the fluid color to the fog color. 255 for the fluid color being 1.0F for the Fog Color.
			event.setRed(0);
		}
	}
	
	
	
	@SubscribeEvent
	public static void cancelVanillaWaterOverlay(RenderBlockOverlayEvent event) 
	{
		PlayerEntity player = Minecraft.getInstance().player;
		double eyeHeight = player.getEyeY() - 1 / 9d;
		FluidState fluidstate = player.level.getFluidState(new BlockPos(player.getX(), eyeHeight, player.getZ()));
		
		if (fluidstate.getType() == FluidInit.NAMEK_FLUID_FLOWING.get() || fluidstate.getType() == FluidInit.NAMEK_FLUID_SOURCE.get()) 
		{
			if (event.isCancelable()) 
			{
				event.setCanceled(true);
			}
		}
	}
	
}
