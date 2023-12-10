package co.crystaldev.harvesterhoe;


import co.aikar.commands.annotation.*;
import co.crystaldev.alpinecore.AlpinePlugin;
import co.crystaldev.alpinecore.framework.command.AlpineCommand;
import co.crystaldev.alpinecore.util.Components;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import java.util.*;

@CommandAlias("hoe")
@Description("Get a harvester hoe.")
public class HoeCommand extends AlpineCommand {
    protected HoeCommand(AlpinePlugin plugin) {
        super(plugin);
    }

    @Default
    @CommandPermission("Harvesterhoe.get")
    public void get(CommandSender sender) {
        HoeConfig config = this.plugin.getConfiguration(HoeConfig.class);
        //HoeConfig config = this.plugin.getConfigManager().getConfig(HoeConfig.class);
        Player player = (Player) sender;
        giveCustomHoe(player, 1);
        Components.send(sender, config.giveMessage.build("sender", sender.getName()));
    }

    @Subcommand("give")
    @CommandCompletion("@players 1|2|5")
    @Syntax("<player> [multiplier]")
    @CommandPermission("Harvesterhoe.give")
    public void give(CommandSender sender, String player, int hoeType) {
        HoeConfig config = this.plugin.getConfiguration(HoeConfig.class);
        Player target = Bukkit.getPlayer(player);
        if (target == null) { Components.send(sender, config.noOnlineMessage.build("player", player)); return; }
        giveCustomHoe(target, hoeType);
        Components.send(sender, config.giveMessage.build("sender", sender.getName(), "multiplier", hoeType));
        Components.send(sender, config.giveOtherMessage.build("player", target.getName(), "multiplier", hoeType));
    }

    private void giveCustomHoe(Player player, int hoeType) {
        ItemStack customHoe = createCustomHoe(hoeType);

        player.getInventory().addItem(customHoe);
    }

    private ItemStack createCustomHoe(int hoeType) {
        HoeConfig config = this.plugin.getConfiguration(HoeConfig.class);

        ItemStack hoe = new ItemStack(Material.DIAMOND_HOE); // Customize material and properties as needed

        ItemMeta meta = hoe.getItemMeta();
        if (meta != null) {
            // Add lore or other customizations if desired
            //meta.setLore(Arrays.asList("Denne Gem Hoe giver ", hoeType + "x af dine tjente Gems"));
            // Setting custom NBT data using PersistentDataContainer
            PersistentDataType<String, String> dataType = PersistentDataType.STRING;
            NamespacedKey key = new NamespacedKey(plugin, "hoe_type");
            NamespacedKey harvested = new NamespacedKey(plugin, "harvested");
            double cropsharvested = 0;
            meta.getPersistentDataContainer().set(harvested, PersistentDataType.DOUBLE, cropsharvested);
            meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, hoeType);
            //meta.setLore(Arrays.asList("You have earned " + cropsharvested + " Gems"));

            //for (int i = 0; i < config.lore.size(); i++) {
            //    config.lore.set(i, config.lore.get(i).replace("%multiplier%", hoeType + ""));
            //    meta.setLore(config.lore);
            //}

            List<String> lore = config.getLore();
            for (int i = 0; i < lore.size(); i++) {
                lore.set(i, lore.get(i).replace("%multiplier%", hoeType + ""));
                meta.setLore(lore);
            }

            meta.setDisplayName(hoeType + "x Gem Hoe");
            meta.addEnchant(Enchantment.DURABILITY, 0, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            hoe.setItemMeta(meta);
        }

        return hoe;
    }

}
