package io.github.FireTamer81.GeoPlayerModelTest.server.world.feature.structure.pieces;

import io.github.FireTamer81.GeoPlayerModelTest.server.block.BlockHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.EntityHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.barakoa.EntityBarako;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.barakoa.EntityBarakoaVillager;
import io.github.FireTamer81.GeoPlayerModelTest.server.entity.barakoa.MaskType;
import io.github.FireTamer81.GeoPlayerModelTest.server.item.ItemBarakoaMask;
import io.github.FireTamer81.GeoPlayerModelTest.server.item.ItemHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.loot.LootTableHandler;
import io.github.FireTamer81.GeoPlayerModelTest.server.world.feature.FeatureHandler;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import io.github.FireTamer81.TestModMain;
import net.minecraft.block.*;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.item.ItemFrameEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.Half;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.*;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.*;

public class BarakoaVillagePieces {
    private static final Set<Block> BLOCKS_NEEDING_POSTPROCESSING = ImmutableSet.<Block>builder().add(Blocks.NETHER_BRICK_FENCE).add(Blocks.TORCH).add(Blocks.WALL_TORCH).add(Blocks.OAK_FENCE).add(Blocks.SPRUCE_FENCE).add(Blocks.DARK_OAK_FENCE).add(Blocks.ACACIA_FENCE).add(Blocks.BIRCH_FENCE).add(Blocks.JUNGLE_FENCE).add(Blocks.LADDER).add(Blocks.IRON_BARS).add(Blocks.SKELETON_SKULL).build();

    public static final ResourceLocation HOUSE = new ResourceLocation(TestModMain.MODID, "barakoa_house");
    public static final ResourceLocation ROOF = new ResourceLocation(TestModMain.MODID, "barakoa_house_roof");
    public static final ResourceLocation HOUSE_SIDE = new ResourceLocation(TestModMain.MODID, "barakoa_house_side");
    public static final ResourceLocation THRONE = new ResourceLocation(TestModMain.MODID, "barako_throne");

    private static final Map<ResourceLocation, BlockPos> OFFSET = ImmutableMap.of(
            HOUSE, new BlockPos(-3, 1, -3),
            ROOF, new BlockPos(-3, 5, -3),
            HOUSE_SIDE, new BlockPos(2, 1, -2),
            THRONE, new BlockPos(-3, 0, 0)
    );

    public static StructurePiece addPiece(ResourceLocation resourceLocation, TemplateManager manager, BlockPos pos, Rotation rot, List<StructurePiece> pieces, Random rand) {
        BlockPos placementOffset = OFFSET.get(resourceLocation);
        BlockPos blockPos = pos.add(placementOffset.rotate(rot));
        StructurePiece newPiece = new BarakoaVillagePieces.Piece(manager, resourceLocation, blockPos, rot);
        pieces.add(newPiece);
        return newPiece;
    }

    public static StructurePiece addPieceCheckBounds(ResourceLocation resourceLocation, TemplateManager manager, BlockPos pos, Rotation rot, List<StructurePiece> pieces, Random rand, List<StructurePiece> ignore) {
        BlockPos placementOffset = OFFSET.get(resourceLocation);
        BlockPos blockPos = pos.add(placementOffset.rotate(rot));
        BarakoaVillagePieces.Piece newPiece = new BarakoaVillagePieces.Piece(manager, resourceLocation, blockPos, rot);
        for (StructurePiece piece : pieces) {
            if (ignore.contains(piece)) continue;
            if (newPiece.getBoundingBox().intersectsWith(piece.getBoundingBox())) return null;
        }
        pieces.add(newPiece);
        return newPiece;
    }

    public static StructurePiece addHouse(TemplateManager manager, BlockPos pos, Rotation rot, List<StructurePiece> pieces, Random rand) {
        ResourceLocation resourceLocation = HOUSE;
        BlockPos placementOffset = OFFSET.get(resourceLocation);
        BlockPos blockPos = pos.add(placementOffset.rotate(rot));
        BarakoaVillagePieces.HousePiece newPiece = new BarakoaVillagePieces.HousePiece(manager, resourceLocation, blockPos, rot);
        for (StructurePiece piece : pieces) {
            if (newPiece.getBoundingBox().intersectsWith(piece.getBoundingBox())) return null;
        }
        pieces.add(newPiece);
        newPiece.tableCorner = rand.nextInt(6);
        newPiece.tableContent = rand.nextInt(4);
        newPiece.bedCorner = rand.nextInt(6);
        newPiece.bedDirection = rand.nextInt(2);
        newPiece.chestCorner = rand.nextInt(6);
        newPiece.chestDirection = rand.nextInt(2);
        return newPiece;
    }

