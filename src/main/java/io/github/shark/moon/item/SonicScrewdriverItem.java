package io.github.shark.moon.item;

import io.github.shark.moon.block.MoonPortalBlock;
import io.github.shark.moon.event.BlockListener;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

/**
 * @author TheSharkHour
 * @since 11/21/2026 <br>
 * Item class for the Sonic Screwdriver.
 */
public class SonicScrewdriverItem extends TemplateItem {
    public SonicScrewdriverItem(Identifier identifier) {
        super(identifier);
        setMaxCount(1);
    }

    /**
     * Checks for tries to and activates a Moon portal.
     */
    @Override
    public boolean useOnBlock(ItemStack stack, PlayerEntity user, World world, int x, int y, int z, int side) {
        if (world.getBlockId(x, y, z) != BlockListener.CHEESE.id)
            return false;

        switch (side) {
            case 0 -> y--;
            case 1 -> y++;
            case 2 -> z--;
            case 3 -> z++;
            case 4 -> x--;
            case 5 -> x++;
        }

        if (!world.isAir(x, y, z))
            return false;

        for (int i = 0; i < 3 && world.isAir(x, y - 1, z); i++) {
            y--;
        }

        if (!((MoonPortalBlock) BlockListener.PORTAL).create(world, x, y, z))
            return false;

        world.playSound(x + 0.5, y + 0.5, z + 0.5, "moon:pulse", 0.6F, 1F);
        stack.damage(1, user);
        return true;
    }
}
