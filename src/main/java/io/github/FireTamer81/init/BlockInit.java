package io.github.FireTamer81.init;

import java.util.HashSet;
import java.util.Set;

import io.github.FireTamer81.TestModMain;
import io.github.FireTamer81.blocks.AjisaBush;
import io.github.FireTamer81.blocks.NamekGrass;
import io.github.FireTamer81.blocks.SpreadableNamekGrass;
import io.github.FireTamer81.blocks.TilledNamekDirt;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TestModMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlockInit 
{
	/**
	* RegistryEvent Stuff (This takes the place of DeferredRegistry so everything plays nicer with each other [World Gen is the main issue though])
	**/
	public static Set<Block> BLOCKS = new HashSet<>();
	
	
	private static Block registerBlock(String name, Block block) 
	{
		ResourceLocation r1 = TestModMain.loc(name);
		block.setRegistryName(r1);
		BLOCKS.add(block);
		return block;
	}
	
	
	@SubscribeEvent
	public static void blockEventRegister(RegistryEvent.Register<Block> event) 
	{
		event.getRegistry().registerAll(BLOCKS.toArray(new Block[0]));
	}
	

	//Dirty Stone (This is a block made in the Testing Mod, it needs to be copied into the actual mod)
	public static final Block DIRTY_STONE = registerBlock("dirty_stone", 
			new Block(AbstractBlock.Properties.of(Material.STONE)
					.strength(1.5F)
					.harvestTool(ToolType.PICKAXE)
					.harvestLevel(1)
					.sound(SoundType.STONE)));
	
	
	
	
	

	
	//Namek Grass Block (Needs Loot table)
	public static final Block NAMEK_GRASS_BLOCK = registerBlock("namek_grass_block", 
			new SpreadableNamekGrass(AbstractBlock.Properties.of(Material.DIRT)
					.strength(0.5f, 0.5F)
					.harvestTool(ToolType.SHOVEL)
					.harvestLevel(0)
					.sound(SoundType.GRASS)
					.randomTicks()));


	//Namek Dirt (Needs Loot table)
	public static final Block NAMEK_DIRT = registerBlock("namek_dirt", 
			new Block(AbstractBlock.Properties.of(Material.DIRT)
					.strength(0.5f, 0.5F)
					.harvestTool(ToolType.SHOVEL)
					.sound(SoundType.GRASS)
					.harvestLevel(0)));


	//Namek Logs (Needs Loot table)
	public static final Block NAMEK_LOG = registerBlock("namek_log",
			new RotatedPillarBlock(AbstractBlock.Properties.of(Material.WOOD)
					.strength(2.0f, 2.0f)
					.harvestTool(ToolType.AXE)
					.sound(SoundType.WOOD)
					.harvestLevel(0)));


	//Namek Leaves (Needs Loot table)
	public static final Block NAMEK_LEAVES = registerBlock("namek_leaves",
			new LeavesBlock(AbstractBlock.Properties.of(Material.LEAVES)
					.strength(0.2F)
					.sound(SoundType.GRASS)
					.randomTicks() 
					.noOcclusion())); //"notSolid()" is the original method, "noOcclusion()" might not be correct.


	//Namek Grass Short (Needs Loot Table)
	public static final Block SHORT_NAMEK_GRASS = registerBlock("short_namek_grass", 
			new NamekGrass(AbstractBlock.Properties.of(Material.REPLACEABLE_PLANT) 
					.noCollission()
					.instabreak()
					.sound(SoundType.GRASS)
					.randomTicks()));


	//Namek Grass Tall (Needs Loot Table)
	public static final Block TALL_NAMEK_GRASS = registerBlock("tall_namek_grass", 
			new NamekGrass(AbstractBlock.Properties.of(Material.REPLACEABLE_PLANT) 
					.noCollission()
					.instabreak()
					.sound(SoundType.GRASS)
					.randomTicks()));


	//Ajisa Bush (Needs a loottable [IRL artificial propagation is done by taking a fully grown bush and splitting it into two smaller ones. Do something like this])
	public static final Block AJISA_BUSH = registerBlock("ajisa_bush",
			new AjisaBush(AbstractBlock.Properties.of(Material.LEAVES)
					.strength(0.2f)
					.sound(SoundType.GRASS)
					.noOcclusion()
					.randomTicks()));


	//Tilled Namek Dirt (Needs LootTable [Just drops dirt])
	public static final Block TILLED_NAMEK_DIRT = registerBlock("tilled_namek_dirt", 
			new TilledNamekDirt(AbstractBlock.Properties.of(Material.DIRT)
					.strength(0.5f, 0.5F)
					.harvestTool(ToolType.SHOVEL)
					.sound(SoundType.GRASS)
					.harvestLevel(0)
					.randomTicks()));


}
