package io.github.FireTamer81;

import io.github.FireTamer81.dataGenStuff.dataGenProviders.DBEBlockStates_BlockModels_Provider;
import io.github.FireTamer81.dataGenStuff.dataGenProviders.DBEBlockTagsProvider;
import io.github.FireTamer81.dataGenStuff.dataGenProviders.DBEItemModelsProvider;
import io.github.FireTamer81.init.ItemInit;
import io.github.FireTamer81.init.WarenaiBlocksInit;
import io.github.FireTamer81.item_groups.WarenaiBlocksGroup;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
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


	public static final ItemGroup WARENAI_BLOCKS_GROUP = new WarenaiBlocksGroup("warenai_blocks_group");
	
	
	public TestModMain() 
	{
		IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
		//IEventBus forgeBus = MinecraftForge.EVENT_BUS;


		//Event Listeners... setup and clientSetup are empty right now, just give time
		modBus.addListener(this::setup);

		DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
			modBus.addListener(this::clientSetup);
		});

		modBus.addListener(this::gatherData);
     

		
		//Registries
		DeferredRegister<?>[] registers = {
				WarenaiBlocksInit.BLOCKS,
				ItemInit.ITEMS
		};
		
		for (DeferredRegister<?> register : registers) {
			register.register(modBus);
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

	private void gatherData(GatherDataEvent event)
	{
		DataGenerator gen = event.getGenerator();
		ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

		gen.addProvider(new DBEBlockStates_BlockModels_Provider(gen, existingFileHelper));
		gen.addProvider(new DBEItemModelsProvider(gen, existingFileHelper));

		//The BlockTagsProvider is in a variable because the ItemTagsProvider requires it, so the variable makes sure it loads first or something.
		DBEBlockTagsProvider blockTags = new DBEBlockTagsProvider(gen, existingFileHelper);
		gen.addProvider(blockTags);
		//gen.addProvider(new DBEItemTagsProvider(gen, blockTags, existingFileHelper));
	}
}
