import java.sql.Connection;

public class TestDB {
    public static void main(String[] args) {
        Connection con = DBconnection.getConnection();
        if (con != null) {
            System.out.println("Database Connected!");
        } else {
            System.out.println("Connection Failed!");
        }
    }
}
