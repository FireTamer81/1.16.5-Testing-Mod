package io.github.FireTamer81.GeoPlayerModelTest.server.item;

import net.minecraft.item.Item;

public class ItemTestStructure extends Item {
    public ItemTestStructure(Item.Properties properties) {
        super(properties);
    }


//    @Override
//    public ActionResultType onItemUse(ItemUseContext context) {
////        if (!context.getWorld().isRemote) StructureWroughtnautRoom.generate(context.getWorld(), context.getPos(), context.getWorld().rand, Direction.NORTH);
//        if (!context.getWorld().isRemote && context.getPlayer().isSneaking()) StructureBarakoaVillage.generateHouse(context.getWorld(), context.getWorld().rand, context.getPos(), context.getPlacementHorizontalFacing());
//        else if (!context.getWorld().isRemote) StructureBarakoaVillage.generateSideHouse(context.getWorld(), context.getWorld().rand, context.getPos(), context.getPlacementHorizontalFacing());
//        return ActionResultType.SUCCESS;
//    }
}
