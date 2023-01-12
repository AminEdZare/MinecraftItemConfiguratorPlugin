// This class describes the recolor command.
// The recolor command sets a color to items that you gave a nickname to.
package me.anhaamin.namecraft.commands;

// Imports necessary libraries

import me.anhaamin.namecraft.NameCraft;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

// Implements the recolor command
public class RecolorCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label,
                             String[] args) {
        // Checks whether sender is an in-game player
        if (sender instanceof Player) {

            Player p = (Player) sender;
            // sets item being renamed to item in player's hand
            ItemStack currentItem = p.getInventory().getItemInMainHand();
            // validates command
            if (NameCraft.validate(p, args, "Format: /recolor [color]",
                    currentItem)) {
                return true;
            }
            // attempts to recolor an item
            String attemptedColor = String.join("_", args);
            try {
                attemptColor(p, attemptedColor, currentItem);
            } catch (Exception e) {
                p.sendMessage(ChatColor.RED + attemptedColor +
                        " is an invalid color!"); // sends error
                // message if unsuccessful
                return true;
            }

        } else if (sender instanceof ConsoleCommandSender) {
            System.out.println("You have to use this command in-game, " +
                    "silly goose!"); // Checks if sender used the console
        } else if (sender instanceof BlockCommandSender) {
            System.out.println("You have to be a player to use this command, " +
                    "silly goose!"); // Checks if sender used a command block
        }
        return true;
    }

    // Implements recoloring functionality
    public static void attemptColor(Player p, String argument, ItemStack item) {
        // Parses string into color object
        ChatColor color = ChatColor.valueOf(argument.toUpperCase());
        // Retrieves meta of an item
        ItemMeta currentItemMeta = item.getItemMeta();

        // Checks whether item already has a display name
        assert currentItemMeta != null;
        if (currentItemMeta.hasDisplayName()) {
            // Removes existing color of item
            String displayName = ChatColor.stripColor
                    (currentItemMeta.getDisplayName());
            // Recolors item with new color given by player
            currentItemMeta.setDisplayName(color + displayName);
            item.setItemMeta(currentItemMeta);
            p.getInventory().setItemInMainHand(item);
            p.updateInventory();
            // Success message
            p.sendTitle("Successfully recolored your item! ", "What a cute color!",
                    10, 70, 20);
            NameCraft.fireworks(p, Color.PURPLE);
        } else {
            // Errors out if item does not have a display name
            p.sendMessage(ChatColor.RED + "You must first customize your item with a name " +
                    "to color it!");
        }
    }
}
