package co.crystaldev.harvesterhoe;

import co.crystaldev.alpinecore.framework.config.AlpineConfig;
import co.crystaldev.alpinecore.framework.config.object.ConfigMessage;

public class HoeConfig extends AlpineConfig {

    public ConfigMessage noHoeMessage = new ConfigMessage("<red>You must be holding a hoe to harvest!");
    public ConfigMessage noOnlineMessage = new ConfigMessage("<red>That player is not online!");
    public ConfigMessage noPermissionMessage = new ConfigMessage("<red>You do not have permission to use this command!");
    public ConfigMessage giveMessage = new ConfigMessage("<green>You have been given a Harvester Hoe!");
    public ConfigMessage giveOtherMessage = new ConfigMessage("<green>You have given <yellow>%target%<green> a Harvester Hoe!");
    public double wheatAmount = 1;
    public double carrotAmount = 1.2;
    public double potatoAmount = 1.5;
}
