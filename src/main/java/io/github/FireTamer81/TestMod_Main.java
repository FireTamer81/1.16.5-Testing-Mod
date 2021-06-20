package io.github.FireTamer81;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(TestMod_Main.MOD_ID)
public class TestMod_Main
{
	public static final String MOD_ID = "testingmod";

    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    
    

    public TestMod_Main() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
    	
        bus.addListener(this::setup);
        //bus.addListener(this::clientSetup);
        
        
        
        
        
        
        DeferredRegister<?>[] registers = {
        		
        };
        
        
        
        for (DeferredRegister<?> register : registers) {
        	register.register(bus);
        }
        


        MinecraftForge.EVENT_BUS.register(this);
    }

    
    
    
    
    
    
    
    
    private void setup(FMLCommonSetupEvent event)
    {
        MinecraftForge.EVENT_BUS.register(RegisterCommandEvent.class);
    }
    
    
    
	public void clientSetup(FMLClientSetupEvent event) 
	{
		
	}


}
