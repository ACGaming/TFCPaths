package mod.acgaming.tfcpaths;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
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
    private static BlockPos posEntityOld = new BlockPos(0, 0, 0);

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void createPath(LivingEvent.LivingUpdateEvent event)
    {
        if (event.getEntity() != null)
        {
            Entity entity = event.getEntity();
            BlockPos posEntity = new BlockPos(entity.posX, entity.posY + entity.getYOffset(), entity.posZ);
            World world = entity.getEntityWorld();
            IBlockState state = world.getBlockState(posEntity);

            double random = Math.random() * 1000.0D + 1.0D;

            if (PathConfig.DEBUG)
                System.out.println(random);

            if (!world.isRemote && !posEntityOld.equals(posEntity) && state.getBlock() instanceof BlockRockVariant && (Math.abs(entity.motionX) > 0.0D || Math.abs(entity.motionY) > 0.0D || Math.abs(entity.motionZ) > 0.0D))
            {
                BlockRockVariant blockRock = (BlockRockVariant) state.getBlock();

                if (blockRock.getType() == Rock.Type.GRASS || blockRock.getType() == Rock.Type.DRY_GRASS)
                {
                    if (random < PathConfig.GRASS_TO_DIRT)
                    {
                        world.setBlockState(posEntity, BlockRockVariant.get(blockRock.getRock(), Rock.Type.DIRT).getDefaultState());
                        BlockPos upPos = posEntity.up();
                        Material upMaterial = world.getBlockState(upPos).getMaterial();
                        if (upMaterial == Material.PLANTS || upMaterial == Material.VINE || world.getBlockState(upPos).getBlock() instanceof BlockPlacedItemFlat)
                        {
                            world.destroyBlock(upPos, false);
                        }
                        posEntityOld = posEntity;
                        return;
                    }
                }

                if (blockRock.getType() == Rock.Type.DIRT)
                {
                    if (random < PathConfig.DIRT_TO_PATH)
                    {
                        world.setBlockState(posEntity, BlockRockVariant.get(blockRock.getRock(), Rock.Type.PATH).getDefaultState());
                        posEntityOld = posEntity;
                        return;
                    }
                }

                if (blockRock.getType() == Rock.Type.PATH)
                {
                    if (random < PathConfig.PATH_TO_GRAVEL)
                    {
                        world.setBlockState(posEntity, BlockRockVariant.get(blockRock.getRock(), Rock.Type.GRAVEL).getDefaultState());
                        posEntityOld = posEntity;
                        return;
                    }
                }
            }
        }
    }
}