package io.github.FireTamer81;

import io.github.FireTamer81.GeoPlayerModelTest.client.ClientProxy;
import io.github.FireTamer81.GeoPlayerModelTest.client.model.tools.MowzieGeoBuilder;
import io.github.FireTamer81.GeoPlayerModelTest.client.particle.ParticleHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.ServerEventHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.ServerProxy;
import io.github.FireTamer81.GeoPlayerModelTest.server.ability.AbilityCommonEventHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.advancement.AdvancementHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.block.BlockHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.capability.CapabilityHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.creativetab.CreativeTabHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.EntityHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.item.ItemHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.loot.LootTableHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.sound.MMSounds;
import io.github.FireTamer81.GeoPlayerModelTest.server.world.feature.ConfiguredFeatureHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.world.spawn.SpawnHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.world.feature.FeatureHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.example.GeckoLibMod;
import software.bernie.geckolib3.GeckoLib;


@Mod("testingmod")
public class TestModMain 
{
	//Identifiers
	public static final String MODID = "testingmod";
	public static final Logger LOGGER = LogManager.getLogger();
	public static ServerProxy PROXY;

	public static SimpleChannel NETWORK;

	public TestModMain() 
	{
		GeckoLibMod.DISABLE_IN_DEV = true;
		MowzieGeoBuilder.registerGeoBuilder(MODID, new MowzieGeoBuilder());
		GeckoLib.initialize();

		PROXY = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);
		final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		CreativeTabHandler.INSTANCE.onInit();
		EntityHandler.REG.register(bus);
		MMSounds.REG.register(bus);
		BlockHandler.REG.register(bus);
		ParticleHandler.REG.register(bus);
		FeatureHandler.REG.register(bus);

		PROXY.init(bus);
		bus.<FMLCommonSetupEvent>addListener(this::init);
		bus.<FMLLoadCompleteEvent>addListener(this::init);

		MinecraftForge.EVENT_BUS.register(this);
		MinecraftForge.EVENT_BUS.register(new ServerEventHandler());
		MinecraftForge.EVENT_BUS.register(new AbilityCommonEventHandler());
	}

	public void init(final FMLCommonSetupEvent event) {
		CapabilityHandler.register();
		SpawnHandler.registerSpawnPlacementTypes();
		PROXY.initNetwork();
		AdvancementHandler.preInit();
		LootTableHandler.init();

		event.enqueueWork(() -> {
			FeatureHandler.setupStructures();
			ConfiguredFeatureHandler.registerConfiguredStructures();
		});
	}

	private void init(FMLLoadCompleteEvent event) {
		ItemHandler.initializeAttributes();
		ItemHandler.initializeDispenserBehaviors();
		BlockHandler.init();
		final IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		PROXY.onLateInit(bus);
	}

	@SubscribeEvent
	public void onBiomeLoading(BiomeLoadingEvent event) {
		SpawnHandler.onBiomeLoading(event);
		ConfiguredFeatureHandler.onBiomeLoading(event);
	}

	@SubscribeEvent
	public void onWorldLoad(final WorldEvent.Load event) {
		FeatureHandler.addDimensionalSpacing(event);
	}
}
