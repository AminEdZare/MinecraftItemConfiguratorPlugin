// This class describes the tag command.
// The tag command allows you to describe an item.
// If the description is very long, the command splits
// the description into multiple lines.
package me.anhaamin.namecraft.commands;

// Imports necessary libraries

import me.anhaamin.namecraft.NameCraft;
import org.bukkit.Color;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TagCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label,
                             String[] args) {
        // Checks whether sender is an in-game player
        if (sender instanceof Player) {
            Player p = (Player) sender;
            // sets item being renamed to item in player's hand
            ItemStack currentItem = p.getInventory().getItemInMainHand();
            // validates command
            if (NameCraft.validate(p, args, "Format: /tag [description]",
                    currentItem)) {
                return true;
            }
            // sets item description
            attemptTag(p, String.join(" ", args), currentItem);

        } else if (sender instanceof ConsoleCommandSender) {
            System.out.println("You have to use this command in-game, " +
                    "silly goose!"); // Checks if sender used the console
        } else if (sender instanceof BlockCommandSender) {
            System.out.println("You have to be a player to use this command, " +
                    "silly goose!"); // Checks if sender used a command block
        }
        return true;
    }

    // Implements tagging functionality
    public static void attemptTag(Player p, String description, ItemStack item) {
        // Creates a String ArrayList used to describe items
        String[] splitDescription =
                splitLines(description); // splits into multiple lines
        List<String> tag = new ArrayList<>(Arrays.asList(splitDescription));
        // Adds lore to item
        ItemMeta currentItemMeta = item.getItemMeta();
        assert currentItemMeta != null;
        currentItemMeta.setLore(tag);
        item.setItemMeta(currentItemMeta);
        // Success message
        p.sendTitle("Successfully added description!", "Interesting story!",
                10, 70, 20);
        NameCraft.fireworks(p, Color.RED);
    }

    // Splits lore into multiple lines if it is too long
    public static String[] splitLines(String description) {
        String[] splitTag;
        // Splits if lore is longer than threshold
        if (description.length() > 30) {
            splitTag = description.split("(?<=\\G.{30})"); // regex provided
            // by Techie Delight
        } else {
            splitTag = new String[]{description};
        }
        return splitTag;
    }
}
