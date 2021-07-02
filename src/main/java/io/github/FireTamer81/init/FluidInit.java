package io.github.FireTamer81.init;

import java.awt.Color;

import io.github.FireTamer81.TestModMain;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FluidInit 
{
	//Deferred Registries
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TestModMain.MOD_ID);
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, TestModMain.MOD_ID);
	public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, TestModMain.MOD_ID);
	
	
	
	
	
	//Resource Locations
	private static ResourceLocation stillNamekWaterTexture = new ResourceLocation(TestModMain.MOD_ID, "fluids/namek_fluid_still");
	private static ResourceLocation flowingNamekWaterTexture = new ResourceLocation(TestModMain.MOD_ID, "fluids/namek_fluid_flow");
	
	
	
	
	
	//Namek Water Bucket
	public static final RegistryObject<Item> NAMEK_WATER_BUCKET = ITEMS.register("namek_water_bucket", 
			() -> new BucketItem(FluidInit.NAMEK_FLUID_SOURCE, (
					new Item.Properties())
					.craftRemainder(Items.BUCKET)
					.stacksTo(1)));
	
	//Namek Fluid Block
	public static final RegistryObject<FlowingFluidBlock> NAMEK_FLUID_BLOCK = BLOCKS.register("namek_fluid_block", 
			() -> new FlowingFluidBlock(FluidInit.NAMEK_FLUID_SOURCE, AbstractBlock.Properties.of(Material.WATER)
					.noCollission()
					.strength(100f)
					.noDrops()));
	
	
	
	
	
	//Namek Fluid Source and Flowing Objects
	public static final RegistryObject<FlowingFluid> NAMEK_FLUID_SOURCE = FLUIDS.register("namek_fluid_source", () -> new ForgeFlowingFluid.Source(FluidInit.NAMEK_FLUID_PROPERTIES));
	public static final RegistryObject<FlowingFluid> NAMEK_FLUID_FLOWING = FLUIDS.register("namek_fluid_flowing", () -> new ForgeFlowingFluid.Flowing(FluidInit.NAMEK_FLUID_PROPERTIES));
	
	
	
	
	
	//Fluid Properties
	public static final ForgeFlowingFluid.Properties NAMEK_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(NAMEK_FLUID_SOURCE, NAMEK_FLUID_FLOWING, 
			FluidAttributes.builder(stillNamekWaterTexture, flowingNamekWaterTexture)
				.sound(SoundEvents.WATER_AMBIENT)
				.color(new Color(25, 115, 2, 225).getRGB()) //(new Color(66, 191, 34, 200).getRGB())
			)
			.bucket(FluidInit.NAMEK_WATER_BUCKET)
			.block(FluidInit.NAMEK_FLUID_BLOCK)
			.canMultiply();

}
