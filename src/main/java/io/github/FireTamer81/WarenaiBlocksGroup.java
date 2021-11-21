package io.github.FireTamer81;

import io.github.FireTamer81.init.ItemInit;
import io.github.FireTamer81.init.WarenaiBlocksInit;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class WarenaiBlocksGroup extends ItemGroup
{
    public WarenaiBlocksGroup(String label) {
        super(label);
    }

    @Override
    public ItemStack makeIcon() { return WarenaiBlocksInit.WARENAI_BLOCK_BLACK.get().asItem().getDefaultInstance(); }

    //This is used to apply a specific order to your creative tab based on what you enter first.
    @Override
    public void fillItemList(NonNullList<ItemStack> items)
    {
        items.add(ItemInit.DAMAGE_ITEM.get().getDefaultInstance());
        items.add(ItemInit.REPAIR_ITEM.get().getDefaultInstance());
        items.add(ItemInit.POLISHER_ITEM.get().getDefaultInstance());
        items.add(ItemInit.WEDGE_ITEM.get().getDefaultInstance());

        /**
         * Warenai Blocks
         **/
        //Full Blocks
        items.add(WarenaiBlocksInit.WARENAI_BLOCK_BLACK.get().asItem().getDefaultInstance());

        //Stairs
        items.add(WarenaiBlocksInit.WARENAI_BLOCK_BLACK_STAIRS.get().asItem().getDefaultInstance());

        //Slabs
        items.add(WarenaiBlocksInit.WARENAI_BLOCK_BLACK_SLAB.get().asItem().getDefaultInstance());

        //Fences
        items.add(WarenaiBlocksInit.WARENAI_BLOCK_BLACK_FENCE.get().asItem().getDefaultInstance());

        //Walls
        items.add(WarenaiBlocksInit.WARENAI_BLOCK_BLACK_WALL.get().asItem().getDefaultInstance());
    }
}