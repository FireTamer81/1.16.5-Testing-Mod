package io.github.FireTamer81.common.tileEntities;

import io.github.FireTamer81.common.blocks.*;
import io.github.FireTamer81.init.TileEntityTypesInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;

public class StrongBlockTileEntity extends TileEntity implements ITickableTileEntity
{
    /**
     * This is just the initial value. If you look at the "polishBlock" method you will see that the absolute maximum is 3000
     * You can just tweak the values and such how you want, you can even set the value below to any starting number you choose
     */


    private int strongBlockHealth = 2900;

    public StrongBlockTileEntity() {
        super(TileEntityTypesInit.STRONGBLOCK_TILE.get());
    }


    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~**/


    public void settingBlockValue(int setValueTo) {
        Block thisBlock = this.level.getBlockState(this.worldPosition).getBlock();
        BlockState thisBlockState = this.level.getBlockState(this.worldPosition);

        if (thisBlock instanceof WarenaiBlock) {
            this.level.setBlock(this.worldPosition, thisBlockState.setValue(WarenaiBlock.CRACKED_DIRTY_CLEAN_POLISHED, setValueTo), 3);
        }
        if (thisBlock instanceof WarenaiBlockStairs) {
            this.level.setBlock(this.worldPosition, thisBlockState.setValue(WarenaiBlockStairs.CRACKED_DIRTY_CLEAN_POLISHED, setValueTo), 3);
        }
        if (thisBlock instanceof WarenaiBlockSlab) {
            this.level.setBlock(this.worldPosition, thisBlockState.setValue(WarenaiBlockSlab.CRACKED_DIRTY_CLEAN_POLISHED, setValueTo), 3);
        }
        if (thisBlock instanceof WarenaiBlockFence) {
            this.level.setBlock(this.worldPosition, thisBlockState.setValue(WarenaiBlockFence.CRACKED_DIRTY_CLEAN_POLISHED, setValueTo), 3);
        }
        if (thisBlock instanceof WarenaiBlockWall) {
            this.level.setBlock(this.worldPosition, thisBlockState.setValue(WarenaiBlockWall.CRACKED_DIRTY_CLEAN_POLISHED, setValueTo), 3);
        }
    }

    @Override
    public void tick() {
        if (!this.level.isClientSide()) {
            int currentHealth = getHealth();

            if (currentHealth >= 2901 && currentHealth <= 3000) {
                settingBlockValue(4);
            }
            if (currentHealth >= 1501 && currentHealth <= 2900) {
                settingBlockValue(3);
            }
            if (currentHealth >= 501 && currentHealth <= 1500) {
                settingBlockValue(2);
            }
            if (currentHealth >= 1 && currentHealth <= 500) {
                settingBlockValue(1);
            }
            if (currentHealth == 0) {
                this.level.setBlockAndUpdate(this.worldPosition, Blocks.AIR.defaultBlockState());
            }
        }
    }

    public int getHealth() { return this.strongBlockHealth; }

    public void polishBlock(int polishAmount) {
        final int maximumHealthValueForPolish = 3000;
        int currentMaxPolishValue = maximumHealthValueForPolish - getHealth();
        int currentHealth = getHealth();

        if (currentHealth >= 2900) {
            if (polishAmount > currentMaxPolishValue) {
                this.strongBlockHealth = maximumHealthValueForPolish;
            } else {
                this.strongBlockHealth = getHealth() + polishAmount;
            }
        }

        this.setChanged();
    }

    public void repairBlock(int repairAmount) {
        final int maximumHealthValueForRepair = 2900;
        int currentMaxRepairValue = maximumHealthValueForRepair - getHealth();

        if (getHealth() >=2901) {
            this.strongBlockHealth = getHealth();
        } else if (repairAmount > currentMaxRepairValue) {
            this.strongBlockHealth = maximumHealthValueForRepair;
        } else {
            this.strongBlockHealth = getHealth() + repairAmount;
        }

        this.setChanged();
    }

    public void damageBlock(int damageAmount) {
        final int minimumHealth = 0;
        int currentMaxDamageValue = getHealth();

        if (damageAmount > currentMaxDamageValue) {
            this.strongBlockHealth = minimumHealth;
        } else {
            this.strongBlockHealth = getHealth() - damageAmount;
        }

        this.setChanged();
    }




    /**
     * Overrides
     */

    @Override
    public CompoundNBT save(CompoundNBT tags) {
        if (tags == null) tags = new CompoundNBT();
        tags.putInt("BlockHealth", this.strongBlockHealth);
        return super.save(tags);
    }

    @Override
    public void load(BlockState state, CompoundNBT tags) {
        if (tags != null && tags.contains("BlockHealth")) this.strongBlockHealth = tags.getInt("BlockHealth");
        super.load(state, tags);
    }
}
