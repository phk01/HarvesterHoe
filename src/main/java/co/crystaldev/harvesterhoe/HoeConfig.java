package co.crystaldev.harvesterhoe;

import co.crystaldev.alpinecore.framework.config.AlpineConfig;
import co.crystaldev.alpinecore.framework.config.object.ConfigMessage;
import de.exlll.configlib.Comment;
import lombok.Getter;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class HoeConfig extends AlpineConfig {

    public ConfigMessage noHoeMessage = new ConfigMessage("<red>You must be holding a hoe to harvest!");
    public ConfigMessage noOnlineMessage = new ConfigMessage("<red>That player is not online!");
    public ConfigMessage noPermissionMessage = new ConfigMessage("<red>You do not have permission to use this command!");
    public ConfigMessage giveMessage = new ConfigMessage("<green>You have been given a Harvester Hoe! %multiplier%");
    public ConfigMessage giveOtherMessage = new ConfigMessage("<green>You have given <yellow>%player%<green> a Harvester Hoe with multi %multiplier%!");
    @Comment("You can add more crops by doing \"CropsName: Amount\"")
    public Map<Material, Double> materialToAmount = new HashMap<>();
    // Constructor to initialize the map
    {
        materialToAmount.put(Material.WHEAT, 1.0);
        materialToAmount.put(Material.CARROTS, 1.2);
        materialToAmount.put(Material.POTATOES, 1.5);
    }

    //Make a list of lore

    @Getter
    private List<String> Lore;

    public HoeConfig() {
        Lore = Arrays.asList("&7Harvester Hoe", "&7Harvest crops with a multiplier of &a%multiplier%&7!");
    }

    //public List<String> lore = List.of("&7Harvester Hoe", "&7Harvest crops with a multiplier of &a%multiplier%&7!");
}
