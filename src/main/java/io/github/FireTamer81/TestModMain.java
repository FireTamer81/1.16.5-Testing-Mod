package io.github.FireTamer81;

import io.github.FireTamer81.core.init.BlockInit;
import io.github.FireTamer81.core.tabs.TestGroup;
import net.minecraft.item.ItemGroup;
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


	public static final ItemGroup TEST_GROUP = new TestGroup("test_group");
	
	
	public TestModMain() 
	{
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		
		//bus.addListener(this::setup);
		bus.addListener(this::clientSetup);
     
	
		
		//Registries
		DeferredRegister<?>[] registers = {

				
		};
		
		for (DeferredRegister<?> register : registers) {
			register.register(bus);
		}
		
     
		//Register ourselves for server and other game events we are interested in
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	
	public static ResourceLocation loc(String name) 
	{
		return new ResourceLocation(MOD_ID, name);
	}
	
	public void setup(FMLCommonSetupEvent event) {}

	public void clientSetup(FMLClientSetupEvent event) {}
}
