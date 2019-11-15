//package vcasino.client;
import javax.xml.transform.Result;
import java.sql.*;

public class DatabaseConnection {
    static final String JDBC_DRIVER = "org.postgresql.Driver"; //dont know if we need this exactly or not
    static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/casino?user=casinomanager&password=password";
    static final String USER = "casinomanager";
    static final String PASS = "password";

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        ResultSet x = executeQuery("Select * from game");
        System.out.println(x);
        executeQuery("Insert into game(id,gameName) VALUES ('7', 'poker')");
        ResultSet y = executeQuery("Select * from game");
        System.out.println(y);
    }

    public static ResultSet executeQuery(String statement) throws ClassNotFoundException, SQLException {
        Class.forName(JDBC_DRIVER);
//        System.out.println("Connecting to database...");
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
//        System.out.println("Creating database...");
        ResultSet rs = null;
        try{
            Statement stmt = conn.createStatement();
            if (!(statement.toLowerCase().startsWith("insert") || statement.toLowerCase().startsWith("update"))){
                rs = stmt.executeQuery(statement);
            }
            else{
                stmt.executeQuery(statement);
            }
        }
        catch(Exception e){
            System.out.println("Error: " + e);
            e.printStackTrace();
        }
        finally {
            conn.close();
            return rs;
        }
    }
}