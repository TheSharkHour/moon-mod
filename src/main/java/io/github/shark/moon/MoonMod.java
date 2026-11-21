package io.github.shark.moon;

import net.glasslauncher.mods.gcapi3.api.ConfigRoot;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Namespace;
import org.apache.logging.log4j.Logger;

public class MoonMod {
    @Entrypoint.Namespace
    public static Namespace NAMESPACE;

    @Entrypoint.Logger
    public static Logger LOGGER;

    @ConfigRoot(value = "miscellaneous", visibleName = "Miscellaneous Options", index = 0)
    public static final MoonConfig.MiscellaneousConfig MISC_CONFIG = new MoonConfig.MiscellaneousConfig();

    @ConfigRoot(value = "miscellaneous", visibleName = "Miscellaneous Options", index = 1)
    public static final MoonConfig.WorldConfig WORLD_CONFIG = new MoonConfig.WorldConfig();
}
