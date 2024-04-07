import java.sql.*;
public class Database_Test {
    public static void connect() {
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:C:/sqlite/Library.db";
            // create a connection to the book
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

            //get info
            String sqlInfo = "SELECT Barcode, Title, Author FROM Book";
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sqlInfo);

            while (rs.next()) {
                System.out.println(rs.getString(
                        "Barcode") + "\t" +
                        rs.getString("Title") + "\t" +
                        rs.getString("Author"));
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        connect();
    }
}
