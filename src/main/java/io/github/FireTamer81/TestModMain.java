package io.github.FireTamer81;

import com.matyrobbrt.lib.ClientSetup;
import com.matyrobbrt.lib.ModSetup;
import com.matyrobbrt.lib.registry.annotation.AnnotationProcessor;
import io.github.FireTamer81.api.TestMod_AnnotationProcessor;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.Optional;
import java.util.function.Supplier;

@Mod("testingmod")
public class TestModMain extends ModSetup
{
	//Identifiers
	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MOD_ID = "testingmod";

	public static final TestMod_AnnotationProcessor ANNOTATION_PROCESSOR = new TestMod_AnnotationProcessor(MOD_ID);

	public TestModMain() {
		super(MOD_ID);

		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		
		//bus.addListener(this::setup);
		//bus.addListener(this::clientSetup);


		//ANNOTATION_PROCESSOR.setAutoBlockItemTab(block -> MAIN_GROUP);
		
     
		//Register ourselves for server and other game events we are interested in
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public AnnotationProcessor annotationProcessor() {
		return ANNOTATION_PROCESSOR;
	}
	
	public static ResourceLocation loc(String name) 
	{
		return new ResourceLocation(MOD_ID, name);
	}
	
	public void setup(FMLCommonSetupEvent event) {}

	@Override
	public Optional<Supplier<ClientSetup>> clientSetup() {
		return Optional.of(() -> new ClientSetup(modBus));
	}
}
