package co.crystaldev.harvesterhoe;

import co.crystaldev.alpinecore.AlpinePlugin;
import co.crystaldev.alpinecore.framework.engine.AlpineEngine;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.block.Block;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;

public class HoeListener extends AlpineEngine {

    private final Map<Material, Material> cropSeedsMap;
    private final Map<String, Double> cropPrices;

    protected HoeListener(AlpinePlugin plugin) {
        super(plugin);
        cropSeedsMap = initializeCropSeedsMap(); // Initialize the crop and seeds mapping
        cropPrices = initializeCropPrices(); // Initialize crop prices
    }

    // Method to initialize the crop and seeds mapping
    private Map<Material, Material> initializeCropSeedsMap() {
        Map<Material, Material> map = new HashMap<>();
        // Add crop and seed pairs here
        map.put(Material.WHEAT, Material.WHEAT);
        map.put(Material.CARROTS, Material.CARROTS);
        map.put(Material.POTATOES, Material.POTATOES);
        // Add more crops and seeds as needed
        return map;
    }

    // Method to initialize crop prices
    private Map<String, Double> initializeCropPrices() {
        HoeConfig config = this.plugin.getConfigManager().getConfig(HoeConfig.class);
        Map<String, Double> prices = new HashMap<>();
        // Set default prices
        prices.put("WHEAT", config.wheatAmount);
        prices.put("CARROTS", config.carrotAmount);
        prices.put("POTATOES", config.potatoAmount);
        // Add more crop prices as needed
        return prices;
    }

    @EventHandler
    private void onBlockBreak(BlockBreakEvent event) {
        HoeConfig config = this.plugin.getConfigManager().getConfig(HoeConfig.class);
        Block block = event.getBlock();
        Player player = event.getPlayer();
        ItemStack handItem = player.getInventory().getItemInMainHand();

        if (handItem.getType().equals(Material.DIAMOND_HOE)) {
            ItemMeta meta = handItem.getItemMeta();
            if (meta != null) {
                NamespacedKey key = new NamespacedKey(plugin, "hoe_type");
                PersistentDataType<String, String> dataType = PersistentDataType.STRING;
                String hoeType = meta.getPersistentDataContainer().get(key, dataType);

                if (hoeType != null) {
                    Material cropType = block.getType();
                    if (cropSeedsMap.containsKey(cropType)) {
                        Ageable data = (Ageable) block.getBlockData();

                        if (data.getAge() == data.getMaximumAge()) {
                            event.setCancelled(true);
                            //player.sendMessage("You broke a block with a diamond hoe!");
                            block.setType(cropSeedsMap.get(cropType));

                            double multiplier = switch (hoeType) {
                                case "1" -> 1.0;
                                case "2" -> 2.0;
                                case "5" -> 5.0;
                                default -> 1.0; // Default multiplier if the tag is not present or unknown
                            };

                            // Get crop price based on crop type
                            double cropPrice = cropPrices.getOrDefault(cropType.toString(), 0.0);

                            // Calculate amount to give to the player
                            double amount = cropPrice * multiplier;

                            // Add the money
                            String command = "eco give " + player.getName() + " " + amount;
                            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);

                        } else {
                            event.setCancelled(true);
                        }
                    }
                }
            }
        }
        //event.setCancelled(true);
    }
}
