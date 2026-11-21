package io.github.shark.moon.block;

import io.github.shark.moon.event.ItemListener;
import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Random;

/**
 * @author TheSharkHour
 * @since 11/21/2026 <br>
 * Block class for the Cheese.
 */
public class CheeseBlock extends TemplateBlock {
    public CheeseBlock(Identifier identifier) {
        super(identifier, Material.WOOD);
    }

    @Override
    public int getDroppedItemCount(Random random) {
        return 4;
    }

    @Override
    public int getDroppedItemId(int blockMeta, Random random) {
        return ItemListener.CHEESE_WEDGE.id;
    }
}
