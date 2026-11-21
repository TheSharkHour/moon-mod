package io.github.shark.moon.event;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Namespace;

public class BlockListener {

    public static Block PORTAL;
    public static Block LAMP;
    public static Block MOON_TENT;
    public static Block REINFORCED_CLOTH;
    public static Block REINFORCED_CLOTH_UNBREAKABLE;
    public static Block CHEESE;
    public static Block REGOLITH;

    @Entrypoint.Namespace
    public static Namespace NAMESPACE;

    @EventListener
    public void registerBlocks(BlockRegistryEvent event) {

    }
}
