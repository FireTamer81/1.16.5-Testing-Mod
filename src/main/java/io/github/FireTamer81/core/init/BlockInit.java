package io.github.FireTamer81.core.init;

import io.github.FireTamer81.TestModMain;
import io.github.FireTamer81.common.blocks.FacedPortalBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber(modid = TestModMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlockInit
{
    /**
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister
            .create(ForgeRegistries.BLOCKS, TestModMain.MOD_ID);

    //public static final RegistryObject<Block> START_TEST_BLOCK = BLOCKS.register("multi_block_core",
    //        () -> new Block(AbstractBlock.Properties.of(Material.STONE)));


    public static final RegistryObject<Block> FACED_PORTAL_BLOCK = BLOCKS.register("faced_portal_block",
            () -> new FacedPortalBlock(AbstractBlock.Properties.of(Material.STONE)));
    **/

    /**
     * RegistryEvent Stuff (This takes the place of DeferredRegistry so everything plays nicer with each other [World Gen is the main issue though])
     **/
    public static Set<Block> BLOCKS = new HashSet<>();


    private static Block registerBlock(String name, Block block)
    {
        ResourceLocation r1 = TestModMain.loc(name);
        block.setRegistryName(r1);
        BLOCKS.add(block);
        return block;
    }


    @SubscribeEvent
    public static void blockEventRegister(RegistryEvent.Register<Block> event)
    {
        event.getRegistry().registerAll(BLOCKS.toArray(new Block[0]));
    }



    /**
     * Blocks
     **/
    public static final Block FACED_PORTAL_BLOCK = registerBlock("faced_portal_block",
            new FacedPortalBlock(AbstractBlock.Properties.copy(Blocks.STONE))); //.copy(Blocks.IRON_ORE)));
}
