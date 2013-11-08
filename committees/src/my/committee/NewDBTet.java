package my.committee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NewDBTet {

    public static void main(String[] args) throws InstantiationException, SQLException, IllegalAccessException {
        try {
            System.out.println("MySQL Connect Example.");
            Connection conn = null;
            String url = "jdbc:mysql://localhost:3306/";
            String dbName = "foc_group3";
            String driver = "com.mysql.jdbc.Driver";
            String userName = "username";
            String password = "password";

                Class.forName(driver).newInstance();
                conn = DriverManager.getConnection(url + dbName, userName, password); //breaks on this line
                System.out.println("Connected to the database");
                conn.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(NewDBTet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}