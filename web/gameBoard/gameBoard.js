	
	var urlParams = new URLSearchParams(window.location.search);
	
	var cardURL = 'https://raw.githubusercontent.com/TheHumanGiraffe/AdvancedJavaAppsProject/master/web/gameBoard/cards/';
	
	$(document).ready(function() {
		$("#echoText").val('');
	});
	
	if(urlParams.has('roomNumber')){
		var roomNumber = urlParams.get('roomNumber')
	}else{
		var roomNumber = "TheWrongRoom"
	}
	if(urlParams.has('game')){
		var game = urlParams.get('game');
	}else{
		var game = 'loser'
	}
	
	var webSocket = new WebSocket("ws://localhost:8080/AdvancedJavaAppsProject/vcasino/"+game+"/"+roomNumber);
	//var webSocket = new WebSocket("ws://ec2-3-89-73-209.compute-1.amazonaws.com:8080/webSocketTest4/websocketendpoint");
	
   // var echoText = document.getElementById("echoText");
   // echoText.value = "";
    
   // var message = document.getElementById("message");
    webSocket.onopen = function(message){ wsOpen(message);};
    webSocket.onmessage = function(message){ wsGetMessage(message);};
    webSocket.onclose = function(message){ wsClose(message);};
    webSocket.onerror = function(message){ wsError(message);};
    
    
    function drawCard(){
      var jsonText ='{ "name":"draw"}';
 	   wsSendMessage(jsonText);
    }
    
    function playCard(card){
      var jsonText ='{ "name":"play", "arg0": "'+card+'"}';
      wsSendMessage(jsonText);
    }
    
    function getWinner(){
 	   var jsonText ='{ "name":"winner"}'
 	   wsSendMessage(jsonText);
    }
   
   
    function wsOpen(message){
       // echoText.value += "Connecting ... \n";
    }
    function wsSendMessage(jsonText){
       // webSocket.send(message.value);
       // webSocket.send(json);
        webSocket.send(jsonText);
       // echoText.value += "Message sent to the server : " + message.value + "\n";
       // message.value = "";
    }
    function wsCloseConnection(){
        webSocket.close();
    }
    function wsGetMessage(message){
    	document.getElementById("player1Div").innerHTML = "";
    	document.getElementById("player2Div").innerHTML = "";
    	document.getElementById("player3Div").innerHTML = "";
    	document.getElementById("player4Div").innerHTML = "";
    	
    	console.dir(message);
    	console.dir(message.data);
    	
    	var json = JSON.parse(message.data);
    	
    	console.dir(json);
    	
    	var gamestate = json.gamestate;
    	
    	var currentPlayer = gamestate.players[gamestate.visible];
    	console.dir(gamestate);
    	console.dir(currentPlayer);
    	console.dir(currentPlayer.hand);
    	
    	
    	if(currentPlayer.hand.length>0) {
    		console.dir(currentPlayer.hand[0]);
    		console.log(currentPlayer.hand[0].cardID);
    	} else {
    		echoText.value += "Waiting for players...\n";
    	}

    	var playerCount = gamestate.length;
    	var players = gamestate.players;
    	
    	document.getElementById("discard").src = cardURL + gamestate.deck.discards[0].cardID + '.jpg';
    	
//    	var player1Cards = document.getElementById("player1Table");
//    	player1Cards.deleteRow(0);
//    	var cardRow = player1Cards.insertRow(0);
    	
//    	currentPlayer.hand.forEach(function(card){
//    		var x=cardRow.insertCell(-1);
//            x.innerHTML ="<img class=\"p1\" src='"+cardURL + card.cardID + ".jpg'"+"/>";
//    	});
    	
    	var player1div = document.getElementById('player1Div');
    	var player2div = document.getElementById('player2Div');
    	var player3div = document.getElementById('player3Div');
    	var player4div = document.getElementById('player4Div');
    	
    	currentPlayer.hand.forEach(function(card) {
    		var img = document.createElement("img");
    		img.src = cardURL + card.cardID + ".jpg";
    		img.className = "p1";
    		player1div.appendChild(img);
    	})
    	
    	players.splice(gamestate.visible, 1);
    
    	var iterator = 2;
    	players.forEach(function(player) {
    		console.log(player);
    		
    		var workingDiv = document.getElementById('player' + iterator + 'Div');
    		
    		player.hand.forEach(function(card){
    			var img = document.createElement("img");
    			img.src = cardURL + card.cardID + ".jpg";
    			img.className = "p" + iterator;
    			workingDiv.appendChild(img);
     		});
    		iterator++;
    	});
    	
    	
    	/*var player2Cards = document.getElementById("player2Table");
    	
    	
    	for(i = numberOfCardsInBlindHands[0]; i > 0; i --){
    		player2Cards.deleteRow(i-1);
        	var cardRow = player2Cards.insertRow(i-1);
    		var x=cardRow.insertCell(-1);
            x.innerHTML ="<img class=\"p2\" src=\"cards/0.jpg\"/>";
           
    	}
    	player2Cards.insertRow(numberOfCardsInBlindHands[0]);*/

       // echoText.value += "Message received from the server : " + message.data + "\n";
    }
    function wsClose(message){
       // echoText.value += "Disconnect ... \n";
    }

    function wsError(message){
        //echoText.value += "Error ... \n";
    }