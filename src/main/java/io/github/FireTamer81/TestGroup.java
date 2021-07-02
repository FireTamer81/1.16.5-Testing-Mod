package io.github.FireTamer81;

import io.github.FireTamer81.init.BlockInit;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class TestGroup extends ItemGroup 
{
	public TestGroup(String label) {
		super(label);
	}
	
	@Override
	public ItemStack makeIcon() {
		return BlockInit.NAMEK_DIRT.asItem().getDefaultInstance();
	}
	
	//This is used to apply a specific order to your creative tab based on what you enter first.
	@Override
	public void fillItemList(NonNullList<ItemStack> items) 
	{
		items.add(BlockInit.DIRTY_STONE.asItem().getDefaultInstance());
		
		items.add(BlockInit.AJISA_BUSH.asItem().getDefaultInstance());
		items.add(BlockInit.NAMEK_DIRT.asItem().getDefaultInstance());
		items.add(BlockInit.NAMEK_GRASS_BLOCK.asItem().getDefaultInstance());
		items.add(BlockInit.NAMEK_LEAVES.asItem().getDefaultInstance());
		items.add(BlockInit.NAMEK_LOG.asItem().getDefaultInstance());
		items.add(BlockInit.SHORT_NAMEK_GRASS.asItem().getDefaultInstance());
		items.add(BlockInit.TALL_NAMEK_GRASS.asItem().getDefaultInstance());
		items.add(BlockInit.TILLED_NAMEK_DIRT.asItem().getDefaultInstance());
	}
}
