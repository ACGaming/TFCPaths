package mod.acgaming.tfcpaths;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
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
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void createPath(LivingEvent.LivingUpdateEvent event)
    {
        if (event.getEntity() != null && event.getEntity() instanceof EntityPlayer)
        {
            EntityLivingBase entity = event.getEntityLiving();
            int posX = MathHelper.floor(entity.posX);
            int posY = MathHelper.floor(entity.getEntityBoundingBox().minY - 1);
            int posZ = MathHelper.floor(entity.posZ);
            BlockPos posEntity = new BlockPos(posX, posY, posZ);
            World world = entity.getEntityWorld();
            IBlockState state = world.getBlockState(posEntity);

            double random = Math.random() * 1000.0D + 1.0D;
            double speed = Math.sqrt(entity.motionX * entity.motionX + entity.motionY * entity.motionY + entity.motionZ * entity.motionZ);

            if (!world.isRemote && state.getBlock() instanceof BlockRockVariant && speed > 0.08)
            {
                BlockRockVariant blockRock = (BlockRockVariant) state.getBlock();

                if (blockRock.getType() == Rock.Type.GRASS || blockRock.getType() == Rock.Type.DRY_GRASS)
                {
                    if (random < PathConfig.PlayerSettings.GRASS_TO_DIRT)
                    {
                        world.setBlockState(posEntity, BlockRockVariant.get(blockRock.getRock(), Rock.Type.DIRT).getDefaultState());
                        BlockPos upPos = posEntity.up();
                        Material upMaterial = world.getBlockState(upPos).getMaterial();
                        if (upMaterial == Material.PLANTS || upMaterial == Material.VINE || world.getBlockState(upPos).getBlock() instanceof BlockPlacedItemFlat)
                        {
                            world.destroyBlock(upPos, false);
                        }
                        return;
                    }
                }

                if (blockRock.getType() == Rock.Type.DIRT)
                {
                    if (random < PathConfig.PlayerSettings.DIRT_TO_PATH)
                    {
                        world.setBlockState(posEntity, BlockRockVariant.get(blockRock.getRock(), Rock.Type.PATH).getDefaultState());
                        return;
                    }
                }

                if (blockRock.getType() == Rock.Type.PATH)
                {
                    if (random < PathConfig.PlayerSettings.PATH_TO_GRAVEL)
                    {
                        world.setBlockState(posEntity, BlockRockVariant.get(blockRock.getRock(), Rock.Type.GRAVEL).getDefaultState());
                        return;
                    }
                }
            }
        }

        if (PathConfig.GeneralSettings.ALL_ENTITIES)
        {
            if (event.getEntity() != null & !(event.getEntity() instanceof EntityPlayer))
            {
                EntityLivingBase entity = event.getEntityLiving();
                int posX = MathHelper.floor(entity.posX);
                int posY = MathHelper.floor(entity.getEntityBoundingBox().minY - 1);
                int posZ = MathHelper.floor(entity.posZ);
                BlockPos posEntity = new BlockPos(posX, posY, posZ);
                World world = entity.getEntityWorld();
                IBlockState state = world.getBlockState(posEntity);

                double random = Math.random() * 1000.0D + 1.0D;
                double speed = Math.sqrt(entity.motionX * entity.motionX + entity.motionY * entity.motionY + entity.motionZ * entity.motionZ);

                if (PathConfig.GeneralSettings.DEBUG)
                {
                    System.out.println("Random: " + random);
                    System.out.println("Speed: " + speed);
                }

                if (!world.isRemote && state.getBlock() instanceof BlockRockVariant && speed > 0.08)
                {
                    BlockRockVariant blockRock = (BlockRockVariant) state.getBlock();

                    if (blockRock.getType() == Rock.Type.GRASS || blockRock.getType() == Rock.Type.DRY_GRASS)
                    {
                        if (random < PathConfig.MobSettings.GRASS_TO_DIRT)
                        {
                            world.setBlockState(posEntity, BlockRockVariant.get(blockRock.getRock(), Rock.Type.DIRT).getDefaultState());
                            BlockPos upPos = posEntity.up();
                            Material upMaterial = world.getBlockState(upPos).getMaterial();
                            if (upMaterial == Material.PLANTS || upMaterial == Material.VINE || world.getBlockState(upPos).getBlock() instanceof BlockPlacedItemFlat)
                            {
                                world.destroyBlock(upPos, false);
                            }
                            return;
                        }
                    }

                    if (blockRock.getType() == Rock.Type.DIRT)
                    {
                        if (random < PathConfig.MobSettings.DIRT_TO_PATH)
                        {
                            world.setBlockState(posEntity, BlockRockVariant.get(blockRock.getRock(), Rock.Type.PATH).getDefaultState());
                            return;
                        }
                    }

                    if (blockRock.getType() == Rock.Type.PATH)
                    {
                        if (random < PathConfig.MobSettings.PATH_TO_GRAVEL)
                        {
                            world.setBlockState(posEntity, BlockRockVariant.get(blockRock.getRock(), Rock.Type.GRAVEL).getDefaultState());
                            return;
                        }
                    }
                }
            }
        }
    }
}