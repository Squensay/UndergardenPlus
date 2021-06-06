package squensay.undergardenplus.util;

import com.google.common.collect.Lists;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.World;

import java.util.List;


public class FillBlock {
    public static void voidColumn(World world, BlockPos pos, int columnSize) {
        List<BlockPos> list = Lists.newArrayList();
        MutableBoundingBox area = new MutableBoundingBox(pos.getX() - columnSize, pos.getY(), pos.getZ() - columnSize,
                pos.getX() + columnSize, 255, pos.getZ() + columnSize);
        for (BlockPos blockpos : BlockPos.betweenClosed(area.x0, area.y0, area.z0, area.x1, area.y1, area.z1)) {
            list.add(blockpos.immutable());
        }
        for (BlockPos blockpos1 : list) {
            world.setBlockAndUpdate(blockpos1, Blocks.AIR.defaultBlockState());
        }
    }
}