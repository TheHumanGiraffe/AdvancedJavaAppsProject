package vcasino.core.events;

public class ChatEvent extends GameEvent {
	
	protected String text;
	
	public ChatEvent(String text) {
		super(text, 0);
		this.setAction("chat");
		this.text = text;
	}

	

}
