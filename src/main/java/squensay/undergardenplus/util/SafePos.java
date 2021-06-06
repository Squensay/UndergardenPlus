package squensay.undergardenplus.util;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;

public class SafePos {
    public static BlockPos CheckSafePos(ServerPlayerEntity player, BlockPos playerPos) {
        while(player.level.getBlockState(playerPos).getMaterial().blocksMotion()) {
            playerPos = playerPos.offset(0, -1, 0);
        }
        BlockPos safePos = playerPos;
        while(!player.level.getBlockState(playerPos).getMaterial().blocksMotion()) {
            playerPos = playerPos.offset(0, -1, 0);
        }
        return playerPos.offset(0, 1, 0);
    }
}
