package config.db;

import config.db.exceptions.DbException;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DB {

    private static Connection conn = null;

    public static Connection getConnection(){

        try{
            if(conn == null){
                Properties props = loadProperties();
                String url = props.getProperty("dburl");
                conn = DriverManager.getConnection(url, props);
                System.out.println("Connected to DataBase!!");
            }
        } catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        return conn;
    }

    public static void closeConnection(){

        try{
            if(conn!=null){
                conn.close();
            }
        }
        catch (SQLException e){
            throw new DbException(e.getMessage());
        }
        System.out.println("Connection closed!!");
    }

    public static void closeStatement(Statement st){
        try {
            if(st!=null)
                st.close();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    public static void closeResultSet(ResultSet rs){
        try {
            if(rs!=null)
                rs.close();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    private static Properties loadProperties() {

        try(FileInputStream fs = new FileInputStream("src/config/db/db.properties")){
            Properties props = new Properties();
            props.load(fs);
            return props;
        }
        catch(IOException e){
            throw  new DbException(e.getMessage());
        }
    }
}
