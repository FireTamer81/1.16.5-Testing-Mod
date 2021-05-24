package io.github.FireTamer81;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockInit 
{
	public static DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, TestMod_Main.MOD_ID);
	
	
	public static final RegistryObject<FlowingFluidBlock> NAMEK_FLUID_BLOCK = BLOCKS.register("namek_fluid", 
			() -> new FlowingFluidBlock(FluidInit.NAMEK_FLUID_SOURCE, AbstractBlock.Properties.of(Material.WATER)
					.noCollission()
					.strength(100f)
					.noDrops()));
	
	
}
