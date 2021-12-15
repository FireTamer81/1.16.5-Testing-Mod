package io.github.FireTamer81.fullBlock;

import io.github.FireTamer81.Registration;
import io.github.FireTamer81.StrongBlockTextureHelper;
import io.github.FireTamer81.TestModMain;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelDataManager;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.client.model.data.ModelProperty;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BakedModelFullBlock_Tile extends TileEntity implements ITickableTileEntity {
    //Model Properties
    public static final ModelProperty<Integer> TEXTURE = new ModelProperty<>();
    public static final ModelProperty<Boolean> NORTH_VISIBLE = new ModelProperty<>();
    public static final ModelProperty<Boolean> EAST_VISIBLE = new ModelProperty<>();
    public static final ModelProperty<Boolean> SOUTH_VISIBLE = new ModelProperty<>();
    public static final ModelProperty<Boolean> WEST_VISIBLE = new ModelProperty<>();
    public static final ModelProperty<Boolean> UP_VISIBLE = new ModelProperty<>();
    public static final ModelProperty<Boolean> DOWN_VISIBLE = new ModelProperty<>();


    private static Integer texture = 0;
    private Boolean northVisible = true;
    private Boolean eastVisible = true;
    private Boolean southVisible = true;
    private Boolean westVisible = true;
    private Boolean upVisible = true;
    private Boolean downVisible = true;



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



    public static final ResourceLocation UNDERLAY_TEXTURE = StrongBlockTextureHelper.getSmoothWarenaiBlockResourceLocations().get(texture);



    /******************************************************************************************************************/
    // Model Property Stuff
    /******************************************************************************************************************/
    public Integer getTextureIndex() { return this.texture; }

    public void setTextureIndex(int newTextureIndex) {
        //World world = Minecraft.getInstance().level;
        this.texture = newTextureIndex;
        this.setChanged();
        level.sendBlockUpdated(worldPosition, this.getBlockState(), this.getBlockState(), Constants.BlockFlags.BLOCK_UPDATE + Constants.BlockFlags.NOTIFY_NEIGHBORS);
    }


    public void setVisibleSides(Direction dir, boolean isVisible) {
        switch (dir) {
            case DOWN:
                downVisible = isVisible;
                break;
            case UP:
                upVisible = isVisible;
                break;
            case NORTH:
                northVisible = isVisible;
                break;
            case WEST:
                westVisible = isVisible;
                break;
            case SOUTH:
                southVisible = isVisible;
                break;
            case EAST:
                eastVisible = isVisible;
                break;
            default:
                break;
        }
    }

    public List<Direction> getVisibleSides() {
        List<Direction> dir = new ArrayList<>();
        if (northVisible)
            dir.add(Direction.NORTH);
        if (eastVisible)
            dir.add(Direction.EAST);
        if (southVisible)
            dir.add(Direction.SOUTH);
        if (westVisible)
            dir.add(Direction.WEST);
        if (upVisible)
            dir.add(Direction.UP);
        if (downVisible)
            dir.add(Direction.DOWN);
        return dir;
    }

    @Nonnull
    @Override
    public IModelData getModelData() {
        return new ModelDataMap.Builder()
                .withInitial(TEXTURE, texture)
                .withInitial(NORTH_VISIBLE, northVisible)
                .withInitial(EAST_VISIBLE, eastVisible)
                .withInitial(SOUTH_VISIBLE, southVisible)
                .withInitial(WEST_VISIBLE, westVisible)
                .withInitial(UP_VISIBLE, upVisible)
                .withInitial(DOWN_VISIBLE, downVisible)
                .build();
    }



    /**
     * Overrides
     */

    @Override
    public CompoundNBT save(CompoundNBT tags) {
        if (tags == null) tags = new CompoundNBT();
        tags.putInt("BlockHealth", texture);
        return super.save(tags);
    }

    @Override
    public void load(BlockState state, CompoundNBT tags) {
        if (tags != null && tags.contains("TextureIndex")) texture = tags.getInt("TextureIndex");
        super.load(state, tags);
    }
}
