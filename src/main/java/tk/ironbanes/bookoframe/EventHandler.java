package tk.ironbanes.bookoframe;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class EventHandler implements Listener
{
    BookOFrame plugin = BookOFrame.getPlugin(BookOFrame.class);

    //private BookOFrame plugin;



    //Checks if the entity the player is clicking on is an Item Frame
    @org.bukkit.event.EventHandler
    public void onPlayerEntityInteract(PlayerInteractEntityEvent event)
    {
        Player p = event.getPlayer();
        if (p.hasPermission("bookoframe.use")){
            Entity entity = event.getRightClicked();

            //checks to see if the Right Clicked entity is an item frame
            //if the entity was an item frame it will go throught the if
            //otherwise it passes
            if(entity instanceof ItemFrame)
            {

                //if the entity was an item frame it will create a copy of the book in the item frame.
                ItemStack ItemInFrame = ((ItemFrame) entity).getItem();
                if (ItemInFrame.getType() == Material.WRITTEN_BOOK)
                {

                    //gets the values needed for checkSQLforBlock()
                    World world = entity.getLocation().getBlock().getWorld();
                    double x = entity.getLocation().getX();
                    double y = entity.getLocation().getY();
                    double z = entity.getLocation().getZ();
                    String blockname = world.getName()+ x +","+ y +","+ z;

                    //checks if the itemframe is in the list of copy frames
                    if(plugin.data.exists(blockname)){
                        //adds the item and updates the inventory of the player
                        p.getInventory().addItem(ItemInFrame);
                        p.updateInventory();
                    }
                }
            }
        }

    }
    @org.bukkit.event.EventHandler
    public void onPlaceFrame(HangingPlaceEvent event)
    {
        Player player = event.getPlayer();

        //checks if the player has permission to place an item frame that can copy
        if(player.hasPermission("bookoframe.place")){
            Entity entity = event.getEntity();

            //checks if the entity was an item frame
            if (entity instanceof ItemFrame) {
                ItemStack itemframe = player.getInventory().getItemInMainHand();//gets the item fram data from the player
                //checks for itemmeta data on the item frame
                if (itemframe.hasItemMeta()) {
                    //compares the items meta data to the desired itemmeta/lore
                    ItemMeta itemMeta = itemframe.getItemMeta();
                    List<String> Lore = new ArrayList<>();
                    Lore.add(ChatColor.BLUE + (plugin.item_lore));
                    if (itemMeta != null) {

                        if (Lore.equals(itemMeta.getLore())) {
                            //if the item meta matches it get the entitys location
                            World world = entity.getLocation().getBlock().getWorld();
                            double x = entity.getLocation().getX();
                            double y = entity.getLocation().getY();
                            double z = entity.getLocation().getZ();
                            //blockname acts as our identification key for database
                            String blockname = world.getName() + x + "," + y + "," + z;
                            //with the blocks location you pass it to addBlockToSQL to add it to the
                            //sql database to keep track of blocks that can copy
                            plugin.data.createBlock(blockname, world.getName(), x, y, z);
                        }
                    }
                    //clears lore to prevent it from compounding
                    Lore.clear();
                }
            }
        }
    }
    @org.bukkit.event.EventHandler
    public void onBreakFrame(HangingBreakEvent event)
    {
        Entity entity = event.getEntity();
        //check if item frame
        if (entity instanceof ItemFrame){
            //gets entity location
            World world = entity.getLocation().getBlock().getWorld();
            double x = entity.getLocation().getX();
            double y = entity.getLocation().getY();
            double z = entity.getLocation().getZ();
            String blockname = world.getName()+ x +","+ y +","+ z;//identification key for sql
            //checks if the block is in the database
            if(plugin.data.exists(blockname)){
                //if the block is in the database the item frame is removed from the database
                plugin.data.deleteBlock(blockname);
            }
        }
    }
}