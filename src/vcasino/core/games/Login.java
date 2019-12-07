package vcasino.core.games;
import vcasino.servlet.GameAction;

import java.sql.ResultSet;

import javax.websocket.Session;

import vcasino.core.exceptions.RulesException;
import vcasino.db.DatabaseConnection;

public class Login {
	
	private String username="";
	private String sessionId="";
	private boolean isValid=false;
	
	public String newLogin(GameAction action) {
		DatabaseConnection db = new DatabaseConnection();
		System.out.println(action.arg0);
		System.out.println(action.arg1);
		if (!action.arg2.equals(action.arg1)) {
			System.out.println("someone tried to do something that was an invalid login, but we stopped him (or her) with superior code.");
			isValid = false;
			return null;
		}

		String mysqlQuery = "Insert into player Values(\""+action.arg0+"\",\""+action.arg1+"\",1000,0,0);";
		try {
			System.out.println(mysqlQuery);
			db.executeQuery(mysqlQuery);
			username = action.arg0;
			isValid = true;
			return "Success";
		} catch (com.mysql.cj.jdbc.exceptions.CommunicationsException ce) {
        	System.out.println("Error: " + ce);
        	//FIXME: in case this was a connection error, RECONNECT!
        } catch (RulesException e){
			e.printStackTrace();
			isValid = false;
			return null;
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String login(GameAction action, Session s) {
		DatabaseConnection db = new DatabaseConnection();
		String mysqlQuery = "select password from player where name = \""+action.arg0+"\";";
		System.out.println(mysqlQuery);
		try {
			ResultSet result = db.executeQuery(mysqlQuery);
			if (result.next()) {
				String password = result.getString(1);
				if (password.equals(action.arg1)){
					username = action.arg0;
					sessionId = (String) s.getUserProperties().get("id");
					isValid = true;
					return "Success";
				}
			}
		} catch (com.mysql.cj.jdbc.exceptions.CommunicationsException ce) {
        	System.out.println("Error: " + ce);
        	//FIXME: in case this was a connection error, RECONNECT!
        } catch (RulesException e){
			System.out.println("someone tried to do something that was an invalid login, but we stopped him (or her) with superior code.");
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean isValidLogin() {
		return isValid;
	}
	
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	public String getSessionId() {
		return sessionId;
	}
	
	public String getUsername() {
		return username;
	}
}
