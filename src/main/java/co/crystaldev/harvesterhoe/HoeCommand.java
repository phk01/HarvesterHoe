package co.crystaldev.harvesterhoe;


import co.aikar.commands.annotation.*;
import co.crystaldev.alpinecore.AlpinePlugin;
import co.crystaldev.alpinecore.framework.command.AlpineCommand;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.UUID;

@CommandAlias("hoe")
@Description("Get a harvester hoe.")
public class HoeCommand extends AlpineCommand {

    /**
     * Locked down to prevent improper instantiation.
     * <p>
     * Commands are reflectively instantiated by the
     * framework automatically.
     *
     * @param plugin
     */
    protected HoeCommand(AlpinePlugin plugin) {
        super(plugin);
    }
    @Default
    @Syntax("<player>")
    @CommandCompletion("@player 1|2|5")
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        UUID uuid = player.getUniqueId();

        if (args.length == 0) {
            giveCustomHoe(player, "1");
            player.sendMessage("You've been given a Harvester Hoe!");
        } else {
            if (!player.hasPermission("Harvesterhoe.give")) {
                player.sendMessage("You don't have permission to give a Harvester Hoe!");
                return;
            }

            if (args.length < 2) {
                player.sendMessage("Usage: /hoe [player] [hoe multiplier: 1|2|5]");
                return;
            }

            Player target = plugin.getServer().getPlayer(args[0]);
            if (target == null) {
                player.sendMessage("That player is not online!");
                return;
            }

            String hoeVariant = args[1];
            if (!hoeVariant.equals("1") && !hoeVariant.equals("2") && !hoeVariant.equals("5")) {
                hoeVariant = "1";
                player.sendMessage("Invalid hoe type specified. Defaulting to Hoe 1.");
            }

            giveCustomHoe(target, hoeVariant);
            player.sendMessage("You've given " + target.getName() + " a Harvester Hoe!");
        }

    }

    private void giveCustomHoe(Player player, String hoeType) {
        ItemStack customHoe = createCustomHoe(hoeType);

        player.getInventory().addItem(customHoe);
        player.sendMessage("You've received a custom hoe!");
    }

    private ItemStack createCustomHoe(String hoeType) {
        ItemStack hoe = new ItemStack(Material.DIAMOND_HOE); // Customize material and properties as needed

        ItemMeta meta = hoe.getItemMeta();
        if (meta != null) {
            // Add lore or other customizations if desired
            meta.setLore(Arrays.asList("Denne Gem Hoe giver ", hoeType + "x af dine tjente Gems"));
            // Setting custom NBT data using PersistentDataContainer
            PersistentDataType<String, String> dataType = PersistentDataType.STRING;
            NamespacedKey key = new NamespacedKey(plugin, "hoe_type");
            meta.getPersistentDataContainer().set(key, dataType, hoeType);
            meta.setDisplayName("Gem Hoe");
            meta.addEnchant(Enchantment.DURABILITY, 0, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            hoe.setItemMeta(meta);
        }

        return hoe;
    }

}
