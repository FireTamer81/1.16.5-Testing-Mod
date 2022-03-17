package io.github.FireTamer81;

//import io.github.FireTamer81.EpicFightAnimationStuff.AnimationManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("testingmod")
public class TestModMain 
{
	//Identifiers
	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MOD_ID = "testingmod";
	public static TestModMain instance;
	//public final AnimationManager animationManager;

	public static TestModMain getInstance() { return instance; };
	
	public TestModMain() 
	{
		//this.animationManager = new AnimationManager();
		//instance = this;




		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

		bus.addListener(this::clientSetup);
		bus.addListener(this::commonSetup);

		//Registries
		//DeferredRegister<?>[] registers = {};
		//for (DeferredRegister<?> register : registers) { register.register(bus); }

		//Register ourselves for server and other game events we are interested in
		MinecraftForge.EVENT_BUS.register(this);
	}

	public void commonSetup(FMLCommonSetupEvent event) {}

	public void clientSetup(FMLClientSetupEvent event) {}
}
