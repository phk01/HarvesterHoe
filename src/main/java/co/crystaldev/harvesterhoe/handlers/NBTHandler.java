package co.crystaldev.harvesterhoe.handlers;

import co.crystaldev.alpinecore.AlpinePlugin;
import co.crystaldev.alpinecore.framework.engine.AlpineEngine;
import co.crystaldev.harvesterhoe.config.HoeConfig;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class NBTHandler extends AlpineEngine{
    public NBTHandler(AlpinePlugin plugin) {
        super(plugin);
    }

    public void updateNBT (ItemStack itemStack, double amount) {
        HoeConfig config = this.plugin.getConfiguration(HoeConfig.class);
        ItemMeta meta = itemStack.getItemMeta();
        List<String> retrievedLore = config.getLore();
        List<String> modifiedLore = new ArrayList<>();
        if (meta != null) {
            NamespacedKey harvested = new NamespacedKey(plugin, "harvested");
            PersistentDataContainer container = meta.getPersistentDataContainer();
            if (container.has(harvested, PersistentDataType.DOUBLE)) {
                double cropsHarvested = container.get(harvested, PersistentDataType.DOUBLE);
                container.set(harvested, PersistentDataType.DOUBLE, ( cropsHarvested + amount));

                for (String lore : retrievedLore) {
                    String updatedLore = lore.replace("%harvested%", cropsHarvested + "");
                    modifiedLore.add(updatedLore);
                }
                    meta.setLore(modifiedLore);
                    itemStack.setItemMeta(meta);

            } else {
                container.set(harvested, PersistentDataType.DOUBLE, amount);
                for (String lore : retrievedLore) {
                    String updatedLore = lore.replace("%harvested%", amount + "");
                    modifiedLore.add(updatedLore);
                }

                meta.setLore(modifiedLore);
                itemStack.setItemMeta(meta);
            }
        } else {
            System.out.println("Meta is null");
        }

    }

    public void createNBT (ItemStack itemStack, int hoeType) {
        HoeConfig config = this.plugin.getConfiguration(HoeConfig.class);
        List<String> retrievedLore = config.getLore();
        List<String> modifiedLore = new ArrayList<>();
        NamespacedKey harvested = new NamespacedKey(plugin, "harvested");
        NamespacedKey key = new NamespacedKey(plugin, "hoe_type");
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(harvested, PersistentDataType.DOUBLE, 0.0);
        container.set(key, PersistentDataType.INTEGER, hoeType);

        for (String lore : retrievedLore) {
            String updatedLore = lore.replace("%harvested%", "0");
            updatedLore = updatedLore.replace("%multiplier%", hoeType + "");

            modifiedLore.add(updatedLore);
        }
        meta.setLore(modifiedLore);
        meta.setDisplayName(hoeType + "x Gem Hoe");
        meta.addEnchant(Enchantment.DURABILITY, 0, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemStack.setItemMeta(meta);

    }

}
