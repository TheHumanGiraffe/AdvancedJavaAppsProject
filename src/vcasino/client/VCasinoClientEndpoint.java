//package vcasino.client;
//
//import java.net.URI;
//
//import javax.websocket.*;
//import javax.websocket.WebSocketContainer;
//
//@ClientEndpoint
//public class VCasinoClientEndpoint {
//
//	URI uri;
//	Session userSession = null;
//    private MessageHandler messageHandler;
//    
//    public VCasinoClientEndpoint(URI endpointURI) {
//        uri = endpointURI;
//	}
//
//    public void connect() throws Exception {
//    	WebSocketContainer container = ContainerProvider.getWebSocketContainer();
//    	container.connectToServer(this, uri);
//    }
//    
//    public boolean isConnected() {
//    	return (userSession != null);
//    }
//    
//    @OnOpen
//    public void onOpen(Session userSession) {
//        System.out.println("opening websocket");
//        this.userSession = userSession;
//    }
//    
//    @OnClose
//    public void onClose(Session userSession, CloseReason reason) {
//        System.out.println("closing websocket");
//        this.userSession = null;
//    }
//
//    @OnMessage
//    public void onMessage(String message) {
//        if (this.messageHandler != null) {
//            this.messageHandler.handleMessage(message);
//        }
//    }
//
//    public void addMessageHandler(MessageHandler msgHandler) {
//        this.messageHandler = msgHandler;
//    }
//
//    public void sendMessage(String message) {
//    	if(this.userSession != null)
//    		this.userSession.getAsyncRemote().sendText(message);
//    }
//    
//    public void sendRequest(String command, String payload) {
//    	sendMessage("{\"request\": {\"command\": \""+command+"\",\n\"payload\": \""+payload+"\"\n}}");
//    }
//
//    public static interface MessageHandler {
//
//        public void handleMessage(String message);
//    }
//}