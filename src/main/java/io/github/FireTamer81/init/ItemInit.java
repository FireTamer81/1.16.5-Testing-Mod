package io.github.FireTamer81.init;

import io.github.FireTamer81.TestModMain;
import io.github.FireTamer81.common.items.PolisherItem;
import io.github.FireTamer81.common.items.RepairItem;
import io.github.FireTamer81.common.items.DamageItem;
import io.github.FireTamer81.common.items.WedgeItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TestModMain.MOD_ID);


    public static final RegistryObject<Item> POLISHER_ITEM = ITEMS.register("polisher_item",
            () -> new PolisherItem(new Item.Properties()));

    public static final RegistryObject<Item> REPAIR_ITEM = ITEMS.register("repair_item",
            () -> new RepairItem(new Item.Properties()));

    public static final RegistryObject<Item> DAMAGE_ITEM = ITEMS.register("damage_item",
            () -> new DamageItem(new Item.Properties()));

    public static final RegistryObject<Item> WEDGE_ITEM = ITEMS.register("wedge_item",
            () -> new WedgeItem(new Item.Properties()));
}
