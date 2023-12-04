package co.crystaldev.harvesterhoe;

import co.crystaldev.alpinecore.framework.config.AlpineConfig;
import co.crystaldev.alpinecore.framework.config.object.ConfigMessage;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

public class HoeConfig extends AlpineConfig {

    public ConfigMessage noHoeMessage = new ConfigMessage("<red>You must be holding a hoe to harvest!");
    public ConfigMessage noOnlineMessage = new ConfigMessage("<red>That player is not online!");
    public ConfigMessage noPermissionMessage = new ConfigMessage("<red>You do not have permission to use this command!");
    public ConfigMessage giveMessage = new ConfigMessage("<green>You have been given a Harvester Hoe!");
    public ConfigMessage giveOtherMessage = new ConfigMessage("<green>You have given <yellow>%target%<green> a Harvester Hoe!");

    public Map<Material, Double> materialToAmount = new HashMap<>();

    // Constructor to initialize the map
    public HoeConfig() {
        // Populate the map with default material amounts
        materialToAmount.put(Material.WHEAT, 1.0);
        materialToAmount.put(Material.CARROTS, 1.2);
        materialToAmount.put(Material.POTATOES, 1.5);
        // Add more materials and amounts as needed
    }

}
