package vcasino.core.events;

import vcasino.core.Player;

public class ChatEvent extends GameEvent {
	
	protected String text;
	
	public ChatEvent(Player by, String text) {
		super(by);
		this.setAction("chat");
		this.text = text;
	}

	@Override
	public String toJSONString() {
		return "{\n \"ChatEvent\": {\n  owner: "+eventOwner.getId()+",\n  text: \""+text+"\"\n }\n}";
	}

}
