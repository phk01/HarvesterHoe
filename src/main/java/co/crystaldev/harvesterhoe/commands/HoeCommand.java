package co.crystaldev.harvesterhoe.commands;


import co.aikar.commands.annotation.*;
import co.crystaldev.alpinecore.AlpinePlugin;
import co.crystaldev.alpinecore.framework.command.AlpineCommand;
import co.crystaldev.alpinecore.util.Components;
import co.crystaldev.harvesterhoe.config.HoeConfig;
import co.crystaldev.harvesterhoe.handlers.NBTHandler;
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
        Components.send(sender, config.giveMessage.build("sender", sender.getName(), "multiplier", 1));
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
        ItemStack hoe = new ItemStack(Material.DIAMOND_HOE); // Customize material and properties as needed
        ItemMeta meta = hoe.getItemMeta();
        if (meta != null) {
            NBTHandler nbtHandler = new NBTHandler(plugin);
            nbtHandler.createNBT(hoe, hoeType);
        }

        return hoe;
    }

}
