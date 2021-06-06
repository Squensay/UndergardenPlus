package squensay.undergardenplus.util;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;


public class DimensionTeleport {
    public static void teleport(ServerPlayerEntity player, RegistryKey<World> destinationWorld, BlockPos destinationPos) {
        ServerWorld destination = player.getServer().getLevel(destinationWorld);
        destination.getChunk(destinationPos);
        player.teleportTo(destination, destinationPos.getX(), destinationPos.getY(), destinationPos.getZ(), player.yRot, player.xRot);
    }
}