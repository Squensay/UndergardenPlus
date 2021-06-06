package squensay.undergardenplus.event;

import squensay.undergardenplus.util.DimensionTeleport;
import squensay.undergardenplus.util.FillBlock;
import squensay.undergardenplus.util.SafePos;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import quek.undergarden.registry.UGDimensions;



@Mod.EventBusSubscriber
public class PlayerInVoid {

    public static boolean isEnabledForDimension(RegistryKey<World> dimension) {
        return dimension == World.OVERWORLD;
    }

    public static boolean isTeleported = false;

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if(!event.player.level.isClientSide) {
            ServerPlayerEntity player = (ServerPlayerEntity) event.player;
            boolean inVoid = player.getY() <= -5;
            BlockPos playerPos = new BlockPos(player.getX(), 254, player.getZ());
            BlockPos safePos;
            if (inVoid && isEnabledForDimension(player.level.dimension())) {
                DimensionTeleport.teleport(player, UGDimensions.UNDERGARDEN_WORLD, playerPos);
                safePos = SafePos.CheckSafePos(player, playerPos);
                FillBlock.voidColumn(event.player.level, safePos, 1);
                player.teleportTo(safePos.getX() + 0.5, 256, safePos.getZ() + 0.5);
                player.addEffect(new EffectInstance(Effects.BLINDNESS, 2000, 0));
                isTeleported = true;
            }
        }
    }

    @SubscribeEvent
    public static void onDimensionChange(LivingFallEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntity();
            if (!player.level.isClientSide && isTeleported) {
                event.setDamageMultiplier(0);
                player.level.explode(player, player.getX(), player.getY(), player.getZ(), 6, Explosion.Mode.BREAK);
                player.removeEffect(Effects.BLINDNESS);
                isTeleported = false;
            }
        }
    }
}