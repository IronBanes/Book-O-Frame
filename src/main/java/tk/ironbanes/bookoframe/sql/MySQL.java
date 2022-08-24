package tk.ironbanes.bookoframe.sql;

import org.bukkit.Bukkit;
import tk.ironbanes.bookoframe.BookOFrame;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {

    BookOFrame plugin = BookOFrame.getPlugin(BookOFrame.class);

    private String host = plugin.host;
    private String port = plugin.port;
    private String database = plugin.database;
    private String username = plugin.username;
    private String password = plugin.password;

    private Connection connection;

    public boolean isConnected(){
        return(connection == null ? false : true);
    }

    public void connect() throws ClassNotFoundException, SQLException {
        if(!isConnected()){
            Bukkit.getLogger().info("jdbc:mysql://"+host+":"+port+"/"+database+"?useSSL=false"+username+password);
            connection = DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/"+database+"?useSSL=false",username,password);
        }
    }

    public void disconnect(){
        if(isConnected()){
            try{
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection() {
        return connection;
    }

}




















