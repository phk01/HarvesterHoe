package co.crystaldev.harvesterhoe;

import co.crystaldev.alpinecore.AlpinePlugin;
import lombok.Getter;

public class Hoe extends AlpinePlugin {

    @Getter
    private static Hoe instance;
    {
        instance = this;
    }

}
