
package my.committee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Database {

    private Connection conn;

    public Database() throws InstantiationException, SQLException, IllegalAccessException {
        conn = null;
        init();
    }

    public void close() throws InstantiationException, SQLException, IllegalAccessException {
        try {
            conn.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void init() throws InstantiationException, SQLException, IllegalAccessException {
        try {
            System.out.println("MySQL Connect Example.");
            String url = "jdbc:derby://localhost:1527/";
            String dbName = "db";
            String driver = "org.apache.derby.jdbc.EmbeddedDriver";
            String userName = "admin";
            String password = "password";

            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(url + dbName, userName, password);
            System.out.println("Connected to the database");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void addComm(String name, String desc, String members, String chair) {
        try {
            Statement s = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String query = "INSERT INTO APP.COMMS (NAME, DESCRIPTION, CURRENT_MEMBERS, CHAIR)"
                    + " VALUES (?, ?, ?, ?)";
            PreparedStatement psInsert = conn.prepareStatement(query);
            //psInsert.setInt(1, 1956);
            psInsert.setString(1, name);
            psInsert.setString(2, desc);
            psInsert.setString(3, members);
            psInsert.setString(4, chair);
            psInsert.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void addProf(String first_name, String last_name, String department, String school,
            String current_committees, String past_committees, String requested_committees) {
        try {
            Statement s = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String query = "INSERT INTO APP.PROFS (FIRST_NAME, LAST_NAME, DEPARTMENT, SCHOOL, "
                    + "CURRENT_COMMITTEES, PAST_COMMITTEES, REQUESTED_COMMITTEES)"
                    + " VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement psInsert = conn.prepareStatement(query);
            //psInsert.setInt(1, 1956);
            psInsert.setString(1, first_name);
            psInsert.setString(2, last_name);
            psInsert.setString(3, department);
            psInsert.setString(4, school);
            psInsert.setString(5, current_committees);
            psInsert.setString(6, past_committees);
            psInsert.setString(7, requested_committees);
            psInsert.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

     public String search(String input) {
         if (input.equals("")) {
             return null;
         }
         try {
            String result = "";
            Statement s = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String query = "SELECT LAST_NAME, FIRST_NAME FROM APP.PROFS WHERE LOWER(FIRST_NAME) "
                    + "LIKE TRIM(LOWER('" + input + "%'))";
            query += " OR LOWER(LAST_NAME) LIKE TRIM(LOWER('" + input + "%'))";
            query += " OR LOWER(DEPARTMENT) LIKE TRIM(LOWER('" + input + "%'))";
            query += " OR LOWER(SCHOOL) LIKE TRIM(LOWER('" + input + "%'))";
            query += " OR LOWER(CURRENT_COMMITTEES) LIKE TRIM(LOWER('" + input + "%'))";
            query += " OR LOWER(PAST_COMMITTEES) LIKE TRIM(LOWER('" + input + "%'))";
            query += " OR LOWER(REQUESTED_COMMITTEES) LIKE TRIM(LOWER('" + input + "%'))";
            ResultSet rs = s.executeQuery(query);
            while (rs.next()) {
                result += rs.getString("LAST_NAME") + ", " + rs.getString("FIRST_NAME") + "\n";
            }
            query = "SELECT NAME FROM APP.COMMS WHERE LOWER(NAME) LIKE TRIM(LOWER('" + input + "%'))";
            query += " OR LOWER(DESCRIPTION) LIKE TRIM(LOWER('" + input + "%'))";
            query += " OR LOWER(CURRENT_MEMBERS) LIKE TRIM(LOWER('" + input + "%'))";
            query += " OR LOWER(CHAIR) LIKE TRIM(LOWER('" + input + "%'))";
            rs = s.executeQuery(query);
            while (rs.next()) {
                result += rs.getString("NAME") + "\n";
            }
            return result;
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }
    }
}