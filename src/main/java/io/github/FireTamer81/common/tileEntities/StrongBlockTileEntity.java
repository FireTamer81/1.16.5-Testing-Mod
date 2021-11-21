package io.github.FireTamer81.common.tileEntities;

import io.github.FireTamer81.common.blocks.*;
import io.github.FireTamer81.common.blocks.properties.WarenaiBlockCondition;
import io.github.FireTamer81.init.TileEntityTypesInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.DestroyBlockProgress;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class StrongBlockTileEntity extends TileEntity implements ITickableTileEntity
{
    /**
     * This is just the initial value. If you look at the "polishBlock" method you will see that the absolute maximum is 3000
     * You can just tweak the values and such how you want, you can even set the value below to any starting number you choose
     */


    private int strongBlockHealth = 3000;

    public StrongBlockTileEntity() {
        super(TileEntityTypesInit.STRONGBLOCK_TILE.get());
    }


    /**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~**/


    public void settingBlockValue(int CDCPValue, int crackedValue, WarenaiBlockCondition condition) {
        Block thisBlock = this.level.getBlockState(this.worldPosition).getBlock();
        BlockState thisBlockState = this.level.getBlockState(this.worldPosition);

        if (thisBlock instanceof WarenaiBlock) {
            this.level.setBlock(this.worldPosition, thisBlockState.setValue(WarenaiBlock.BLOCK_CONDITION, condition), 3);
        }
        if (thisBlock instanceof WarenaiBlockStairs) {
            this.level.setBlock(this.worldPosition, thisBlockState.setValue(WarenaiBlockStairs.CRACKED_DIRTY_CLEAN_POLISHED, CDCPValue).setValue(WarenaiBlockStairs.CRACKED_LEVEL, crackedValue), 3);
        }
        if (thisBlock instanceof WarenaiBlockSlab) {
            this.level.setBlock(this.worldPosition, thisBlockState.setValue(WarenaiBlockSlab.CRACKED_DIRTY_CLEAN_POLISHED, CDCPValue).setValue(WarenaiBlockSlab.CRACKED_LEVEL, crackedValue), 3);
        }
        if (thisBlock instanceof WarenaiBlockFence) {
            this.level.setBlock(this.worldPosition, thisBlockState.setValue(WarenaiBlockFence.CRACKED_DIRTY_CLEAN_POLISHED, CDCPValue).setValue(WarenaiBlockFence.CRACKED_LEVEL, crackedValue), 3);
        }
        if (thisBlock instanceof WarenaiBlockWall) {
            this.level.setBlock(this.worldPosition, thisBlockState.setValue(WarenaiBlockWall.CRACKED_DIRTY_CLEAN_POLISHED, CDCPValue).setValue(WarenaiBlockWall.CRACKED_LEVEL, crackedValue), 3);
        }
    }

    @Override
    public void tick() {
        if (!this.level.isClientSide()) {
            int currentHealth = getHealth();

            if (currentHealth >= 3001 && currentHealth <= 3100) {
                settingBlockValue(4, 0, WarenaiBlockCondition.POLISHED);
            }
            if (currentHealth >= 1601 && currentHealth <= 3000) {
                settingBlockValue(3, 0, WarenaiBlockCondition.NORMAL);
            }
            if (currentHealth >= 801 && currentHealth <= 1600) {
                settingBlockValue(2, 0, WarenaiBlockCondition.SCUFFED);
            }
            if (currentHealth >= 631 && currentHealth <= 800) {
                settingBlockValue(2, 1, WarenaiBlockCondition.CRACKED1);
            }
            if (currentHealth >= 461 && currentHealth <= 630) {
                settingBlockValue(2, 2, WarenaiBlockCondition.CRACKED2);
            }
            if (currentHealth >= 291 && currentHealth <= 460) {
                settingBlockValue(2, 3, WarenaiBlockCondition.CRACKED3);
            }
            if (currentHealth >= 120 && currentHealth <= 290) {
                settingBlockValue(2, 4, WarenaiBlockCondition.CRACKED4);
            }
            if (currentHealth == 0) {
                this.level.setBlockAndUpdate(this.worldPosition, Blocks.AIR.defaultBlockState());
            }
        }
    }

    public int getHealth() { return this.strongBlockHealth; }

    public void polishBlock(int polishAmount) {
        final int maximumHealthValueForPolish = 3100;
        int currentMaxPolishValue = maximumHealthValueForPolish - getHealth();
        int currentHealth = getHealth();

        if (currentHealth >= 3000) {
            if (polishAmount > currentMaxPolishValue) {
                this.strongBlockHealth = maximumHealthValueForPolish;
            } else {
                this.strongBlockHealth = getHealth() + polishAmount;
            }
        }

        this.setChanged();
    }

    public void repairBlock(int repairAmount) {
        final int maximumHealthValueForRepair = 3000;
        int currentMaxRepairValue = maximumHealthValueForRepair - getHealth();

        if (getHealth() >= 3001) {
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
