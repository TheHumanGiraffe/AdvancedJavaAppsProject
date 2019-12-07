package vcasino.core.games;
import vcasino.servlet.GameAction;

import java.sql.ResultSet;

import vcasino.core.exceptions.RulesException;
import vcasino.db.DatabaseConnection;

public class Login {
	
	/*
	 * Pushes new login information to the database
	 */
	public static String newLogin(GameAction action) {
		DatabaseConnection db = new DatabaseConnection();
		System.out.println(action.arg0);
		System.out.println(action.arg1);
		if (!action.arg2.equals(action.arg1)) {return "loginError";}

		String mysqlQuery = "Insert into player Values(\""+action.arg0+"\",\""+action.arg1+"\",1000,0,0);";
		System.out.println(mysqlQuery);
		try {
			db.executeQuery(mysqlQuery);
		}
		catch (RulesException e){
			System.out.println("someone tried do do something that was an invalid login");
			return "loginError";
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return "Success";
	}
	
	/***
	 * Signs a user into the system
	 */
	public static String login(GameAction action) {
		DatabaseConnection db = new DatabaseConnection();
			String mysqlQuery = "select password from player where name = \""+action.arg0+"\";";
			System.out.println(mysqlQuery);
			try {
				//tests if the query works
				ResultSet result = db.executeQuery(mysqlQuery);
				
				if (result.next()) {
					String password = result.getString(1);
					if (password.equals(action.arg1)){
						return "Success";
					}
				}
			}
			catch (RulesException e){
				System.out.println("someone tried do do something that was an invalid login");
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		return "loginError";
	}

}
