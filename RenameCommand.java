// This class describes the rename command.
// The rename command allows you to give an item a nickname.
package me.anhaamin.namecraft.commands;

// Imports necessary libraries

import me.anhaamin.namecraft.NameCraft;
import org.bukkit.Color;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


// Implements the rename command
public class RenameCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label,
                             String[] args) {
        // Checks whether sender is an in-game player
        if (sender instanceof Player) {

            Player p = (Player) sender;
            // sets item being renamed to item in player's hand
            ItemStack currentItem = p.getInventory().getItemInMainHand();
            // validates command
            if (NameCraft.validate(p, args, "Format: /rename [name]",
                    currentItem)) {
                return true;
            }
            // renames item
            attemptRename(p, args, currentItem);

        } else if (sender instanceof ConsoleCommandSender) {
            System.out.println("You have to use this command in-game, " +
                    "silly goose!"); // Checks if sender used the console
        } else if (sender instanceof BlockCommandSender) {
            System.out.println("You have to be a player to use this command, " +
                    "silly goose!"); // Checks if sender used a command block
        }
        return true;
    }

    // Implements renaming functionality
    public static void attemptRename(Player p, String[] name, ItemStack item) {
        // Creates new display name
        ItemMeta currentItemMeta = item.getItemMeta();
        String displayName = String.join(" ", name);
        assert currentItemMeta != null;
        currentItemMeta.setDisplayName(displayName);
        item.setItemMeta(currentItemMeta);
        // Success message
        p.sendTitle("Successfully renamed your item!", "What a cool nickname!",
                10, 70, 20);
        NameCraft.fireworks(p, Color.FUCHSIA);
    }
}
