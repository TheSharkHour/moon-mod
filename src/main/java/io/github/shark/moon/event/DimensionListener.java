package io.github.shark.moon.event;

import io.github.shark.moon.world.dimension.MoonDimension;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.event.registry.DimensionRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.registry.DimensionContainer;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.Namespace;

/**
 * @author TheSharkHour
 * @since 11/21/2026 <br>
 * Listener event class for Dimension registration.
 */
public class DimensionListener {
    @Entrypoint.Namespace
    public static Namespace NAMESPACE;

    public static Identifier MOON;

    @EventListener
    public void registerDimensions(DimensionRegistryEvent event) {
        MOON = NAMESPACE.id("dimension_moon");
        event.registry.register(MOON, new DimensionContainer<>(MoonDimension::new));
    }
}
