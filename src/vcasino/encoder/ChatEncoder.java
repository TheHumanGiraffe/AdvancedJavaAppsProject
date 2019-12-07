package vcasino.encoder;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;

import vcasino.core.events.ChatEvent;

/*
 * Encodes chat functions into a json format.
 */
public class ChatEncoder implements Encoder.Text<ChatEvent> {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(EndpointConfig arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String encode(ChatEvent object) throws EncodeException {
		Gson gson = new Gson();
		return gson.toJson(object);
	}

}
