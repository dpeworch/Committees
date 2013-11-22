
package my.committee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) throws InstantiationException, SQLException, IllegalAccessException {
        try {
            System.out.println("MySQL Connect Example.");
            Connection conn = null;
            String url = "jdbc:derby://localhost:1527/";
            String dbName = "db";
            String driver = "org.apache.derby.jdbc.EmbeddedDriver";
            String userName = "admin";
            String password = "password";

                Class.forName(driver).newInstance();
                conn = DriverManager.getConnection(url + dbName, userName, password);
                System.out.println("Connected to the database");
                conn.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}