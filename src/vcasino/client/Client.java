/*package vcasino.client;

import java.net.Socket;
import java.net.URI;

import com.google.gson.Gson;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import vcasino.core.GameState;


public class Client extends Application {
	Socket sock;
	String server = "192.168.1.3:8080"; //change me as necessary
	VCasinoClientEndpoint endpoint;
	TextArea chat;
	
	class ClientThread implements Runnable {
		
		TextArea output;
		
		ClientThread(TextArea a) {
			output = a;
		}
		
		public void run() {
			try {
			
				Platform.runLater(() -> {
					output.appendText("Connecting to "+server+"...\n");
				});
			
			
				endpoint = new VCasinoClientEndpoint(new URI("ws://"+server+"/AdvancedJavaAppsProject/vcasino/loser/0"));
				endpoint.addMessageHandler(new ClientMessageHandler());
				endpoint.connect();
				
				if(endpoint.isConnected()) {
					Platform.runLater(() -> {
						output.appendText("Connected!\n");
					});
				}
			
			} catch (Exception e) {
				Platform.runLater(() -> {
					output.appendText("Error: "+e.getLocalizedMessage()+"\n");
					output.appendText(e.toString()+"\n");
				});
			}
		}
	}
	public static void main(String [] args) {

		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		FlowPane flowPane = new FlowPane();
		chat = new TextArea();
		TextField chatInput = new TextField();
		
		Button d = new Button("Draw");
		Button ds = new Button("Discard");
		Button p = new Button("Place");
		Button c = new Button("Chat");
		
		flowPane.getChildren().addAll(d,ds,p, chat, chatInput, c);
		
		c.setOnAction(e -> {endpoint.sendRequest("chat", chatInput.getText()); chatInput.setText("");});
		d.setOnAction(e -> {endpoint.sendRequest("draw", "");});
		ds.setOnAction(e -> {endpoint.sendRequest("discard", "todo-cardid");});
		p.setOnAction(e -> {endpoint.sendRequest("place", "todo-cardid;todo-ontowhere");});
		
		chat.setEditable(false);
		
		ClientThread thread = new ClientThread(chat);
		
		Scene scene = new Scene(flowPane);
		
		primaryStage.setScene(scene);
		primaryStage.centerOnScreen();
		primaryStage.show();
		
		(new Thread(thread)).start();
	}
	
	class ClientMessageHandler implements VCasinoClientEndpoint.MessageHandler {

		@Override
		public void handleMessage(String message) {
			Gson gson = new Gson();
			GameState state = gson.fromJson(message, GameState.class);
			
			Platform.runLater(() -> {
				chat.appendText(message);
			});
		}
	
	}
}*/
