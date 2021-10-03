package io.github.FireTamer81.common.tileEntities;

import io.github.FireTamer81.init.TileEntityTypesInit;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.IIntArray;

public class StrongBlockTileEntity extends TileEntity
{
    private static int strongBlockHealth;


    public StrongBlockTileEntity(TileEntityType<?> tileEntityType) {
        super(tileEntityType);
    }

    public StrongBlockTileEntity() { this(TileEntityTypesInit.WARENAI_BLOCK_BLACK_STRONGBLOCK_TILE.get()); }


    public static void damageStrongBlock(int damageAmount) {
        int currentBlockHealth = strongBlockHealth;
        strongBlockHealth = currentBlockHealth - damageAmount;

    }

    public void repairStrongBlock(int repairAmount) {
        int currentBlockHealth = strongBlockHealth;
        strongBlockHealth = currentBlockHealth + repairAmount;
    }



    public static int getStrongBlockHealth() { return strongBlockHealth; }

    public static void showBlockHealth() {
        System.out.println("Block Health is: " + getStrongBlockHealth());
    }

    public static void setInitialBlockHealth() { strongBlockHealth = 3000; }







    /**
     * Overrides
     */

    @Override
    public CompoundNBT save(CompoundNBT tags) {
        super.save(tags);
        tags.putInt("BlockHealth", this.strongBlockHealth);
        return tags;
    }

    @Override
    public void load(BlockState state, CompoundNBT tags) {
        super.load(state, tags);
        strongBlockHealth = tags.getInt("BlockHealth");
    }

    




}
