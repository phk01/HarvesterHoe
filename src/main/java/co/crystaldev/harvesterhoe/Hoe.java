package co.crystaldev.harvesterhoe;

import co.crystaldev.alpinecore.AlpinePlugin;
import co.crystaldev.alpinecore.framework.config.ConfigManager;
import lombok.Getter;

public class Hoe extends AlpinePlugin {

    @Getter
    private static Hoe instance;
    {
        instance = this;
    }

}