    public static StructurePiece addPieceCheckBounds(ResourceLocation resourceLocation, TemplateManager manager, BlockPos pos, Rotation rot, List<StructurePiece> pieces, Random rand) {
        return addPieceCheckBounds(resourceLocation, manager, pos, rot, pieces, rand, Collections.emptyList());
    }

    public static class Piece extends TemplateStructurePiece {
        protected ResourceLocation resourceLocation;
        protected Rotation rotation;

        public Piece(TemplateManager templateManagerIn, ResourceLocation resourceLocationIn, BlockPos pos, Rotation rotationIn) {
            super(FeatureHandler.BARAKOA_VILLAGE_PIECE, 0);
            this.resourceLocation = resourceLocationIn;
            this.templatePosition = pos;
            this.rotation = rotationIn;
            this.setupPiece(templateManagerIn);
        }

        public Piece(TemplateManager templateManagerIn, CompoundNBT tagCompound) {
            super(FeatureHandler.BARAKOA_VILLAGE_PIECE, tagCompound);
            this.resourceLocation = new ResourceLocation(tagCompound.getString("Template"));
            this.rotation = Rotation.valueOf(tagCompound.getString("Rot"));
            this.setupPiece(templateManagerIn);
        }

        public Piece(IStructurePieceType pieceType, TemplateManager templateManagerIn, ResourceLocation resourceLocationIn, BlockPos pos, Rotation rotationIn) {
            super(pieceType, 0);
            this.resourceLocation = resourceLocationIn;
            this.templatePosition = pos;
            this.rotation = rotationIn;
            this.setupPiece(templateManagerIn);
        }

        public Piece(IStructurePieceType pieceType, TemplateManager templateManagerIn, CompoundNBT tagCompound) {
            super(pieceType, tagCompound);
            this.resourceLocation = new ResourceLocation(tagCompound.getString("Template"));
            this.rotation = Rotation.valueOf(tagCompound.getString("Rot"));
            this.setupPiece(templateManagerIn);
        }

        private void setupPiece(TemplateManager templateManager) {
            Template template = templateManager.getTemplateDefaulted(this.resourceLocation);
            PlacementSettings placementsettings = (new PlacementSettings()).setRotation(this.rotation).setMirror(Mirror.NONE);
            this.setup(template, this.templatePosition, placementsettings);
        }

        /**
         * (abstract) Helper method to read subclass data from NBT
         */
        @Override
        protected void readAdditional(CompoundNBT tagCompound) {
            super.readAdditional(tagCompound);
            tagCompound.putString("Template", this.resourceLocation.toString());
            tagCompound.putString("Rot", this.rotation.name());
        }

