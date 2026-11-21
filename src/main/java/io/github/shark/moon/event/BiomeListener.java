package io.github.shark.moon.event;

import io.github.shark.moon.MoonMod;
import io.github.shark.moon.world.gen.feature.CheeseFeature;
import io.github.shark.moon.world.gen.feature.CraterFeature;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.OreFeature;
import net.modificationstation.stationapi.api.event.world.biome.BiomeRegisterEvent;
import net.modificationstation.stationapi.api.worldgen.biome.BiomeBuilder;

/**
 * @author TheSharkHour
 * @since 11/21/2026 <br>
 * Listener event class for Biome registration.
 */
public class BiomeListener {

    public static Biome MOON;

    @EventListener
    public void registerBiomes(BiomeRegisterEvent event) {
        MOON = BiomeBuilder.start("Moon")
                .height(56, 80)
                .precipitation(false)
                .snow(false)
                .fogColor(0x000005)
                .feature(new CraterFeature(MoonMod.WORLD_CONFIG.craterRarity, MoonMod.WORLD_CONFIG.craterMin, MoonMod.WORLD_CONFIG.craterMax))
                .feature(new CheeseFeature(MoonMod.WORLD_CONFIG.cheeseRarity))
                .feature(new OreFeature(Block.ICE.id, 1))
                .feature(new OreFeature(Block.IRON_ORE.id, 16))
                .feature(new OreFeature(Block.GRAVEL.id, 48))
                .build();
    }
}
