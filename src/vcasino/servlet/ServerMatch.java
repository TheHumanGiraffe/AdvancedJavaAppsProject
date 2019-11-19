package vcasino.servlet;

import java.util.ArrayList;

import javax.websocket.Session;

import vcasino.core.Match;
import vcasino.core.Ruleset;

public class ServerMatch extends Match {
	private ArrayList<Session> sessions;
	
	public ServerMatch(String id, Ruleset rules) {
		super(id, rules);
		
		sessions = new ArrayList<>();
	}
	
	public void addPlayer(Session session) {
		sessions.add(session);
	}
}
