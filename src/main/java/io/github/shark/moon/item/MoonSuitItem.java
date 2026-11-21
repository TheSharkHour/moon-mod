package io.github.shark.moon.item;

import io.github.shark.moon.MoonMod;
import net.minecraft.item.ArmorItem;
import net.modificationstation.stationapi.api.client.item.ArmorTextureProvider;
import net.modificationstation.stationapi.api.template.item.TemplateArmorItem;
import net.modificationstation.stationapi.api.util.Identifier;

/**
 * @author TheSharkHour
 * @since 11/20/2026 <br>
 * Item class for the Moon Suit items.
 */
public class MoonSuitItem extends TemplateArmorItem implements ArmorTextureProvider {
    public MoonSuitItem(Identifier identifier, int slot) {
        super(identifier, 0, 1, slot);
    }

    @Override
    public Identifier getTexture(ArmorItem armor) {
        return MoonMod.NAMESPACE.id("moonSuit");
    }
}
