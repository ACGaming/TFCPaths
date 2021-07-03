package mod.acgaming.tfcpaths;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = TFCPaths.MODID, name = "TFCPaths")
public class PathConfig
{
    public static GeneralSettings general_settings = new GeneralSettings();
    public static PlayerSettings player_settings = new PlayerSettings();
    public static MobSettings mob_settings = new MobSettings();

    public static class GeneralSettings
    {
        @Config.Name("Debug Mode")
        @Config.Comment("When enabled, prints debug values to console")
        public static boolean DEBUG = false;

        @Config.Name("All Entities")
        @Config.Comment("When enabled, all entities create paths (performance intensive)")
        public static boolean ALL_ENTITIES = false;
    }

    public static class PlayerSettings
    {
        @Config.Name("Grass -> Dirt")
        @Config.Comment("Chance per mille for grass to turn into dirt, set to 0.0 to disable")
        @Config.RangeDouble(min = 0.0, max = 1000.0)
        public static double GRASS_TO_DIRT = 30.0;

        @Config.Name("Dirt -> Path")
        @Config.Comment("Chance per mille for dirt to turn into a path, set to 0.0 to disable")
        @Config.RangeDouble(min = 0.0, max = 1000.0)
        public static double DIRT_TO_PATH = 20.0;

        @Config.Name("Path -> Gravel")
        @Config.Comment("Chance per mille for a path to turn into gravel, set to 0.0 to disable")
        @Config.RangeDouble(min = 0.0, max = 1000.0)
        public static double PATH_TO_GRAVEL = 2.0;
    }

    public static class MobSettings
    {
        @Config.Name("Grass -> Dirt")
        @Config.Comment("Chance per mille for grass to turn into dirt, set to 0.0 to disable")
        @Config.RangeDouble(min = 0.0, max = 1000.0)
        public static double GRASS_TO_DIRT = 15.0;

        @Config.Name("Dirt -> Path")
        @Config.Comment("Chance per mille for dirt to turn into a path, set to 0.0 to disable")
        @Config.RangeDouble(min = 0.0, max = 1000.0)
        public static double DIRT_TO_PATH = 10.0;

        @Config.Name("Path -> Gravel")
        @Config.Comment("Chance per mille for a path to turn into gravel, set to 0.0 to disable")
        @Config.RangeDouble(min = 0.0, max = 1000.0)
        public static double PATH_TO_GRAVEL = 1.0;
    }

    @Mod.EventBusSubscriber(modid = TFCPaths.MODID)
    private static class EventHandler
    {
        @SubscribeEvent
        public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event)
        {
            if (event.getModID().equals(TFCPaths.MODID))
            {
                ConfigManager.sync(TFCPaths.MODID, Config.Type.INSTANCE);
            }
        }
    }
}