        /*
         * If you added any data marker structure blocks to your structure, you can access and modify them here. In this case,
         * our structure has a data maker with the string "chest" put into it. So we check to see if the incoming function is
         * "chest" and if it is, we now have that exact position.
         *
         * So what is done here is we replace the structure block with a chest and we can then set the loottable for it.
         *
         * You can set other data markers to do other behaviors such as spawn a random mob in a certain spot, randomize what
         * rare block spawns under the floor, or what item an Item Frame will have.
         */
        @Override
        protected void handleDataMarker(String function, BlockPos pos, IServerWorld worldIn, Random rand, MutableBoundingBox sbb) {
            if ("support".equals(function)) {
                worldIn.setBlockState(pos, Blocks.OAK_FENCE.getDefaultState(), 3);
                fillAirLiquidDown(worldIn, Blocks.OAK_FENCE.getDefaultState(), pos.down());
            }
            else if ("leg".equals(function)) {
                worldIn.setBlockState(pos, Blocks.ACACIA_LOG.getDefaultState(), 3);
                fillAirLiquidDown(worldIn, Blocks.ACACIA_LOG.getDefaultState(), pos.down());
            }
            else if ("stairs".equals(function)) {
                Direction stairDirection = Direction.EAST;
                stairDirection = this.rotation.rotate(stairDirection);
                setBlockState(worldIn, pos.offset(Direction.UP, 1), Blocks.AIR.getDefaultState());
                setBlockState(worldIn, pos.offset(Direction.UP, 2), Blocks.AIR.getDefaultState());
                setBlockState(worldIn, pos, Blocks.SPRUCE_STAIRS.getDefaultState().with(StairsBlock.FACING, stairDirection.getOpposite()));
                pos = pos.offset(Direction.DOWN);
                setBlockState(worldIn, pos, Blocks.SPRUCE_STAIRS.getDefaultState().with(StairsBlock.FACING, stairDirection).with(StairsBlock.HALF, Half.TOP));
                for (int i = 1; i < 20; i++) {
                    pos = pos.offset(stairDirection);
                    if (!Block.hasSolidSideOnTop(worldIn, pos)) {
                        setBlockState(worldIn, pos, Blocks.SPRUCE_STAIRS.getDefaultState().with(StairsBlock.FACING, stairDirection.getOpposite()));
                        pos = pos.offset(Direction.DOWN);
                        setBlockState(worldIn, pos, Blocks.SPRUCE_STAIRS.getDefaultState().with(StairsBlock.FACING, stairDirection).with(StairsBlock.HALF, Half.TOP));
                    } else {
                        break;
                    }
                }
            }
            else if ("barako".equals(function)) {
                setBlockState(worldIn, pos, Blocks.AIR.getDefaultState());
                EntityBarako barako = new EntityBarako(EntityHandler.BARAKO.get(), worldIn.getWorld());
                barako.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                int i = rotation.rotate(3, 4);
                barako.setDirection(i);
                barako.onInitialSpawn(worldIn, worldIn.getDifficultyForLocation(barako.getPosition()), SpawnReason.STRUCTURE, null, null);
                BlockPos offset = new BlockPos(0, 0, -18);
                offset = offset.rotate(rotation);
                BlockPos firePitPos = pos.add(offset);
                firePitPos = worldIn.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, firePitPos);
                barako.setHomePosAndDistance(firePitPos, -1);
                worldIn.addEntity(barako);
            }
            else {
                worldIn.removeBlock(pos, false);
            }
        }

        protected void setBlockState(IWorld worldIn, BlockPos pos, BlockState state) {
            FluidState ifluidstate = worldIn.getFluidState(pos);
            if (!ifluidstate.isEmpty()) {
                worldIn.getPendingFluidTicks().scheduleTick(pos, ifluidstate.getFluid(), 0);
                if (state.hasProperty(BlockStateProperties.WATERLOGGED)) state = state.with(BlockStateProperties.WATERLOGGED, true);
            }
            worldIn.setBlockState(pos, state, 2);
            if (BLOCKS_NEEDING_POSTPROCESSING.contains(state.getBlock())) {
                worldIn.getChunk(pos).markBlockForPostprocessing(pos);
            }
        }

        @Override
        public boolean func_230383_a_(ISeedReader p_230383_1_, StructureManager p_230383_2_, ChunkGenerator p_230383_3_, Random p_230383_4_, MutableBoundingBox p_230383_5_, ChunkPos p_230383_6_, BlockPos p_230383_7_) {
            PlacementSettings placementsettings = (new PlacementSettings()).setRotation(this.rotation).setMirror(Mirror.NONE);
            BlockPos blockpos = BarakoaVillagePieces.OFFSET.get(this.resourceLocation);
            this.templatePosition.add(Template.transformedBlockPos(placementsettings, new BlockPos(0 - blockpos.getX(), 0, 0 - blockpos.getZ())));

            return super.func_230383_a_(p_230383_1_, p_230383_2_, p_230383_3_, p_230383_4_, p_230383_5_, p_230383_6_, p_230383_7_);
        }

