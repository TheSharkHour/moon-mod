package io.github.shark.moon.world.dimension;

import io.github.shark.moon.event.BiomeListener;
import io.github.shark.moon.world.gen.MoonChunkGenerator;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.FixedBiomeSource;
import net.minecraft.world.chunk.ChunkSource;
import net.minecraft.world.dimension.Dimension;

import java.util.Collection;
import java.util.List;

/**
 * @author TheSharkHour
 * @since 11/21/2026 <br>
 * The Moon dimension.
 */
public class MoonDimension extends Dimension {

    public MoonDimension(int serialId) {
        this.id = serialId;
    }

    @Override
    protected void initBiomeSource() {
        biomeSource = new FixedBiomeSource(BiomeListener.MOON, 0.5D, 0.0D);
        isNether = false;
        evaporatesWater = false;
        hasCeiling = false;
    }

    @Override
    public ChunkSource createChunkGenerator() {
        return new MoonChunkGenerator(world, world.getSeed());
    }

    @Override
    public Collection<Biome> getBiomes() {
        return List.of(BiomeListener.MOON);
    }

    @Override
    public float getTimeOfDay(long time, float tickDelta) {
        return 0.5F;
    }

    @Override
    public float[] getBackgroundColor(float timeOfDay, float tickDelta) {
        return null;
    }

    @Override
    public Vec3d getFogColor(float timeOfDay, float tickDelta) {
        return Vec3d.createCached(0.0D, 0.0D, 0.02D);
    }

    @Override
    public float getCloudHeight() {
        return -100.0F;
    }

    @Override
    public boolean hasWorldSpawn() {
        return false;
    }

    @Override
    public boolean isValidSpawnPoint(int x, int z) {
        return true;
    }
}
