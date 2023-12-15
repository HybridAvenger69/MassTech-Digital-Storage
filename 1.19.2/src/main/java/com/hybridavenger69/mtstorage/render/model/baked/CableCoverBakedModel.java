package com.hybridavenger69.mtstorage.render.model.baked;

import com.mojang.math.Vector3f;
import com.hybridavenger69.mtstorage.MS;
import com.hybridavenger69.mtstorage.MSBlocks;
import com.hybridavenger69.mtstorage.apiimpl.network.node.cover.Cover;
import com.hybridavenger69.mtstorage.apiimpl.network.node.cover.CoverManager;
import com.hybridavenger69.mtstorage.block.BaseBlock;
import com.hybridavenger69.mtstorage.render.ConstantsCable;
import com.hybridavenger69.mtstorage.render.model.CubeBuilder;
import com.hybridavenger69.mtstorage.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.client.model.BakedModelWrapper;
import net.minecraftforge.client.model.data.ModelData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CableCoverBakedModel extends BakedModelWrapper<BakedModel> {

    private static TextureAtlasSprite BORDER_SPRITE;

    public CableCoverBakedModel(BakedModel base) {
        super(base);
    }

    private static int getHollowCoverSize(@Nullable BlockState state, Direction coverSide) {
        if (state == null) {
            return 6;
        }

        BaseBlock block = (BaseBlock) state.getBlock();
        if (block == MSBlocks.CABLE.get()) {
            return 6;
        }

        if (block.getDirection() != null && state.getValue(block.getDirection().getProperty()) == coverSide) {
            if (block == MSBlocks.EXPORTER.get()) {
                return 6;
            } else if (block == MSBlocks.EXTERNAL_STORAGE.get() || block == MSBlocks.IMPORTER.get()) {
                return 3;
            } else if (block == MSBlocks.CONSTRUCTOR.get() || block == MSBlocks.DESTRUCTOR.get()) { //Removed reader and writer
                return 2;
            }
        }

        return 6;
    }

    protected static void addCover(List<BakedQuad> quads, @Nullable Cover cover, Direction coverSide, Direction side, RandomSource rand, @Nullable CoverManager manager, BlockState state, boolean handle) {
        if (cover == null) {
            return;
        }

        BlockState coverState = CoverManager.getBlockState(cover.getStack());

        if (coverState == null) {
            return;
        }

        boolean hasUp = false, hasDown = false, hasEast = false, hasWest = false;

        if (manager != null) {
            hasUp = manager.hasCover(Direction.UP);
            hasDown = manager.hasCover(Direction.DOWN);
            hasEast = manager.hasCover(Direction.EAST);
            hasWest = manager.hasCover(Direction.WEST);
        }

        switch (cover.getType()) {
            case NORMAL -> addNormalCover(quads, coverState, coverSide, hasUp, hasDown, hasEast, hasWest, handle, rand);
            case HOLLOW ->
                addHollowCover(quads, coverState, coverSide, hasUp, hasDown, hasEast, hasWest, getHollowCoverSize(state, coverSide), rand);
        }
    }

    private static void addNormalCover(List<BakedQuad> quads, BlockState state, Direction coverSide, boolean hasUp, boolean hasDown, boolean hasEast, boolean hasWest, boolean handle, RandomSource random) {
        AABB bounds = ConstantsCable.getCoverBounds(coverSide);

        Vector3f from = new Vector3f((float) bounds.minX * 16, (float) bounds.minY * 16, (float) bounds.minZ * 16);
        Vector3f to = new Vector3f((float) bounds.maxX * 16, (float) bounds.maxY * 16, (float) bounds.maxZ * 16);

        if (coverSide == Direction.NORTH) {
            if (hasWest) {
                from.setX(2);
            }

            if (hasEast) {
                to.setX(14);
            }
        } else if (coverSide == Direction.SOUTH) {
            if (hasWest) {
                from.setX(2);
            }

            if (hasEast) {
                to.setX(14);
            }
        }

        if (coverSide.getAxis() != Direction.Axis.Y) {
            if (hasDown) {
                from.setY(2);
            }

            if (hasUp) {
                to.setY(14);
            }
        }

        HashMap<Direction, TextureAtlasSprite> spriteCache = new HashMap<>();  //Changed from 1.12: to improve sprite getting for each side
        quads.addAll(new CubeBuilder().from(from.x(), from.y(), from.z()).to(to.x(), to.y(), to.z()).addFaces(face -> new CubeBuilder.Face(face, spriteCache.computeIfAbsent(face, direction -> RenderUtils.getSprite(Minecraft.getInstance().getBlockRenderer().getBlockModel(state), state, direction, random)))).bake());

        if (handle) {
            if (BORDER_SPRITE == null) {
                BORDER_SPRITE = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(new ResourceLocation(MS.ID, "block/cable_part_border"));
            }

            bounds = ConstantsCable.getHolderBounds(coverSide);

            from = new Vector3f((float) bounds.minX * 16, (float) bounds.minY * 16, (float) bounds.minZ * 16);
            to = new Vector3f((float) bounds.maxX * 16, (float) bounds.maxY * 16, (float) bounds.maxZ * 16);

            quads.addAll(new CubeBuilder().from(from.x(), from.y(), from.z()).to(to.x(), to.y(), to.z()).addFaces(face -> new CubeBuilder.Face(face, BORDER_SPRITE)).bake());
        }
    }

    private static void addHollowCover(List<BakedQuad> quads, BlockState state, Direction coverSide, boolean hasUp, boolean hasDown, boolean hasEast, boolean hasWest, int size, RandomSource random) {
        AABB bounds = ConstantsCable.getCoverBounds(coverSide);

        Vector3f from = new Vector3f((float) bounds.minX * 16, (float) bounds.minY * 16, (float) bounds.minZ * 16);
        Vector3f to = new Vector3f((float) bounds.maxX * 16, (float) bounds.maxY * 16, (float) bounds.maxZ * 16);

        if (coverSide.getAxis() != Direction.Axis.Y) {
            if (hasDown) {
                from.setY(2);
            }

            if (hasUp) {
                to.setY(14);
            }
        }

        // Right
        if (coverSide == Direction.NORTH) {
            if (hasWest) {
                from.setX(2);
            } else {
                from.setX(0);
            }

            to.setX(size);
        } else if (coverSide == Direction.SOUTH) {
            if (hasEast) {
                to.setX(14);
            } else {
                to.setX(16);
            }

            from.setX(16 - size);
        } else if (coverSide == Direction.EAST) {
            from.setZ(0);
            to.setZ(size);
        } else if (coverSide == Direction.WEST) {
            from.setZ(16 - size);
            to.setZ(16);
        } else if (coverSide == Direction.DOWN || coverSide == Direction.UP) {
            from.setZ(16 - size);
            to.setZ(16);
        }
        HashMap<Direction, TextureAtlasSprite> spriteCache = new HashMap<>(); //Changed from 1.12: to improve sprite getting for each side
        quads.addAll(new CubeBuilder()
            .from(from.x(), from.y(), from.z())
            .to(to.x(), to.y(), to.z())
            .addFaces(face -> new CubeBuilder.Face(face, spriteCache.computeIfAbsent(face, direction -> RenderUtils.getSprite(Minecraft.getInstance().getBlockRenderer().getBlockModel(state), state, direction, random))))
            .bake()
        );

        // Left
        if (coverSide == Direction.NORTH) {
            if (hasEast) {
                to.setX(14);
            } else {
                to.setX(16);
            }

            from.setX(16 - size);
        } else if (coverSide == Direction.SOUTH) {
            if (hasWest) {
                from.setX(2);
            } else {
                from.setX(0);
            }

            to.setX(size);
        } else if (coverSide == Direction.EAST) {
            from.setZ(16 - size);
            to.setZ(16);
        } else if (coverSide == Direction.WEST) {
            from.setZ(0);
            to.setZ(size);
        } else if (coverSide == Direction.DOWN || coverSide == Direction.UP) {
            from.setZ(0);
            to.setZ(size);
        }

        quads.addAll(new CubeBuilder()
            .from(from.x(), from.y(), from.z())
            .to(to.x(), to.y(), to.z())
            .addFaces(face -> new CubeBuilder.Face(face, spriteCache.computeIfAbsent(face, direction -> RenderUtils.getSprite(Minecraft.getInstance().getBlockRenderer().getBlockModel(state), state, direction, random))))
            .bake()
        );

        // Bottom
        if (coverSide == Direction.NORTH) {
            from.setX(size);
            to.setX(16 - size);

            if (hasDown) {
                from.setY(2);
            } else {
                from.setY(0);
            }

            to.setY(size);
        } else if (coverSide == Direction.SOUTH) {
            from.setX(size);
            to.setX(16 - size);

            if (hasDown) {
                from.setY(2);
            } else {
                from.setY(0);
            }

            to.setY(size);
        } else if (coverSide == Direction.EAST) {
            from.setZ(size);
            to.setZ(16 - size);

            if (hasDown) {
                from.setY(2);
            } else {
                from.setY(0);
            }

            to.setY(size);
        } else if (coverSide == Direction.WEST) {
            from.setZ(size);
            to.setZ(16 - size);

            if (hasDown) {
                from.setY(2);
            } else {
                from.setY(0);
            }

            to.setY(size);
        } else if (coverSide == Direction.DOWN || coverSide == Direction.UP) {
            from.setZ(size);
            to.setZ(16 - size);

            from.setX(0);
            to.setX(size);
        }

        quads.addAll(new CubeBuilder()
            .from(from.x(), from.y(), from.z())
            .to(to.x(), to.y(), to.z())
            .addFaces(face -> new CubeBuilder.Face(face, spriteCache.computeIfAbsent(face, direction -> RenderUtils.getSprite(Minecraft.getInstance().getBlockRenderer().getBlockModel(state), state, direction, random))))
            .bake()
        );

        // Up
        if (coverSide == Direction.NORTH) {
            from.setX(size);
            to.setX(16 - size);

            if (hasUp) {
                to.setY(14);
            } else {
                to.setY(16);
            }

            from.setY(16 - size);
        } else if (coverSide == Direction.SOUTH) {
            from.setX(size);
            to.setX(16 - size);

            if (hasUp) {
                to.setY(14);
            } else {
                to.setY(16);
            }

            from.setY(16 - size);
        } else if (coverSide == Direction.EAST) {
            from.setZ(size);
            to.setZ(16 - size);

            if (hasUp) {
                to.setY(14);
            } else {
                to.setY(16);
            }

            from.setY(16 - size);
        } else if (coverSide == Direction.WEST) {
            from.setZ(size);
            to.setZ(16 - size);

            if (hasUp) {
                to.setY(14);
            } else {
                to.setY(16);
            }

            from.setY(16 - size);
        } else if (coverSide == Direction.DOWN || coverSide == Direction.UP) {
            from.setZ(size);
            to.setZ(16 - size);

            from.setX(16 - size);
            to.setX(16);
        }

        quads.addAll(new CubeBuilder()
            .from(from.x(), from.y(), from.z())
            .to(to.x(), to.y(), to.z())
            .addFaces(face -> new CubeBuilder.Face(face, spriteCache.computeIfAbsent(face, direction -> RenderUtils.getSprite(Minecraft.getInstance().getBlockRenderer().getBlockModel(state), state, direction, random))))
            .bake()
        );
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable final BlockState state,
                                    @Nullable final Direction side,
                                    @Nonnull final RandomSource rand,
                                    @Nonnull final ModelData extraData,
                                    @Nullable final RenderType renderType) {
        List<BakedQuad> quads = new ArrayList<>(super.getQuads(state, side, rand, extraData, renderType));
        if (extraData.has(CoverManager.PROPERTY)) {
            CoverManager manager = extraData.get(CoverManager.PROPERTY);
            addCover(quads, manager.getCover(Direction.NORTH), Direction.NORTH, side, rand, manager, state, true);
            addCover(quads, manager.getCover(Direction.SOUTH), Direction.SOUTH, side, rand, manager, state, true);
            addCover(quads, manager.getCover(Direction.EAST), Direction.EAST, side, rand, manager, state, true);
            addCover(quads, manager.getCover(Direction.WEST), Direction.WEST, side, rand, manager, state, true);
            addCover(quads, manager.getCover(Direction.DOWN), Direction.DOWN, side, rand, manager, state, true);
            addCover(quads, manager.getCover(Direction.UP), Direction.UP, side, rand, manager, state, true);
        }
        return quads;
    }
}

