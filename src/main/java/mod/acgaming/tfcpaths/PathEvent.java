package mod.acgaming.tfcpaths;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.objects.blocks.BlockPlacedItemFlat;
import net.dries007.tfc.objects.blocks.stone.BlockRockVariant;

@EventBusSubscriber
public class PathEvent
{
    private static BlockPos posPlayerOld = new BlockPos(0, 0, 0);

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void createPath(LivingEvent.LivingUpdateEvent event)
    {
        if (event.getEntity() != null && event.getEntity() instanceof EntityPlayer)
        {
            Entity player = event.getEntity();
            BlockPos posPlayer = new BlockPos(player.posX, player.posY + player.getYOffset(), player.posZ);
            World world = player.getEntityWorld();
            IBlockState state = world.getBlockState(posPlayer);

            double random = Math.random() * 1000.0D + 1.0D;

            if (PathConfig.DEBUG)
                System.out.println(random);

            if (!world.isRemote && !posPlayerOld.equals(posPlayer) && state.getBlock() instanceof BlockRockVariant && (Math.abs(player.motionX) > 0.0D || Math.abs(player.motionY) > 0.0D || Math.abs(player.motionZ) > 0.0D))
            {
                BlockRockVariant blockRock = (BlockRockVariant) state.getBlock();

                if (blockRock.getType() == Rock.Type.GRASS || blockRock.getType() == Rock.Type.DRY_GRASS)
                {
                    if (random < PathConfig.GRASS_TO_DIRT)
                    {
                        world.setBlockState(posPlayer, BlockRockVariant.get(blockRock.getRock(), Rock.Type.DIRT).getDefaultState());
                        BlockPos upPos = posPlayer.up();
                        Material upMaterial = world.getBlockState(upPos).getMaterial();
                        if (upMaterial == Material.PLANTS || upMaterial == Material.VINE || world.getBlockState(upPos).getBlock() instanceof BlockPlacedItemFlat)
                        {
                            world.destroyBlock(upPos, false);
                        }
                        posPlayerOld = posPlayer;
                        return;
                    }
                }

                if (blockRock.getType() == Rock.Type.DIRT)
                {
                    if (random < PathConfig.DIRT_TO_PATH)
                    {
                        world.setBlockState(posPlayer, BlockRockVariant.get(blockRock.getRock(), Rock.Type.PATH).getDefaultState());
                        posPlayerOld = posPlayer;
                        return;
                    }
                }

                if (blockRock.getType() == Rock.Type.PATH)
                {
                    if (random < PathConfig.PATH_TO_GRAVEL)
                    {
                        world.setBlockState(posPlayer, BlockRockVariant.get(blockRock.getRock(), Rock.Type.GRAVEL).getDefaultState());
                        posPlayerOld = posPlayer;
                        return;
                    }
                }
            }
        }
    }
}