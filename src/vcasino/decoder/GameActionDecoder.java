package vcasino.decoder;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;

import vcasino.servlet.GameAction;

public class GameActionDecoder implements Decoder.Text<GameAction>{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(EndpointConfig arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public GameAction decode(String s) throws DecodeException {
		Gson gson = new Gson();
		return gson.fromJson(s, GameAction.class);
	}

	@Override
	public boolean willDecode(String arg0) {
		// TODO Auto-generated method stub
		return true;
	}

}

