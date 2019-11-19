	
	var urlParams = new URLSearchParams(window.location.search);
	
	var cardURL = 'https://raw.githubusercontent.com/TheHumanGiraffe/AdvancedJavaAppsProject/master/web/gameBoard/cards/';
	
	/*$.getJSON('../../inbound.json', function(data) {
		var container = document.getElementById('discard');
		var img = document.createElement('img');
			img.src = cardURL + data.topDiscard.CardID + '.jpg'; // img[i] refers to the current URL.
			img.title = data.topDiscard.Name; 
			container.appendChild(img);
		$.each(data.table, function (key, val) {
			var container = document.getElementById('table');
			var img = document.createElement('img');
				img.src = 'https://raw.githubusercontent.com/TheHumanGiraffe/AdvancedJavaAppsProject/master/cards/' + val.CardID + '.jpg'; // img[i] refers to the current URL.
				img.title = val.Name;
				container.appendChild(img);
		});
		$.each(data.player1.hand, function (key, val) {
			var container = document.getElementById('player1');
			var img = document.createElement('img');
				img.src = 'https://raw.githubusercontent.com/TheHumanGiraffe/AdvancedJavaAppsProject/master/cards/' + val.CardID + '.jpg'; // img[i] refers to the current URL.
				img.title = val.Name;
				container.appendChild(img);
		});
		$.each(data.player2.hand, function (key, val) {
			var container = document.getElementById('player2');
			var img = document.createElement('img');
				img.src = 'https://raw.githubusercontent.com/TheHumanGiraffe/AdvancedJavaAppsProject/master/cards/' + val.CardID + '.jpg'; // img[i] refers to the current URL.
				img.title = val.Name;
				container.appendChild(img);
		});
		$.each(data.player3.hand, function (key, val) {
			var container = document.getElementById('player3');
			var img = document.createElement('img');
				img.src = 'https://raw.githubusercontent.com/TheHumanGiraffe/AdvancedJavaAppsProject/master/cards/' + val.CardID + '.jpg'; // img[i] refers to the current URL.
				img.title = val.Name;
				container.appendChild(img);
		});
		$.each(data.player4.hand, function (key, val) {
			var container = document.getElementById('player4');
			var img = document.createElement('img');
				img.src = 'https://raw.githubusercontent.com/TheHumanGiraffe/AdvancedJavaAppsProject/master/cards/' + val.CardID + '.jpg'; // img[i] refers to the current URL.
				img.title = val.Name;
				container.appendChild(img);
		});
	});*/
	
	
	//var json = JSON.parse(jsonText); 
	
	//
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
      var jsonText ='{ "action":"draw"}'
 	   wsSendMessage(jsonText);
    }
    
    function getWinner(){
 	   var jsonText ='{ "action":"winner"}'
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
    	console.log(message);
    	console.log(message.data);
    	
    	var json = JSON.parse(message.data);
    	
    	console.log(json);
    	
    	var gamestate = json.gamestate;
    	
    	var currentPlayer = gamestate.players[gamestate.visible];
    	
    	console.log(currentPlayer);
    	console.log(currentPlayer.hand);
    	if(currentPlayer.hand.length>0) {
    		console.log(currentPlayer.hand[0]);
    		console.log(currentPlayer.hand[0].cardID);
    	} else {
    		echoText.value += "Waiting for players...\n";
    	}

    	var playerCount = gamestate.length;
    	var players = gamestate.players;
    	
    	document.getElementById("discard").src = cardURL + gamestate.deck.discards[0].cardID + '.jpg';
    	
    	var player1Cards = document.getElementById("player1Table");
    	player1Cards.deleteRow(0);
    	var cardRow = player1Cards.insertRow(0);
    	
    	currentPlayer.hand.forEach(function(card){
    		var x=cardRow.insertCell(-1);
            x.innerHTML ="<img class=\"p1\" src='"+cardURL + card.cardID + ".jpg'"+"/>";
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