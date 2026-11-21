package io.github.shark.moon.world.gen;

import io.github.shark.moon.event.BlockListener;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSource;
import net.minecraft.world.gen.carver.Carver;
import net.minecraft.world.gen.carver.CaveCarver;
import net.minecraft.world.gen.chunk.OverworldChunkGenerator;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.util.math.MathHelper;
import net.modificationstation.stationapi.impl.world.CaveGenBaseImpl;
import net.modificationstation.stationapi.impl.world.chunk.FlattenedChunk;

import java.util.Random;

/**
 * @author TheSharkHour
 * @since 11/21/2026 <br>
 * Chunk generator for the moon dimension.
 */
public class MoonChunkGenerator extends OverworldChunkGenerator {

    private static final int REGOLITH_DEPTH = 4;

    private final World world;
    private final Random random;
    private final Carver caves;
    private Biome[] biomes;

    public MoonChunkGenerator(World world, long seed) {
        super(world, seed);
        this.world = world;
        this.random = new Random(seed);
        this.caves = new CaveCarver();

        ((CaveGenBaseImpl) caves).stationapi_setWorld(world);
    }

    @Override
    public Chunk loadChunk(int chunkX, int chunkZ) {
        return getChunk(chunkX, chunkZ);
    }

    @Override
    public Chunk getChunk(int chunkX, int chunkZ) {
        random.setSeed(chunkX * 341873128712L + chunkZ * 132897987541L);
        int heightBits = MathHelper.ceilLog2(world.getHeight());
        byte[] tiles = new byte[1 << (heightBits + 8)];

        FlattenedChunk chunk = new FlattenedChunk(world, chunkX, chunkZ);
        biomes = world.method_1781().getBiomesInArea(biomes, chunkX * 16, chunkZ * 16, 16, 16);
        double[] temperatures = world.method_1781().temperatureMap;

        buildTerrain(chunkX, chunkZ, tiles, biomes, temperatures);
        placeBedrock(tiles, heightBits);
        caves.carve(this, world, chunkX, chunkZ, tiles);
        removeFluids(tiles);

        chunk.fromLegacy(tiles);
        chunk.populateHeightMap();
        return chunk;
    }

    private void placeBedrock(byte[] tiles, int heightBits) {
        byte bedrock = (byte) Block.BEDROCK.id;
        for (int x = 0; x < 16; x++)
            for (int z = 0; z < 16; z++) {
                int column = x << (heightBits + 4) | z << heightBits;
                for (int y = 0; y < 5; y++)
                    if (y <= random.nextInt(5)) tiles[column | y] = bedrock;
            }
    }

    private static void removeFluids(byte[] tiles) {
        int flowingWater = Block.FLOWING_WATER.id, water = Block.WATER.id;
        int flowingLava = Block.FLOWING_LAVA.id, lava = Block.LAVA.id, ice = Block.ICE.id;
        for (int i = 0; i < tiles.length; i++) {
            int id = tiles[i] & 0xFF;
            if (id == flowingWater || id == water || id == flowingLava || id == lava || id == ice)
                tiles[i] = 0;
        }
    }

    @Override
    public void decorate(ChunkSource source, int chunkX, int chunkZ) {
        int x0 = chunkX * 16;
        int z0 = chunkZ * 16;

        BlockState regolith = BlockListener.REGOLITH.getDefaultState();
        for (int x = x0; x < x0 + 16; x++)
            for (int z = z0; z < z0 + 16; z++) {
                int top = world.getTopY(x, z) - 1;
                for (int y = top; y > top - REGOLITH_DEPTH && y > 0; y--)
                    if (world.getBlockState(x, y, z).isOf(Block.STONE))
                        world.setBlockStateWithoutNotifyingNeighbors(x, y, z, regolith);
            }

        Biome biome = world.method_1781().getBiome(x0 + 8, z0 + 8);
        random.setSeed(world.getSeed());
        long a = random.nextLong() / 2L * 2L + 1L;
        long b = random.nextLong() / 2L * 2L + 1L;
        random.setSeed(chunkX * a + chunkZ * b ^ world.getSeed());
        int y = world.getTopY(x0 + 8, z0 + 8);
        biome.getFeatures().forEach(feature -> feature.generate(world, random, x0, y, z0));
    }
}
