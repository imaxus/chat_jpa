var CreateProxy = function(wsUri) {
	var websocket = null;
	var audio = null;
	var elements = null;
	
	var showMsgPanel = function() {
			elements.login.style.display = "none";
			elements.msg.style.display = "block";
			elements.txtMsg.focus();
	};
			
	var hideMsgPanel = function() {
			elements.login.style.display = "block";
			elements.msg.style.display = "none";
			elements.txtLogin.focus();
	};
	
	var displayMessage = function(msg) {
		/*if (elements.msgContainer.childNodes.length == 150) {
			elements.msgContainer.removeChild(elements.msgContainer.childNodes[0]);
		}*/
		var msg_dec = JSON.parse(msg.data); // native API
		
		var messageLine =  msg_dec.message;
		var senderLine = msg_dec.sender;
		
		var div = document.createElement('div');
		var pelement = document.createElement('span');
		var pelementMsg = document.createElement('span');
		div.className = 'msgText';
		if(msg_dec.messageType == "login"){
			pelement.className = 'System_sender';
			pelementMsg.className = 'msgrow_login';
		}
		else if(msg_dec.messageType == "logout"){
			pelementMsg.className = 'msgrow_logout';
			pelement.className = 'System_sender';
		}
		else if(msg_dec.messageType == "msg"){
			pelementMsg.className = 'msgrow';
			pelement.className = 'User_sender';
		}
		//var textnode = document.createTextNode(messageLine);
		//div.appendChild(textnode); 
		pelement.innerHTML  = senderLine;
		pelementMsg.innerHTML = messageLine;
		div.appendChild(pelement);
		div.appendChild(pelementMsg);
		elements.msgContainer.appendChild(div);
		
		elements.msgContainer.scrollTop = elements.msgContainer.scrollHeight;
	};
	
	var clearMessage = function() {
		elements.msgContainer.innerHTML = '';
	};
	
	return {
		login: function() {
			elements.txtLogin.focus();
			
			var name = elements.txtLogin.value.trim();
			if (name == '') { 
				return; 
			}
			
			elements.txtLogin.value = '';
			
			// Initiate the socket and set up the events
			if (websocket == null) {
		    	websocket = new WebSocket(wsUri);
		    	
		    	websocket.onopen = function() {
		    		
		    		var message = { messageType: 'login', message: name, sender: "System" };
		    		websocket.send(JSON.stringify(message));
		        };
		        websocket.onmessage = function(e) {
		        	displayMessage(e);
		        	showMsgPanel();
		        };
		        websocket.onerror = function(e) {
		        	
		        };
		        websocket.onclose = function(e) {
		        	websocket = null;
		        	clearMessage();
		        	hideMsgPanel();
		        };
			}
		},
		sendMessage: function() {
			elements.txtMsg.focus();
			
			if (websocket != null && websocket.readyState == 1) {
				var input = elements.txtMsg.value.trim();
				if (input == '') {
					return; 
				}
				
				elements.txtMsg.value = '';
				
				var message = { message: input, messageType: 'msg'};
				
				// Send a message through the web-socket
				websocket.send(JSON.stringify(message));
			}
		},
		login_keyup: function(e) { 
			if (e.keyCode == 13) { 
				this.login(); 
			} 
		},
		sendMessage_keyup: function(e) { 
			if (e.keyCode == 13) { 
				this.sendMessage(); 
			} 
		},
		logout: function() {
			if (websocket != null && websocket.readyState == 1) { 
				websocket.close();
			}
		},
		initiate: function(e) {
			elements = e;
			elements.txtLogin.focus();
		}
	}
};