package tk.ironbanes.bookoframe;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import tk.ironbanes.bookoframe.sql.MySQL;
import tk.ironbanes.bookoframe.sql.SQLGetter;

import java.sql.SQLException;

public final class BookOFrame extends JavaPlugin {


    public MySQL SQL;
    public SQLGetter data;
    public String host,database,username,password,port,table,item_name,item_lore;

    @Override
    public void onEnable() {
        // Plugin startup logic

        loadconfig();
        host = this.getConfig().getString("host");
        database = this.getConfig().getString("database");
        username = this.getConfig().getString("username");
        password = this.getConfig().getString("password");
        port = this.getConfig().getString("port");
        table = this.getConfig().getString("table");
        item_name = this.getConfig().getString("item-name");
        item_lore = this.getConfig().getString("item-lore");
        this.SQL = new MySQL();
        this.data = new SQLGetter(this);

        try {
            SQL.connect();
        } catch (ClassNotFoundException | SQLException e ) {
            Bukkit.getLogger().info("Database not Connected");
            e.printStackTrace();
        }

        if (SQL.isConnected()){
            Bukkit.getLogger().info("Database is connected");
            data.createTable();
            getServer().getPluginManager().registerEvents(new EventHandler(), this);
        }

        //getServer().getPluginManager().registerEvents(new EventHandler(), this);
        this.getCommand("bookoframe").setExecutor(new CommandBOF());





    }
    public void loadconfig(){
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        SQL.disconnect();
    }
}
