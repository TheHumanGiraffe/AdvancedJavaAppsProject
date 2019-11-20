package vcasino.servlet;

public class GameAction {
	public String player="";
	public String name="";
	public String arg0="";
	public String arg1;
	public String arg2;
	public String arg3;
	public String arg4;
	
	@Override
	public String toString() {
		return player + name + arg0;
	}
}
