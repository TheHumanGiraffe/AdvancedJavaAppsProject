package vcasino.db;

import java.sql.*;

/**
 * 
 * @author Morgan Patterson
 *
 */
public class DatabaseConnection {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver"; //dont know if we need this exactly or not
    static final String DB_URL = "jdbc:mysql://localhost/casino?user=casinomanager&password=password";
    static final String USER = "casinomanager";
    static final String PASS = "password";

    Connection conn;
    
    public DatabaseConnection() {
    	try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    }
    
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
    	DatabaseConnection connection = new DatabaseConnection();
    	
    	connection.connect();
        ResultSet x = connection.executeQuery("Select * from game");
        System.out.println(x);
        connection.executeQuery("Insert into game(id,gameName) VALUES ('7', 'poker')");
        ResultSet y = connection.executeQuery("Select * from game");
        System.out.println(y);
        connection.disconnect();
    }
    
    public void connect() throws SQLException {
//      System.out.println("Connecting to database...");
    	conn = DriverManager.getConnection(DB_URL, USER, PASS);
    }
    
    public void disconnect() throws SQLException {
    	conn.close();
    }
    
    public ResultSet executeQuery(String statement) throws SQLException {
        

        
//        System.out.println("Creating database...");
        ResultSet rs = null;
        try {
            Statement stmt = conn.createStatement();
            if (!(statement.toLowerCase().startsWith("insert") || statement.toLowerCase().startsWith("update"))){
                rs = stmt.executeQuery(statement);
            }
            else{
                stmt.executeUpdate(statement);
            }
        } catch(SQLException e){
            System.out.println("Error: " + e);
            e.printStackTrace();
            //FIXME: in case this was a connection error, RECONNECT!
        }
        
        return rs;
    }
}
