package io.github.shark.moon.block;

import io.github.shark.moon.event.BlockListener;
import io.github.shark.moon.event.DimensionListener;
import io.github.shark.moon.world.dimension.MoonPortalForcer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraft.world.dimension.PortalForcer;
import net.modificationstation.stationapi.api.block.CustomPortal;
import net.modificationstation.stationapi.api.registry.DimensionRegistry;
import net.modificationstation.stationapi.api.template.block.TemplateNetherPortalBlock;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.world.dimension.VanillaDimensions;

/**
 * @author TheSharkHour
 * @since 11/21/2026 <br>
 * Portal between the overworld and the Moon, framed with Cheese.
 */
public class MoonPortalBlock extends TemplateNetherPortalBlock implements CustomPortal {

    public MoonPortalBlock(Identifier identifier, int textureId) {
        super(identifier, textureId);
    }

    private static int frameId() {
        return BlockListener.CHEESE.id;
    }

    // ! Portal creation ! //

    @Override
    public boolean create(World world, int x, int y, int z) {
        int frame = frameId();
        int dx = 0, dz = 0;
        if (world.getBlockId(x - 1, y, z) == frame || world.getBlockId(x + 1, y, z) == frame) dx = 1;
        if (world.getBlockId(x, y, z - 1) == frame || world.getBlockId(x, y, z + 1) == frame) dz = 1;
        if (dx == dz)
            return false;

        if (world.getBlockId(x - dx, y, z - dz) == 0) {
            x -= dx;
            z -= dz;
        }

        for (int w = -1; w <= 2; w++) {
            for (int h = -1; h <= 3; h++) {
                boolean edgeW = w == -1 || w == 2;
                boolean edgeH = h == -1 || h == 3;
                if (edgeW && edgeH) continue;

                int id = world.getBlockId(x + dx * w, y + h, z + dz * w);
                if (edgeW || edgeH) {
                    if (id != frame) return false;
                } else if (id != 0) {
                    return false;
                }
            }
        }

        world.pauseTicking = true;
        for (int w = 0; w < 2; w++) {
            for (int h = 0; h < 3; h++) {
                world.setBlock(x + dx * w, y + h, z + dz * w, this.id);
            }
        }

        world.pauseTicking = false;
        return true;
    }

    /**
     * Breaks the portal if a frame piece goes missing.
     */
    @Override
    public void neighborUpdate(World world, int x, int y, int z, int id) {
        int frame = frameId();

        int dx = 0, dz = 1;
        if (world.getBlockId(x - 1, y, z) == this.id || world.getBlockId(x + 1, y, z) == this.id) {
            dx = 1;
            dz = 0;
        }

        int bottom = y;
        while (world.getBlockId(x, bottom - 1, z) == this.id) {
            bottom--;
        }

        if (world.getBlockId(x, bottom - 1, z) != frame) {
            world.setBlock(x, y, z, 0);
            return;
        }

        int height = 1;
        while (height < 4 && world.getBlockId(x, bottom + height, z) == this.id) {
            height++;
        }

        if (height != 3 || world.getBlockId(x, bottom + height, z) != frame) {
            world.setBlock(x, y, z, 0);
            return;
        }

        boolean alongX = world.getBlockId(x - 1, y, z) == this.id || world.getBlockId(x + 1, y, z) == this.id;
        boolean alongZ = world.getBlockId(x, y, z - 1) == this.id || world.getBlockId(x, y, z + 1) == this.id;
        if (alongX && alongZ) {
            world.setBlock(x, y, z, 0);
            return;
        }

        boolean frameAhead = world.getBlockId(x + dx, y, z + dz) == frame && world.getBlockId(x - dx, y, z - dz) == this.id;
        boolean frameBehind = world.getBlockId(x - dx, y, z - dz) == frame && world.getBlockId(x + dx, y, z + dz) == this.id;
        if (!frameAhead && !frameBehind)
            world.setBlock(x, y, z, 0);
    }

    // ! Teleportation ! //

    @Override
    public Identifier getDimension(PlayerEntity player) {
        int moonId = DimensionRegistry.INSTANCE.getLegacyId(DimensionListener.MOON).orElse(Integer.MIN_VALUE);
        return player.dimensionId == moonId ? VanillaDimensions.OVERWORLD : DimensionListener.MOON;
    }

    @Override
    public PortalForcer getTravelAgent(PlayerEntity player) {
        return new MoonPortalForcer(this.id, frameId());
    }
}
