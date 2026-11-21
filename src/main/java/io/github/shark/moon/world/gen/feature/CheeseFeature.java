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
 * World feature to generate a lump of cheese.
 */
public class CheeseFeature extends Feature {

    private final int rarity;

    public CheeseFeature(int rarity) {
        this.rarity = rarity;
    }

    @Override
    public boolean generate(World world, Random random, int x, int y, int z) {
        if (random.nextInt(rarity) != 0) return false;

        // Stay one block inside the chunk.
        int cx = x + 2 + random.nextInt(12);
        int cz = z + 2 + random.nextInt(12);
        int cy = world.getTopY(cx, cz) - 1;
        if (cy <= 0) return false;

        BlockState cheese = BlockListener.CHEESE.getDefaultState();
        boolean placed = false;
        for (int dx = -1; dx <= 1; dx++)
            for (int dy = -1; dy <= 1; dy++)
                for (int dz = -1; dz <= 1; dz++) {
                    int dist = Math.abs(dx) + Math.abs(dy) + Math.abs(dz);
                    if (dist > 1 && random.nextInt(dist) != 0) continue;

                    int px = cx + dx, py = cy + dy, pz = cz + dz;
                    BlockState state = world.getBlockState(px, py, pz);
                    if (state.isOf(BlockListener.REGOLITH) || state.isOf(Block.STONE)) {
                        world.setBlockStateWithoutNotifyingNeighbors(px, py, pz, cheese);
                        placed = true;
                    }
                }
        return placed;
    }
}
