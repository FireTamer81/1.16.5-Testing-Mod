package io.github.FireTamer81.core.tabs;

import io.github.FireTamer81.core.init.BlockInit;
import net.minecraft.block.Blocks;
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
		return Blocks.ANCIENT_DEBRIS.asItem().getDefaultInstance();
	}
	
	//This is used to apply a specific order to your creative tab based on what you enter first.
	@Override
	public void fillItemList(NonNullList<ItemStack> items) {
		
		/**
		* Outlier Blocks
		**/
		//items.add(BlockInit.WARENAI_ORE.asItem().getDefaultInstance());
		
		items.add(BlockInit.FACED_PORTAL_BLOCK.asItem().getDefaultInstance());

	}
}
