package io.github.FireTamer81;

import io.github.FireTamer81.blockColorignStuff.BlockColors;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;

@Mod("testingmod")
public class TestModMain 
{
	//Identifiers
	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MOD_ID = "testingmod";

	public TestModMain() 
	{
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		
		//bus.addListener(this::setup);
		bus.addListener(this::clientSetup);
     
		Registration.init();

		FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetup::init);
     
		//Register ourselves for server and other game events we are interested in
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	
	public static ResourceLocation loc(String name) 
	{
		return new ResourceLocation(MOD_ID, name);
	}
	
	public void setup(FMLCommonSetupEvent event) {}

	public void clientSetup(FMLClientSetupEvent event) {
		BlockColors.registerBlockColors();
	}



	public static class TestModItemGroup extends ItemGroup {

		public static final TestModItemGroup TEST_GROUP = new TestModItemGroup(ItemGroup.TABS.length, "test_group");
		private TestModItemGroup(int index, String label) { super(index, label); }

		@Override
		public ItemStack makeIcon() {
			return new ItemStack(Registration.BAKED_MODEL_FULL_BLOCK_ITEM.get());
		}
	}
}
