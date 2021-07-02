package io.github.FireTamer81.events;

import io.github.FireTamer81.TestModMain;
import io.github.FireTamer81.init.BlockInit;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.IForgeRegistry;


@EventBusSubscriber(modid = TestModMain.MOD_ID, bus = Bus.MOD)
public class BlockItemRegistration 
{
	@SubscribeEvent
	public static void onRegisterItems(final RegistryEvent.Register<Item> event) 
	{
		final IForgeRegistry<Item> registry = event.getRegistry();
		
		BlockInit.BLOCKS.forEach(block -> 
		{
			if(registry.containsKey(block.getRegistryName())) return;
			
			final Item.Properties properties = new Item.Properties();
			final BlockItem blockItem = new BlockItem(block, properties);
			
			blockItem.setRegistryName(block.getRegistryName());
			registry.register(blockItem);
		});
	}
}