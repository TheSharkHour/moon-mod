package io.github.shark.moon;

import net.glasslauncher.mods.gcapi3.api.ConfigEntry;

public class MoonConfig {
    public static class MiscellaneousConfig {
        @ConfigEntry(name = "Sonic Screwdriver Uses", description = "How much durability the sonic screwdriver has.", comment = "Default/OG: 30.")
        public Integer screwdriverUses = 30;

        @ConfigEntry(name = "Cheaper Cheese", description = "Make cheese blocks less expensive.", comment = "Default: true, OG: false")
        public Boolean cheaperCheese = true;
    }

    public static class WorldConfig {
        @ConfigEntry(name = "Crater Rarity", description = "How often craters can spawn.", comment = "Default: 4")
        public Integer craterRarity = 4;

        @ConfigEntry(name = "Crater Size Min", description = "Smallest size craters.", comment = "Default/OG: 8")
        public Integer craterMin = 8;

        @ConfigEntry(name = "Crater Size Max", description = "Largest size craters.", comment = "Default: 24, OG: 28")
        public Integer craterMax = 24;

        @ConfigEntry(name = "Cheese Rarity", description = "How often cheese lumps can spawn.", comment = "Default: 3")
        public Integer cheeseRarity = 3;
    }
}
