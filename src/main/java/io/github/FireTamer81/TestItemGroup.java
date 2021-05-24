package io.github.FireTamer81;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class TestItemGroup 
{
	public static final ItemGroup GROUP = new ItemGroup("test_group") 
	{
		@Override
		public ItemStack makeIcon() 
		{
			return new ItemStack(ItemInit.NAMEK_FLUID_BUCKET.get());
		}
	};
}
