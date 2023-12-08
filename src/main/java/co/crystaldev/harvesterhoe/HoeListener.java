package co.crystaldev.harvesterhoe;

import co.crystaldev.alpinecore.AlpinePlugin;
import co.crystaldev.alpinecore.framework.engine.AlpineEngine;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Map;

public class HoeListener extends AlpineEngine {

    protected HoeListener(AlpinePlugin plugin) {
        super(plugin);
    }

    @EventHandler
    private void onBlockBreak(BlockBreakEvent event) {
        HoeConfig config = this.plugin.getConfiguration(HoeConfig.class);
        Block block = event.getBlock();
        Player player = event.getPlayer();
        ItemStack handItem = player.getInventory().getItemInMainHand();
        Material cropType = block.getType();
        if (handItem.getType().equals(Material.DIAMOND_HOE)) {
            ItemMeta meta = handItem.getItemMeta();
            if (meta != null) {
                NamespacedKey key = new NamespacedKey(plugin, "hoe_type");
                PersistentDataType<Integer, Integer> dataType = PersistentDataType.INTEGER;
                Integer hoeType = meta.getPersistentDataContainer().get(key, dataType);

                if (hoeType != null) {
                    Map<Material, Double> cropPrices = config.materialToAmount;
                    if (cropPrices.containsKey(cropType)) {
                        Ageable data = (Ageable) block.getBlockData();

                        if (data.getAge() == data.getMaximumAge()) {
                            event.setCancelled(true);
                            //player.sendMessage("You broke a block with a diamond hoe!");
                            block.setType(cropType);

                            double multiplier = switch (hoeType) {
                                case 1 -> 1.0;
                                case 2 -> 2.0;
                                case 5 -> 5.0;
                                default -> 1.0; // Default multiplier if the tag is not present or unknown
                            };

                            // Get crop price based on crop type
                            double cropPrice = cropPrices.getOrDefault(cropType, 0.0);
                            //double cropPrice = cropPrices.getOrDefault(cropType.toString(), 0.0);

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
        } //else if (cropPrices.containsKey(cropType)) {
            //event.setCancelled(true);
            //Components.send(player, config.noHoeMessage.build());

        //}

    }
}
