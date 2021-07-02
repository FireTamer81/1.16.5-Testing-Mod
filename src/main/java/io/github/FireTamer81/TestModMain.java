package io.github.FireTamer81;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.github.FireTamer81.init.BlockInit;
import io.github.FireTamer81.init.FluidInit;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.ItemGroup;
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
				FluidInit.ITEMS,
				FluidInit.BLOCKS,
				FluidInit.FLUIDS
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

	
	public void clientSetup(FMLClientSetupEvent event) 
	{
		RenderTypeLookup.setRenderLayer(BlockInit.SHORT_NAMEK_GRASS, RenderType.cutoutMipped());
		RenderTypeLookup.setRenderLayer(BlockInit.TALL_NAMEK_GRASS, RenderType.cutoutMipped());
		
		RenderTypeLookup.setRenderLayer(FluidInit.NAMEK_FLUID_SOURCE.get(), RenderType.translucent());
		RenderTypeLookup.setRenderLayer(FluidInit.NAMEK_FLUID_FLOWING.get(), RenderType.translucent());
		RenderTypeLookup.setRenderLayer(FluidInit.NAMEK_FLUID_BLOCK.get(), RenderType.translucent());
	}
}
