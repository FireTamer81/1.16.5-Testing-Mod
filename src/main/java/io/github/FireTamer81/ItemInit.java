package io.github.FireTamer81;

import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit 
{
	public static DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TestMod_Main.MOD_ID);
	
	
	
	public static final RegistryObject<Item> NAMEK_FLUID_BUCKET = ITEMS.register("namek_water_bucket", 
			() -> new BucketItem(FluidInit.NAMEK_FLUID_SOURCE, (
					new Item.Properties()).tab(TestItemGroup.GROUP)
					.craftRemainder(Items.BUCKET)
					.stacksTo(1)));
}
