package io.github.FireTamer81.fullBlock;

import io.github.FireTamer81.Registration;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.client.model.data.ModelProperty;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class BakedModelFullBlock_Tile extends TileEntity implements ITickableTileEntity {
    public static final ModelProperty<Integer> TEXTURE = new ModelProperty<>();

    private int textureIndex = 0;


    /******************************************************************************************************************/
    // Main Methods/Constructor
    /******************************************************************************************************************/
    public BakedModelFullBlock_Tile() {
        super(Registration.BAKED_MODEL_FULL_BLOCK_TILE.get());
    }



    @Override
    public void tick() {
        //int currentTextureIndex = this.getLevel().getBlockState(this.worldPosition).getValue(BakedModelFullBlock.TEXTURE_INDEX);
        //setTextureIndex(currentTextureIndex);
    }



    /******************************************************************************************************************/
    // Model Property Stuff
    /******************************************************************************************************************/
    public Integer getTextureIndex() { return this.textureIndex; }

    public void setTextureIndex(int newTextureIndex) {
        this.textureIndex = newTextureIndex;
        this.setChanged();
        //level.markAndNotifyBlock(worldPosition, null, this.getBlockState(), this.getBlockState(), Constants.BlockFlags.BLOCK_UPDATE, Constants.BlockFlags.NOTIFY_NEIGHBORS);
    }


    @Nonnull
    @Override
    public IModelData getModelData() {
        return new ModelDataMap.Builder()
                .withInitial(TEXTURE, textureIndex)
                .build();
    }

    /**
     * Overrides
     */

    @Override
    public CompoundNBT save(CompoundNBT tags) {
        if (tags == null) tags = new CompoundNBT();
        tags.putInt("TextureIndex", textureIndex);
        return super.save(tags);
    }

    @Override
    public void load(BlockState state, CompoundNBT tags) {
        if (tags != null && tags.contains("TextureIndex")) textureIndex = tags.getInt("TextureIndex");
        super.load(state, tags);
    }
}
