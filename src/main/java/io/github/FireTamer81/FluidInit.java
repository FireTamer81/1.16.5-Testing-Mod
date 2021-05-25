package io.github.FireTamer81;

import java.awt.Color;

import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FluidInit 
{
	public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, TestMod_Main.MOD_ID);
	
	private static ResourceLocation stillNamekWaterTexture = new ResourceLocation(TestMod_Main.MOD_ID, "fluids/namek_fluid_still");
	private static ResourceLocation flowingNamekWaterTexture = new ResourceLocation(TestMod_Main.MOD_ID, "fluids/namek_fluid_flow");
	
	
	
	public static final RegistryObject<FlowingFluid> NAMEK_FLUID_SOURCE = FLUIDS.register("test_fluid_source", () -> new ForgeFlowingFluid.Source(FluidInit.NAMEK_FLUID_PROPERTIES));
	public static final RegistryObject<FlowingFluid> NAMEK_FLUID_FLOWING = FLUIDS.register("test_fluid_flowing", () -> new ForgeFlowingFluid.Flowing(FluidInit.NAMEK_FLUID_PROPERTIES));
	
	
	
	public static final ForgeFlowingFluid.Properties NAMEK_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(NAMEK_FLUID_SOURCE, NAMEK_FLUID_FLOWING, 
			FluidAttributes.builder(stillNamekWaterTexture, flowingNamekWaterTexture)
				.sound(SoundEvents.WATER_AMBIENT)
				.color(new Color(66, 191, 34, 200).getRGB())
			)
			.bucket(ItemInit.NAMEK_FLUID_BUCKET)
			.block(BlockInit.NAMEK_FLUID_BLOCK)
			.canMultiply();


}
