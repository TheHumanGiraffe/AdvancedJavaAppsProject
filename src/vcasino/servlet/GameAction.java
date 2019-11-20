package vcasino.servlet;

public class GameAction {
	public String action="";
	public String arg0="";
	public String arg1;
	public String arg2;
	public String arg3;
	public String arg4;
	
	@Override
	public String toString() {
		return action + arg0;
	}
}
