package tk.ironbanes.bookoframe;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BookOFrame extends JavaPlugin {
    private Connection connection;
    public String host, database, username, password, table;
    public int port;



    @Override
    public void onEnable(){
        getServer().getPluginManager().registerEvents(new EventHandler(), this);
        //getServer().getPluginManager().registerEvents(new EventHandler().onPlayerEntityInteract();, this);
        this.getCommand("bookoframe").setExecutor(new CommandBOF());
        loadconfig();
        mysqlSetup();



    }
    public void loadconfig(){
        getConfig().options().copyDefaults(true);
        saveConfig();

    }

    public void mysqlSetup(){
        host = this.getConfig().getString("host");
        port = this.getConfig().getInt("port");
        database = this.getConfig().getString("database");
        username = this.getConfig().getString("username");
        password = this.getConfig().getString("password");
        table = this.getConfig().getString("table");
        try{
            synchronized (this){
                if(getConnection() != null && !getConnection().isClosed()){
                    return;
                }
                Class.forName("com.mysql.jdbc.Driver");
                setConnection(DriverManager.getConnection("jbdc:mysql://"+this.host+":"+ this.port+"/"
                        + this.database, this.username,this.password));
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"MYSQL CONNECTED");
            }

        }catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }


    }
    @Override
    public void onDisable() {

    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
