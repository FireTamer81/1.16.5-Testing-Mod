package io.github.FireTamer81.init;

import io.github.FireTamer81.TestModMain;
import io.github.FireTamer81.common.items.PolisherItem;
import io.github.FireTamer81.common.items.ScraperItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TestModMain.MOD_ID);


    public static final RegistryObject<Item> POLISHER = ITEMS.register("polisher_item",
            () -> new PolisherItem(new Item.Properties()));

    public static final RegistryObject<Item> SCRAPER = ITEMS.register("scraper_item",
            () -> new ScraperItem(new Item.Properties()));
}
