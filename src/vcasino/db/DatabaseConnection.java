package vcasino.db;

import java.sql.*;

import vcasino.core.exceptions.RulesException;
import vcasino.servlet.VCasinoServerEndpoint;

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
    
    public void disconnect() throws SQLException {
    	conn.close();
    }
    
    public ResultSet executeQuery(String statement) throws SQLException, RulesException {
    	conn = DriverManager.getConnection(DB_URL, USER, PASS);
        System.out.println("Creating database...");
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
        	if (e.getErrorCode() == 1062) {
    			throw new RulesException("Login", "can't use a duplicate login", null);

        	}
            System.out.println("Error: " + e);
            e.printStackTrace();
            //FIXME: in case this was a connection error, RECONNECT!
        }
        
        return rs;
    }
}
