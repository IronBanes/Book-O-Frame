package tk.ironbanes.bookoframe.sql;

import org.bukkit.Bukkit;
import tk.ironbanes.bookoframe.BookOFrame;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class SQLGetter {
    private BookOFrame plugin;

    public SQLGetter(BookOFrame plugin){
        this.plugin = plugin;
    }

    public void createTable() {
        PreparedStatement ps;
        try{

            ps = plugin.SQL.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS "+ plugin.table
                    +" (BLOCKNAME VARCHAR(900),WORLD VARCHAR(30),X DOUBLE(30,5),Y DOUBLE(30,5),Z DOUBLE(30,5))");
            ps.executeUpdate();
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void createBlock(String blockname, String world, Double x, Double y, Double z){
        try{
            if(!exists(blockname)){
                Bukkit.getLogger().info("CreateBlock");
                PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("INSERT INTO "+plugin.table
                        +" (BLOCKNAME,WORLD,X,Y,Z) VALUE (?,?,?,?,?)");
                //inputs all the values passed to the sub to the table
                ps.setString(1,blockname);
                ps.setString(2,world);
                ps.setDouble(3,x);
                ps.setDouble(4,y);
                ps.setDouble(5,z);
                ps.executeUpdate();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void deleteBlock(String blockname){
        try{
            Bukkit.getLogger().info("Delete Block");
            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("DELETE FROM "+plugin.table + " WHERE BLOCKNAME= ?");
            ps.setString(1,blockname);
            ps.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public boolean exists(String blockname){
        try{

            PreparedStatement ps = plugin.SQL.getConnection().prepareStatement("SELECT * FROM "+plugin.table+" WHERE BLOCKNAME=?");
            ps.setString(1, blockname);

            ResultSet results = ps.executeQuery();
            if (results.next()){
                Bukkit.getLogger().info("Exists Block");
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
