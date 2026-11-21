package io.github.shark.moon.event;

import io.github.shark.moon.MoonMod;
import io.github.shark.moon.item.moonSuitItem;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.template.item.TemplateFoodItem;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Namespace;

/**
 * @author TheSharkHour
 * @since 11/20/2026 <br>
 * Listener event class for Item registration.
 */
public class ItemListener {

    public static Item MOON_HELMET;
    public static Item MOON_SUIT;
    public static Item MOON_LEGGINGS;
    public static Item MOON_BOOTS;
    public static Item SONIC_SCREWDRIVER;
    public static Item CHEESE_WEDGE;

    @Entrypoint.Namespace
    public static Namespace NAMESPACE;

    @EventListener
    public void registerItems(ItemRegistryEvent event) {
        MOON_HELMET = new moonSuitItem(NAMESPACE.id("moon_helmet"), 0)
                .setTranslationKey(NAMESPACE.id("moon_helmet"));

        MOON_SUIT = new moonSuitItem(NAMESPACE.id("moon_suit"), 1)
                .setTranslationKey(NAMESPACE.id("moon_suit"));

        MOON_LEGGINGS = new moonSuitItem(NAMESPACE.id("moon_leggings"), 2)
                .setTranslationKey(NAMESPACE.id("moon_leggings"));

        MOON_BOOTS = new moonSuitItem(NAMESPACE.id("moon_boots"), 3)
                .setTranslationKey(NAMESPACE.id("moon_boots"));

        SONIC_SCREWDRIVER = new TemplateItem(NAMESPACE.id("sonic_screwdriver"))
                .setTranslationKey(NAMESPACE.id("sonic_screwdriver"))
                .setMaxCount(1)
                .setMaxDamage(MoonMod.MISC_CONFIG.screwdriverUses);

        CHEESE_WEDGE = new TemplateFoodItem(NAMESPACE.id("cheese_wedge"), 2, false)
                .setTranslationKey(NAMESPACE.id("cheese_wedge"))
                .setMaxCount(16);
    }
}
