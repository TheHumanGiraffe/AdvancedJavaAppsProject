package vcasino.decoder;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;

import vcasino.core.events.GameEvent;

public class GameEventDecoder implements Decoder.Text<GameEvent>{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(EndpointConfig arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public GameEvent decode(String s) throws DecodeException {
		Gson gson = new Gson();
		return gson.fromJson(s, GameEvent.class);
	}

	@Override
	public boolean willDecode(String arg0) {
		// TODO Auto-generated method stub
		return true;
	}

}
