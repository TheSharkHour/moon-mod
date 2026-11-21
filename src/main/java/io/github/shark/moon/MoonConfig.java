package io.github.shark.moon;

import net.glasslauncher.mods.gcapi3.api.ConfigEntry;

public class MoonConfig {
    public static class MiscellaneousConfig {
        @ConfigEntry(name = "Sonic Screwdriver Uses", description = "How much durability the sonic screwdriver has.", comment = "Default/OG: 30.")
        public Integer screwdriverUses = 30;

        @ConfigEntry(name = "Cheaper Cheese", description = "Make cheese blocks less expensive.", comment = "Default/OG: false")
        public Boolean cheaperCheese = false;
    }
}
