package io.github.shark.moon.item;

import io.github.shark.moon.MoonMod;
import net.minecraft.item.ArmorItem;
import net.modificationstation.stationapi.api.client.item.ArmorTextureProvider;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.template.item.TemplateArmorItem;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.Namespace;

public class moonSuitItem extends TemplateArmorItem implements ArmorTextureProvider {
    public moonSuitItem(Identifier identifier, int slot) {
        super(identifier, 0, 1, slot);
    }

    @Override
    public Identifier getTexture(ArmorItem armor) {
        return MoonMod.NAMESPACE.id("moonSuit");
    }
}
