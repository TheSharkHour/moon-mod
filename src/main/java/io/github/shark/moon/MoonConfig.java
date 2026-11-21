package io.github.shark.moon;

import net.glasslauncher.mods.gcapi3.api.ConfigEntry;

public class MoonConfig {
    public static class MiscellaneousConfig {
        @ConfigEntry(name = "Sonic Screwdriver Uses", description = "How many uses the sonic screwdriver has.")
        public Integer screwdriverUses = 30;
    }
}
