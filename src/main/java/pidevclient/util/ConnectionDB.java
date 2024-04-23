package pidevclient.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    Connection conn;
    final String url = "jdbc:mysql://localhost/pidev";
    final String user = "root";
    final String pwd = "";
    static ConnectionDB instance;

    private ConnectionDB(){
        try {
            conn = DriverManager.getConnection(url, user, pwd);
            System.out.println("connected");
        }catch(SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
    }
    public static ConnectionDB getInstance() {
        if (instance == null) {
            return instance = new ConnectionDB();
        }
        return instance;
    }

    public Connection getConn() {
        return conn;
    }
}
