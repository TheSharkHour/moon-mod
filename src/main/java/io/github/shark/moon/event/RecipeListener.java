package io.github.shark.moon.event;

import io.github.shark.moon.MoonMod;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.event.recipe.RecipeRegisterEvent;
import net.modificationstation.stationapi.api.recipe.CraftingRegistry;
import net.modificationstation.stationapi.api.registry.ItemRegistry;
import net.modificationstation.stationapi.api.registry.tag.ItemTags;
import net.modificationstation.stationapi.api.tag.TagKey;
import net.modificationstation.stationapi.api.util.Identifier;
import org.jetbrains.annotations.NotNull;

/**
 * @author TheSharkHour
 * @since 11/21/2026 <br>
 * Listener event class for Recipe registration.
 */
public class RecipeListener {

    @EventListener
    public void registerRecipes(@NotNull RecipeRegisterEvent event) {
        Identifier type = event.recipeId;
        if (type == RecipeRegisterEvent.Vanilla.CRAFTING_SHAPED.type())
            registerShapedRecipes();

        if (type == RecipeRegisterEvent.Vanilla.CRAFTING_SHAPELESS.type())
            registerShapelessRecipes();
    }

    private void registerShapelessRecipes() {
        CraftingRegistry.addShapelessRecipe(new ItemStack(ItemListener.SONIC_SCREWDRIVER),
                ItemTags.REDSTONE_DUSTS,
               new ItemStack(ItemListener.SONIC_SCREWDRIVER, 1, MoonMod.MISC_CONFIG.screwdriverUses)
        );
    }

    private void registerShapedRecipes() {
        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.MOON_HELMET),
                "111", "121",
                '1', BlockListener.REINFORCED_CLOTH,
                '2', ItemTags.GLASS_BLOCKS
        );

        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.MOON_SUIT),
                "121", "111", "121",
                '1', BlockListener.REINFORCED_CLOTH,
                '2', ItemTags.IRON_INGOTS
        );

        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.MOON_LEGGINGS),
                "121", "1 1", "2 2",
                '1', BlockListener.REINFORCED_CLOTH,
                '2', ItemTags.IRON_INGOTS
        );

        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.MOON_BOOTS),
                "1 1", "2 2",
                '1', ItemTags.IRON_INGOTS,
                '2', BlockListener.REINFORCED_CLOTH
        );

        CraftingRegistry.addShapedRecipe(new ItemStack(ItemListener.SONIC_SCREWDRIVER),
                "  1", " 2 ", "2  ",
                '1', ItemTags.REDSTONE_DUSTS,
                '2', ItemTags.IRON_INGOTS
        );

        CraftingRegistry.addShapedRecipe(new ItemStack(BlockListener.LAMP, 6),
                " 1 ", "123", " 3 ",
                '1', ItemTags.GLASS_BLOCKS,
                '2', ItemTags.REDSTONE_DUSTS,
                '3', ItemTags.IRON_INGOTS
        );

        CraftingRegistry.addShapedRecipe(new ItemStack(BlockListener.MOON_TENT),
                " 1 ", "121",
                '1', BlockListener.REINFORCED_CLOTH,
                '2', ItemTags.WOODEN_DOORS
        );

        CraftingRegistry.addShapedRecipe(new ItemStack(BlockListener.REINFORCED_CLOTH, 8),
                "111", "121", "111",
                '1', ItemTags.WOOLS,
                '2', ItemTags.GOLD_INGOTS
        );

        if (MoonMod.MISC_CONFIG.cheaperCheese) {
            CraftingRegistry.addShapedRecipe(new ItemStack(BlockListener.CHEESE, 2),
                    "11 ", "11 ",
                    '1', ItemTags.MILK_BUCKETS
            );
        } else {
            CraftingRegistry.addShapedRecipe(new ItemStack(BlockListener.CHEESE, 2),
                    "111", "111", "111",
                    '1', ItemTags.MILK_BUCKETS
            );
        }

        CraftingRegistry.addShapedRecipe(new ItemStack(BlockListener.CHEESE),
                "11 ", "11 ",
                '1', CHEESES
        );

        CraftingRegistry.addShapedRecipe(new ItemStack(Block.TNT, 2),
                "121", "212", "121",
                '1', ItemTags.GUNPOWDERS,
                '2', BlockListener.REGOLITH
        );
    }

    // ! Item tags past this point ! //
    public static final TagKey<Item>
        CHEESES = TagKey.of(ItemRegistry.KEY, Identifier.of("c:foods/cheese"));
}
