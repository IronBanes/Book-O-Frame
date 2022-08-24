package tk.ironbanes.bookoframe;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CommandBOF implements CommandExecutor {
    List<String> Lore = new ArrayList<>();
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        Player p = (Player) sender;
        BookOFrame plugin = BookOFrame.getPlugin(BookOFrame.class);

        //checks if the player who typed the command has permission to type the command
        if (p.hasPermission("bookoframe.command")){
            if(cmd.getName().equalsIgnoreCase("bookoframe")){
                //checks if the send of the command was a player
                if(sender instanceof Player){

                    //creates and item frame and create a item meta variable for it
                    ItemStack itemframe = new ItemStack(Material.ITEM_FRAME);
                    ItemMeta itemmeta = itemframe.getItemMeta();

                    //sets the lore, name, and enchant, onto the itemframe
                    Lore.add(ChatColor.BLUE + (plugin.item_lore));//value gotten from the config/main class
                    itemmeta.setLore(Lore);
                    itemmeta.setDisplayName(plugin.item_name);//value gotten form the config/main class
                    itemmeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
                    itemframe.setItemMeta(itemmeta);//sets the itemmeta to the itemframe
                    itemframe.addUnsafeEnchantment(Enchantment.DURABILITY, 1);



                    //gives/updates the players inventory with the item frame
                    p.getInventory().addItem(itemframe);
                    p.updateInventory();

                    //clears the lore to prevent it from making the item have multiple lines of lore
                    Lore.clear();
                }
            }
        }else{
            p.sendMessage("You do not have permission to run this command");
        }
        return false;
    }
}