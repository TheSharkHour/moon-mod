package io.github.shark.moon.event;

import io.github.shark.moon.block.CheeseBlock;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Namespace;

/**
 * @author TheSharkHour
 * @since 11/20/2026 <br>
 * Listener event class for Block registration.
 */
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
        LAMP = new TemplateBlock(NAMESPACE.id("lamp"), Material.GLASS)
                .setSoundGroup(Block.GLASS_SOUND_GROUP)
                .setHardness(0.6f)
                .setLuminance(1F)
                .setTranslationKey(NAMESPACE.id("lamp"));

        MOON_TENT = new TemplateBlock(NAMESPACE.id("moon_tent"), Material.WOOL)
                .setSoundGroup(Block.WOOL_SOUND_GROUP)
                .setHardness(0.8F)
                .setTranslationKey(NAMESPACE.id("moon_tent"));

        REINFORCED_CLOTH = new TemplateBlock(NAMESPACE.id("reinforced_cloth"), Material.WOOL)
                .setSoundGroup(Block.WOOL_SOUND_GROUP)
                .setHardness(0.8F)
                .setTranslationKey(NAMESPACE.id("reinforced_cloth"));

        CHEESE = new CheeseBlock(NAMESPACE.id("cheese"))
                .setSoundGroup(Block.WOOD_SOUND_GROUP)
                .setHardness(1.0F)
                .setTranslationKey(NAMESPACE.id("cheese"));

        REGOLITH = new TemplateBlock(NAMESPACE.id("regolith"), Material.SAND)
                .setSoundGroup(Block.GRAVEL_SOUND_GROUP)
                .setHardness(0.6F)
                .setTranslationKey(NAMESPACE.id("regolith"));
    }
}
