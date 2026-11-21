package io.github.shark.moon.world.dimension;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.dimension.PortalForcer;

import java.util.Random;

/**
 * @author TheSharkHour
 * @since 11/21/2026 <br>
 * Finds the nearest moon portal or creates a new one.
 */
public class MoonPortalForcer extends PortalForcer {

    private static final int SEARCH_RADIUS = 128;
    private static final int BUILD_RADIUS = 16;

    private final Random random = new Random();
    private final int portalId;
    private final int frameId;

    public MoonPortalForcer(int portalId, int frameId) {
        this.portalId = portalId;
        this.frameId = frameId;
    }

    @Override
    public void moveToPortal(World world, Entity entity) {
        if (!teleportToValidPortal(world, entity)) {
            createPortal(world, entity);
            teleportToValidPortal(world, entity);
        }
    }

    @Override
    public boolean teleportToValidPortal(World world, Entity entity) {
        int ex = MathHelper.floor(entity.x);
        int ez = MathHelper.floor(entity.z);
        int top = world.getTopY() - 1;
        int bottom = world.getBottomY();

        double best = -1;
        int bx = 0, by = 0, bz = 0;
        for (int x = ex - SEARCH_RADIUS; x <= ex + SEARCH_RADIUS; x++) {
            double ox = x + 0.5 - entity.x;

            for (int z = ez - SEARCH_RADIUS; z <= ez + SEARCH_RADIUS; z++) {
                double oz = z + 0.5 - entity.z;

                for (int y = top; y >= bottom; y--) {
                    if (world.getBlockId(x, y, z) != portalId)
                        continue;

                    while (world.getBlockId(x, y - 1, z) == portalId)
                        y--;

                    double oy = y + 0.5 - entity.y;
                    double dist = ox * ox + oy * oy + oz * oz;
                    if (best < 0 || dist < best) {
                        best = dist;
                        bx = x;
                        by = y;
                        bz = z;
                    }
                }
            }
        }

        if (best < 0)
            return false;

        double tx = bx + 0.5, ty = by + 0.5, tz = bz + 0.5;
        if (world.getBlockId(bx - 1, by, bz) == portalId) tx -= 0.5;
        if (world.getBlockId(bx + 1, by, bz) == portalId) tx += 0.5;
        if (world.getBlockId(bx, by, bz - 1) == portalId) tz -= 0.5;
        if (world.getBlockId(bx, by, bz + 1) == portalId) tz += 0.5;

        entity.setPositionAndAnglesKeepPrevAngles(tx, ty, tz, entity.yaw, 0.0F);
        entity.velocityX = entity.velocityY = entity.velocityZ = 0.0;

        return true;
    }

    @Override
    public boolean createPortal(World world, Entity entity) {
        int ex = MathHelper.floor(entity.x);
        int ey = MathHelper.floor(entity.y);
        int ez = MathHelper.floor(entity.z);
        int top = world.getTopY() - 1;
        int bottom = world.getBottomY();
        int startDir = random.nextInt(4);

        double best = -1;
        int px = ex, py = ey, pz = ez, dir = 0;

        for (int pass = 0; pass < 2 && best < 0; pass++) {
            int depth = pass == 0 ? 3 : 1;
            int dirs = pass == 0 ? 4 : 2;
            for (int x = ex - BUILD_RADIUS; x <= ex + BUILD_RADIUS; x++) {
                double ox = x + 0.5 - entity.x;

                for (int z = ez - BUILD_RADIUS; z <= ez + BUILD_RADIUS; z++) {
                    double oz = z + 0.5 - entity.z;

                    for (int y = top; y >= bottom; y--) {
                        if (!world.isAir(x, y, z)) continue;
                        while (y > bottom && world.isAir(x, y - 1, z))
                            y--;

                        for (int d = startDir; d < startDir + dirs; d++) {
                            if (!fits(world, x, y, z, d, depth))
                                continue;

                            double oy = y + 0.5 - entity.y;
                            double dist = ox * ox + oy * oy + oz * oz;
                            if (best < 0 || dist < best) {
                                best = dist;
                                px = x;
                                py = y;
                                pz = z;
                                dir = d % (pass == 0 ? 4 : 2);
                            }
                        }
                    }
                }
            }
        }

        int[] axis = axis(dir);
        int ax = axis[0], az = axis[1];

        if (best < 0) {
            py = Math.max(bottom + 10, Math.min(py, top - 10));
            for (int side = -1; side <= 1; side++) {
                for (int w = 1; w < 3; w++) {
                    for (int h = -1; h < 3; h++) {
                        int bx = px + (w - 1) * ax + side * az;
                        int bz = pz + (w - 1) * az - side * ax;
                        world.setBlock(bx, py + h, bz, h < 0 ? frameId : 0);
                    }
                }
            }
        }

        for (int pass = 0; pass < 4; pass++) {
            world.pauseTicking = true;
            for (int w = 0; w < 4; w++)
                for (int h = -1; h < 4; h++) {
                    boolean edge = w == 0 || w == 3 || h == -1 || h == 3;
                    world.setBlock(px + (w - 1) * ax, py + h, pz + (w - 1) * az, edge ? frameId : portalId);
                }
            world.pauseTicking = false;

            for (int w = 0; w < 4; w++) {
                for (int h = -1; h < 4; h++) {
                    int bx = px + (w - 1) * ax, by = py + h, bz = pz + (w - 1) * az;
                    world.notifyNeighbors(bx, by, bz, world.getBlockId(bx, by, bz));
                }
            }
        }
        return true;
    }

    private static int[] axis(int dir) {
        int ax = dir % 2;
        int az = 1 - ax;
        if (dir % 4 >= 2) {
            ax = -ax;
            az = -az;
        }

        return new int[]{ax, az};
    }

    private static boolean fits(World world, int x, int y, int z, int dir, int depth) {
        int[] axis = axis(dir);
        int ax = axis[0], az = axis[1];
        for (int side = 0; side < depth; side++) {
            for (int w = 0; w < 4; w++) {
                for (int h = -1; h < 4; h++) {
                    int bx = x + (w - 1) * ax + side * az;
                    int by = y + h;
                    int bz = z + (w - 1) * az - side * ax;
                    if (h < 0 ? !world.getMaterial(bx, by, bz).isSolid() : !world.isAir(bx, by, bz))
                        return false;
                }
            }
        }

        return true;
    }
}