        public void fillAirLiquidDown(IWorld worldIn, BlockState state, BlockPos startPos) {
            int i = startPos.getX();
            int j = startPos.getY();
            int k = startPos.getZ();
            while(!Block.hasSolidSideOnTop(worldIn, new BlockPos(i, j, k)) && j > 1) {
                BlockPos pos = new BlockPos(i, j, k);
                setBlockState(worldIn, pos, state);
                --j;
            }
        }
    }

    public static class HousePiece extends Piece {
        private int tableCorner;
        private int tableContent;
        private int bedCorner;
        private int bedDirection;
        private int chestCorner;
        private int chestDirection;

        public HousePiece(TemplateManager templateManagerIn, ResourceLocation resourceLocationIn, BlockPos pos, Rotation rotationIn) {
            super(FeatureHandler.BARAKOA_VILLAGE_HOUSE, templateManagerIn, resourceLocationIn, pos, rotationIn);
        }

        public HousePiece(TemplateManager templateManagerIn, CompoundNBT tagCompound) {
            super(FeatureHandler.BARAKOA_VILLAGE_HOUSE, templateManagerIn, tagCompound);
            tableCorner = tagCompound.getInt("TableCorner");
            tableContent = tagCompound.getInt("TableContent");
            bedCorner = tagCompound.getInt("BedCorner");
            bedDirection = tagCompound.getInt("bedDirection");
            chestCorner = tagCompound.getInt("ChestCorner");
            chestDirection = tagCompound.getInt("ChestDirection");
        }

        @Override
        protected void readAdditional(CompoundNBT tagCompound) {
            super.readAdditional(tagCompound);
            tagCompound.putInt("TableCorner", tableCorner);
            tagCompound.putInt("TableContent", tableContent);
            tagCompound.putInt("BedCorner", bedCorner);
            tagCompound.putInt("bedDirection", bedDirection);
            tagCompound.putInt("ChestCorner", chestCorner);
            tagCompound.putInt("ChestDirection", chestDirection);
        }

        @Override
        protected void handleDataMarker(String function, BlockPos pos, IServerWorld worldIn, Random rand, MutableBoundingBox sbb) {
            if ("corner".equals(function.substring(0, function.length() - 1))) {
                worldIn.removeBlock(pos, false);
                pos = pos.down();
                int whichCorner = Integer.parseInt(function.substring(function.length() - 1));
                Rotation cornerRotation = Rotation.values()[4 - whichCorner];
                if (whichCorner == tableCorner) {
                    setBlockState(worldIn, pos, Blocks.ACACIA_SLAB.getDefaultState().with(SlabBlock.TYPE, SlabType.TOP));
                    if (tableContent <= 1) {
                        setBlockState(worldIn, pos.up(), Blocks.TORCH.getDefaultState());
                    }
                    else if (tableContent == 2) {
                        int skullRot = cornerRotation.rotate(rotation.rotate(2, 16), 16);
                        setBlockState(worldIn, pos.up(), Blocks.SKELETON_SKULL.getDefaultState().with(BlockStateProperties.ROTATION_0_15, skullRot));
                    }
                }
                else if (whichCorner == bedCorner) {
                    setBlockState(worldIn, pos, Blocks.YELLOW_CARPET.getDefaultState());
                    BlockPos offset = new BlockPos(1, 0, 0);
                    if (bedDirection == 1) offset = new BlockPos(0, 0, -1);
                    offset = offset.rotate(rotation);
                    offset = offset.rotate(cornerRotation);
                    setBlockState(worldIn, pos.add(offset), Blocks.YELLOW_CARPET.getDefaultState());
                }
                else if (whichCorner == chestCorner) {
                    Direction facing = Direction.NORTH;
                    if (chestDirection == 1) facing = Direction.EAST;
                    facing = rotation.rotate(facing);
                    facing = cornerRotation.rotate(facing);
                    generateChest(worldIn, sbb, rand, pos, LootTableHandler.BARAKOA_VILLAGE_HOUSE, Blocks.CHEST.getDefaultState().with(BlockStateProperties.HORIZONTAL_FACING, facing));
                }
                else worldIn.removeBlock(pos, false);
            }
            else if ("mask".equals(function)) {
                worldIn.removeBlock(pos, false);
                ItemFrameEntity itemFrame = new ItemFrameEntity(worldIn.getWorld(), pos, rotation.rotate(Direction.EAST));
                int i = rand.nextInt(MaskType.values().length);
                MaskType type = MaskType.values()[i];
                ItemBarakoaMask mask = ItemHandler.BARAKOA_MASK_FURY;
                switch (type) {
                    case BLISS:
                        mask = ItemHandler.BARAKOA_MASK_BLISS;
                        break;
                    case FEAR:
                        mask = ItemHandler.BARAKOA_MASK_FEAR;
                        break;
                    case FURY:
                        mask = ItemHandler.BARAKOA_MASK_FURY;
                        break;
                    case MISERY:
                        mask = ItemHandler.BARAKOA_MASK_MISERY;
                        break;
                    case RAGE:
                        mask = ItemHandler.BARAKOA_MASK_RAGE;
                        break;
                    case FAITH:
                        mask = ItemHandler.BARAKOA_MASK_FAITH;
                        break;
                }
                ItemStack stack = new ItemStack(mask);
                itemFrame.setDisplayedItemWithUpdate(stack, false);
                worldIn.addEntity(itemFrame);
            }
            else {
                super.handleDataMarker(function, pos, worldIn, rand, sbb);
            }
        }
    }

    public abstract static class NonTemplatePiece extends ScatteredStructurePiece {

        protected NonTemplatePiece(IStructurePieceType structurePieceTypeIn, Random rand, int xIn, int yIn, int zIn, int widthIn, int heightIn, int depthIn) {
            super(structurePieceTypeIn, rand, xIn, yIn, zIn, widthIn, heightIn, depthIn);
        }

        protected NonTemplatePiece(IStructurePieceType structurePieceTypeIn, CompoundNBT nbt) {
            super(structurePieceTypeIn, nbt);
        }

        public BlockPos findGround(IWorld worldIn, int x, int z) {
            int i = this.getXWithOffset(x, z);
            int k = this.getZWithOffset(x, z);
            int j = worldIn.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, i, k);
            return new BlockPos(i, j, k);
        }

        public void fillAirLiquidBelowHeightmap(IWorld worldIn, BlockState state, int x, int z) {
            int i = this.getXWithOffset(x, z);
            int k = this.getZWithOffset(x, z);
            int j = worldIn.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, i, k) - 2;
            while(!Block.hasSolidSideOnTop(worldIn, new BlockPos(i, j, k)) && j > 1) {
                BlockPos pos = new BlockPos(i, j, k);
                FluidState ifluidstate = worldIn.getFluidState(pos);
                BlockState toPut = state;
                if (!ifluidstate.isEmpty()) {
                    worldIn.getPendingFluidTicks().scheduleTick(pos, ifluidstate.getFluid(), 0);
                    if (toPut.hasProperty(BlockStateProperties.WATERLOGGED)) toPut = toPut.with(BlockStateProperties.WATERLOGGED, true);
                }
                worldIn.setBlockState(pos, toPut, 2);
                if (BLOCKS_NEEDING_POSTPROCESSING.contains(state.getBlock())) {
                    worldIn.getChunk(pos).markBlockForPostprocessing(pos);
                }
                --j;
            }
        }

        protected void setBlockState(IWorld worldIn, BlockPos pos, BlockState state) {
            FluidState ifluidstate = worldIn.getFluidState(pos);
            if (!ifluidstate.isEmpty()) {
                worldIn.getPendingFluidTicks().scheduleTick(pos, ifluidstate.getFluid(), 0);
                if (state.hasProperty(BlockStateProperties.WATERLOGGED)) state = state.with(BlockStateProperties.WATERLOGGED, true);
            }
            worldIn.setBlockState(pos, state, 2);
            if (BLOCKS_NEEDING_POSTPROCESSING.contains(state.getBlock())) {
                worldIn.getChunk(pos).markBlockForPostprocessing(pos);
            }
        }
    }

    public static class FirepitPiece extends NonTemplatePiece {

        public FirepitPiece(Random random, int x, int z) {
            super(FeatureHandler.BARAKOA_VILLAGE_FIREPIT, random, x, 64, z, 9, 3, 9);
        }

        public FirepitPiece(TemplateManager templateManagerIn, CompoundNBT tagCompound) {
            super(FeatureHandler.BARAKOA_VILLAGE_FIREPIT, tagCompound);
        }

        /**
         * (abstract) Helper method to read subclass data from NBT
         */
        @Override
        protected void readAdditional(CompoundNBT tagCompound) {
            super.readAdditional(tagCompound);
        }

        @Override
        public boolean func_230383_a_(ISeedReader worldIn, StructureManager structureManager, ChunkGenerator chunkGenerator, Random randomIn, MutableBoundingBox p_230383_5_, ChunkPos p_230383_6_, BlockPos p_230383_7_) {
            BlockPos centerPos = findGround(worldIn, 4, 4);
            worldIn.setBlockState(centerPos, Blocks.CAMPFIRE.getDefaultState(), 2);
            fillAirLiquidBelowHeightmap(worldIn, Blocks.ACACIA_LOG.getDefaultState(), 4, 4);
            Vector2f[] positions = new Vector2f[] {
                    new Vector2f(0, 3),
                    new Vector2f(0, 5),
                    new Vector2f(8, 3),
                    new Vector2f(8, 5),
                    new Vector2f(3, 0),
                    new Vector2f(5, 0),
                    new Vector2f(3, 8),
                    new Vector2f(5, 8),
                    new Vector2f(1, 1),
                    new Vector2f(1, 7),
                    new Vector2f(7, 1),
                    new Vector2f(7, 7),
            };
            for (Vector2f pos : positions) {
                worldIn.setBlockState(findGround(worldIn, (int) pos.x, (int) pos.y), Blocks.ACACIA_LOG.getDefaultState(), 2);
                fillAirLiquidBelowHeightmap(worldIn, Blocks.ACACIA_LOG.getDefaultState(), (int) pos.x, (int) pos.y);
            }

            // Spawn Barakoa
            int numBarakoa = randomIn.nextInt(5) + 5;
            for (int i = 1; i <= numBarakoa; i++) {
                int distance;
                int angle;
                EntityBarakoaVillager barakoa = new EntityBarakoaVillager(EntityHandler.BARAKOA_VILLAGER.get(), worldIn.getWorld());
                for (int j = 1; j <= 20; j++) {
                    distance = randomIn.nextInt(10) + 2;
                    angle = randomIn.nextInt(360);
                    int x = (int) (distance * Math.sin(Math.toRadians(angle))) + 4;
                    int z = (int) (distance * Math.cos(Math.toRadians(angle))) + 4;
                    BlockPos bPos = findGround(worldIn, x, z);
                    barakoa.setPosition(bPos.getX(), bPos.getY(), bPos.getZ());
                    if (bPos.getY() > 0 && barakoa.canSpawn(worldIn, SpawnReason.STRUCTURE) && worldIn.hasNoCollisions(barakoa.getBoundingBox())) {
                        barakoa.onInitialSpawn(worldIn, worldIn.getDifficultyForLocation(barakoa.getPosition()), SpawnReason.STRUCTURE, null, null);
                        barakoa.setHomePosAndDistance(centerPos, 25);
                        worldIn.addEntity(barakoa);
                        break;
                    }
                }
            }
            return true;
        }
    }

    public static class StakePiece extends NonTemplatePiece {
        private final boolean skull;
        private final int skullDir;

        public StakePiece(Random random, int x, int y, int z) {
            super(FeatureHandler.BARAKOA_VILLAGE_STAKE, random, x, y, z, 1, 3, 1);
            skull = random.nextBoolean();
            skullDir = random.nextInt(16);
        }

        public StakePiece(TemplateManager templateManagerIn, CompoundNBT tagCompound) {
            super(FeatureHandler.BARAKOA_VILLAGE_STAKE, tagCompound);
            skull = tagCompound.getBoolean("Skull");
            skullDir = tagCompound.getInt("SkullDir");
        }

        /**
         * (abstract) Helper method to read subclass data from NBT
         */
        @Override
        protected void readAdditional(CompoundNBT tagCompound) {
            super.readAdditional(tagCompound);
            tagCompound.putBoolean("Skull", skull);
            tagCompound.putInt("SkullDir", skullDir);
        }

        @Override
        public boolean func_230383_a_(ISeedReader worldIn, StructureManager p_230383_2_, ChunkGenerator p_230383_3_, Random p_230383_4_, MutableBoundingBox mutableBoundingBoxIn, ChunkPos p_230383_6_, BlockPos p_230383_7_) {
            setBlockState(worldIn, Blocks.OAK_FENCE.getDefaultState(), 0, 1, 0, mutableBoundingBoxIn);
            if (skull) {
                setBlockState(worldIn, Blocks.SKELETON_SKULL.getDefaultState().with(BlockStateProperties.ROTATION_0_15, skullDir), 0, 2, 0, mutableBoundingBoxIn);
            }
            else {
                setBlockState(worldIn, Blocks.TORCH.getDefaultState(), 0, 2, 0, mutableBoundingBoxIn);
            }
            fillAirLiquidBelowHeightmap(worldIn, Blocks.OAK_FENCE.getDefaultState(), 0, 0);
            return true;
        }
    }

    public static class AltarPiece extends NonTemplatePiece {
        public AltarPiece(Random random, int x, int y, int z) {
            super(FeatureHandler.BARAKOA_VILLAGE_ALTAR, random, x - 2, y - 1, z - 2, 5, 4, 5);
        }

        public AltarPiece(TemplateManager templateManagerIn, CompoundNBT tagCompound) {
            super(FeatureHandler.BARAKOA_VILLAGE_STAKE, tagCompound);
        }

        @Override
        public boolean func_230383_a_(ISeedReader worldIn, StructureManager p_230383_2_, ChunkGenerator p_230383_3_, Random randomIn, MutableBoundingBox p_230383_5_, ChunkPos p_230383_6_, BlockPos p_230383_7_) {
            Vector2f[] thatchPositions = new Vector2f[] {
                    new Vector2f(0, 1),
                    new Vector2f(0, 2),
                    new Vector2f(0, 3),
                    new Vector2f(1, 0),
                    new Vector2f(1, 1),
                    new Vector2f(1, 2),
                    new Vector2f(1, 3),
                    new Vector2f(1, 4),
                    new Vector2f(2, 0),
                    new Vector2f(2, 1),
                    new Vector2f(2, 2),
                    new Vector2f(2, 3),
                    new Vector2f(2, 4),
                    new Vector2f(3, 0),
                    new Vector2f(3, 1),
                    new Vector2f(3, 2),
                    new Vector2f(3, 3),
                    new Vector2f(3, 4),
                    new Vector2f(4, 1),
                    new Vector2f(4, 2),
                    new Vector2f(4, 3),
            };
            for (Vector2f pos : thatchPositions) {
                BlockPos placePos = findGround(worldIn, (int) pos.x, (int) pos.y).down();
                worldIn.setBlockState(placePos, BlockHandler.THATCH.get().getDefaultState(), 2);
            }
            Vector2f[] groundSkullPositions = new Vector2f[] {
                    new Vector2f(0, 1),
                    new Vector2f(0, 3),
                    new Vector2f(2, 4),
                    new Vector2f(3, 3),
                    new Vector2f(4, 2),
            };
            for (Vector2f pos : groundSkullPositions) {
                setBlockState(worldIn, findGround(worldIn, (int) pos.x, (int) pos.y), Blocks.SKELETON_SKULL.getDefaultState().with(BlockStateProperties.ROTATION_0_15, randomIn.nextInt(16)));
            }
            Vector2f[] fenceSkullPositions = new Vector2f[] {
                    new Vector2f(0, 2),
                    new Vector2f(1, 4),
                    new Vector2f(3, 4),
                    new Vector2f(4, 1),
                    new Vector2f(4, 3),
            };
            for (Vector2f pos : fenceSkullPositions) {
                BlockPos groundPos = findGround(worldIn, (int) pos.x, (int) pos.y);
                setBlockState(worldIn, groundPos, Blocks.OAK_FENCE.getDefaultState());
                setBlockState(worldIn, groundPos.up(), Blocks.SKELETON_SKULL.getDefaultState().with(BlockStateProperties.ROTATION_0_15, randomIn.nextInt(16)));
            }
            return true;
        }

        public BlockPos findGround(IWorld worldIn, int x, int z) {
            int i = this.getXWithOffset(x, z);
            int k = this.getZWithOffset(x, z);
            int j = worldIn.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, i, k);
            return new BlockPos(i, j, k);
        }
    }
}
