/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import objects.GB;
import user.UserData;


public class DBManager {
    Connection conn;
    GB [] gbs;
    ArrayList <GB> tempList = new ArrayList<>();
    UserData ud;
    String sql;
    
    public DBManager(){//constructor just makes sure the connection can be made
        try{
          conn = DriverManager.getConnection("jdbc:sqlite:foe_data.db");
          System.out.println("Success Connecting to DB!");
        }
        catch(SQLException e){e.printStackTrace(System.err);}
    }
    
    public void read(String table, DefaultTableModel dtm){
        try{
            Statement s = conn.createStatement();
            s.setQueryTimeout(30); 
            ResultSet rs = s.executeQuery("select * from "+table);
            while(rs.next()){
               dtm.addRow(new Object[]{ "ss", rs.getInt("level"),rs.getInt("total_fp"), rs.getInt("p1"), rs.getInt("p2"), rs.getInt("p3"), rs.getInt("p4"), rs.getInt("p5")});
            }
        }
        catch(SQLException e){e.printStackTrace(System.err);}
    }
    
    public GB [] readGBS(){//generate a list of Great Buildings from db data (used for gbList on GUI)
        try{
            Statement s = conn.createStatement();
            s.setQueryTimeout(30); 
            ResultSet rs = s.executeQuery("select * from age_gb");
            while(rs.next()){
                tempList.add(new GB(rs.getString("age"), rs.getString("gb"), rs.getString("gb_short"), rs.getInt("gb_last_lvl"), rs.getString("img_source")));//(String era, String name, String shortForm, int last_lvl, String imgSrc)
            }
        }
        catch(SQLException e){e.printStackTrace();}
        gbs = new GB[tempList.size()];
        for(int i=0; i<tempList.size(); i++)
            gbs[i] = tempList.get(i);
        return gbs;
    }
    
    public String getLast_gb_image(String lastGB){//used for img_gb on GUI on application start
        String response = "";
        try{
            Statement s = conn.createStatement();
            s.setQueryTimeout(30); 
            ResultSet rs = s.executeQuery("select * from age_gb WHERE gb_short = '"+lastGB+"'");
            while(rs.next()){
                response = rs.getString("img_source");
            }
        }
        catch(SQLException ex){ ex.printStackTrace(); }
        return response;
    }
    
    public String getLast_gb(String lastGB){
        String response = "";
        try{
            Statement s = conn.createStatement();
            s.setQueryTimeout(30); 
            ResultSet rs = s.executeQuery("select * from age_gb WHERE gb_short = '"+lastGB+"'");
            while(rs.next()){
                response = rs.getString("gb");
            }
        }
        catch(SQLException ex){ ex.printStackTrace(); }
        return response;
    }
    
    
    
    public UserData getUserData(){
        try{
            Statement s = conn.createStatement();
            s.setQueryTimeout(30); 
            ResultSet rs = s.executeQuery("select * from user_data");
            while(rs.next()){
                ud = new UserData(rs.getString("prefix"), rs.getInt("shortForm"), rs.getInt("showPositions"), rs.getString("lastGb"));
            }
        }
        catch(SQLException e){e.printStackTrace();}
        return ud;
    }
    
    /* There was a show/hide contribution checkbox earlier however functioanlity doesn't seem all that useful (hence 2-contribution)*/
    public void writeUserData(int state, UserData data){//0 - prefix, 1-shortForm, 2-contribution, 3-gb, 4 - pos
        try{
            Statement s = conn.createStatement();
            switch(state){
                case 0: sql = "UPDATE user_data SET prefix = '"+data.getPre()+"' WHERE id = 1"; break;
                case 1: sql = "UPDATE user_data SET shortForm = "+data.getShort()+" WHERE id = 1"; break;
                case 3: sql = "UPDATE user_data SET lastGb = '"+data.getGB()+"' WHERE id = 1"; break;
                case 4: sql = "UPDATE user_data SET showPositions = "+data.getPos()+" WHERE id = 1"; break;
            }
            var query = this.conn.prepareStatement(sql);
            query.executeUpdate();
            System.out.println(sql);
        }
        catch(SQLException e){e.printStackTrace(System.err);}
    }
    
    public void update_gb_lvl(String gb, int lvl){//called when user clicks row/button on table to generate string
        try{
            Statement s = conn.createStatement();
            sql = "UPDATE age_gb SET gb_last_lvl = "+lvl+" WHERE gb_short='"+gb+"'";
            System.out.println(sql);
            var query = this.conn.prepareStatement(sql);
            query.executeUpdate();
        }
        catch(SQLException e){e.printStackTrace(System.err);}
    }
    
    public int get_gb_lvl(String gb){
        int value = -1;
        try{
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery("select * from age_gb WHERE gb_short ='"+gb+"'");
            while(rs.next()){
                value = rs.getInt("gb_last_lvl");
            }
        }
        catch(SQLException e){e.printStackTrace();}
        return value;
    }
    
}
