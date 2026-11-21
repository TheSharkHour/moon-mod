package io.github.shark.moon.world.gen.feature;

import io.github.shark.moon.event.BlockListener;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Feature;
import net.modificationstation.stationapi.api.block.BlockState;

import java.util.Random;

/**
 * @author TheSharkHour
 * @since 11/21/2026 <br>
 * A world feature to carve a shallow, bowl-shaped crater that follows the terrain.
 */
public class CraterFeature extends Feature {

    // The OG mod's 'squish' factor. This keeps it from being too deep.
    private static final double FLATTEN = 2.5;

    // How often should the crater be lined with Regolith.
    private static final double REGOLITH_FLOOR_CHANCE = 0.8;

    // Bedrock preventative.
    private static final int MIN_Y = 6;

    /**
     * During decoration only this chunk, and its +X/+Z neighbors, are guaranteed to be loaded.
     */
    private static final int SAFE_AREA = 32;

    private final int rarity;
    private final int minRadius;
    private final int maxRadius;

    /**
     * @param rarity    one crater per {@code rarity} chunks, on average
     * @param minRadius smallest crater radius
     * @param maxRadius largest crater radius, at most 15 (see {@link #SAFE_AREA})
     */
    public CraterFeature(int rarity, int minRadius, int maxRadius) {
        this.rarity = rarity;
        this.minRadius = minRadius;
        this.maxRadius = Math.min(maxRadius, SAFE_AREA / 2 - 1);
    }

    @Override
    public boolean generate(World world, Random random, int x, int y, int z) {
        if (random.nextInt(rarity) != 0) return false;

        int radius = minRadius + random.nextInt(maxRadius - minRadius + 1);

        // Keep the whole crater inside of the loaded area.
        int span = SAFE_AREA - 2 * radius;
        int centerX = x + radius + random.nextInt(span);
        int centerZ = z + radius + random.nextInt(span);

        BlockState regolith = BlockListener.REGOLITH.getDefaultState();
        for (int bx = centerX - radius; bx <= centerX + radius; bx++)
            for (int bz = centerZ - radius; bz <= centerZ + radius; bz++) {
                int dx = bx - centerX, dz = bz - centerZ;
                int inside = radius * radius - dx * dx - dz * dz;
                if (inside <= 0)
                    continue;

                int depth = (int) (Math.sqrt(inside) / FLATTEN);
                if (depth == 0)
                    continue;

                // Dig down from this column's own surface, so the crater follows hills.
                int top = world.getTopY(bx, bz) - 1;
                int floor = Math.max(top - depth, MIN_Y);
                for (int by = top; by > floor; by--) {
                    world.setBlockWithoutNotifyingNeighbors(bx, by, bz, 0);
                }

                if (random.nextDouble() < REGOLITH_FLOOR_CHANCE) {
                    BlockState state = world.getBlockState(bx, floor, bz);
                    if (state.isOf(Block.STONE) || state.isOf(BlockListener.CHEESE))
                        world.setBlockStateWithoutNotifyingNeighbors(bx, floor, bz, regolith);
                }
            }

        return true;
    }
}
