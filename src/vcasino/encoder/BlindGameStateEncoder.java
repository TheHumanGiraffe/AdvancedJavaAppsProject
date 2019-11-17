package vcasino.encoder;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import vcasino.blind.BlindGameState;


public class BlindGameStateEncoder implements Encoder.Text<BlindGameState>{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(EndpointConfig arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String encode(BlindGameState blindGameState) throws EncodeException {
		Gson gson = new Gson();	
		//Null out other players
		blindGameState.setOtherPlayers();
				
		return gson.toJson(blindGameState);
	}

}
