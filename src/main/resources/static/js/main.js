'use strict';

var usernamePage = document.querySelector('#username-page');
var profilPage = document.querySelector('#profil-page');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var live = document.querySelector('#live');
var connectingElement = document.querySelector('.connecting');
var useridStore = -1;
var stompClient = null;
var email = null;
var idUserLogin = 0;
var tokenConnected = null;
var chatBox=null;
var pass = null;
var typeChat = 0; // 0 : public , 1 : users
var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

function connect(event) {
    email = document.querySelector('#email').value.trim();
    pass = document.querySelector('#pass').value.trim();
    const login = '{"email": '+email+', "password": '+pass+'}';
    console.log(login);
    var valide = false;
    $.ajax({
        type: "POST",
        async:false,
        url: "/authentication/authenticate",
        data:  JSON.stringify({email: email, password: pass}),
        contentType: "application/json",
        dataType: "json",
        success: function(token) {
            valide=true;
            console.log("success connect");
            console.log(token);
            tokenConnected=token;

            usernamePage.classList.add('hidden');
            profilPage.classList.remove('hidden');
            /*var socket = new SockJS('/ws');
            stompClient = Stomp.over(socket);
            stompClient.connect({}, onConnected, onError);*/
        },
        error: function(response) {
            console.log("error "+response);
        }
    });
    if (valide){
        $.ajax({
            type: "GET",

            url: "/user/show-all-users2/"+tokenConnected.token,

            contentType: "application/json",
            dataType: "json",
            success: function(users) {
                var sidebar = document.getElementById("sidebar");
                var button= document.getElementById("public");
                for (let u in users)  {
                    console.log(users[u]);
                    var myButton = document.createElement("button");
                    myButton.innerHTML = users[u].firstname;

                    // add a class to the button
                    myButton.className = "btn btn-success";
                    myButton.addEventListener("click", function() {
                        handleClick(users[u].id);
                    });
                    sidebar.appendChild(myButton);
                }
            },
            error: function(response) {
                console.log("error "+response);
            }
        });
    }

    event.preventDefault();
}
function handleClick(userId) {
    if (useridStore!=userId){
        deleteAllMsg();
        typeChat=1;
        idUserLogin=userId;
        useridStore=userId;
        chatPage.classList.remove('hidden');

        console.log(tokenConnected);
        $.ajax({
            type: "GET",
            url: "/user/getNomPrenomUser/" + idUserLogin ,
            contentType: "application/json",
            async:false,
            dataType: "text",
            success: function(email) {
                console.log("success email ");
                console.log(email);
                var h = document.getElementById("nomUser");
                h.innerHTML=email;
            },
            error: function(response) {
                console.log("error "+response);
            }
        });
        $.ajax({
            type: "GET",
            url: "/retrieveBoite/user/" + tokenConnected.token + "/chat/" + userId,
            contentType: "application/json",
            dataType: "json",
            success: function(chatb) {
                console.log("success handleClick ");
                console.log(chatBox);
                var socket = new SockJS('/ws');
                stompClient = Stomp.over(socket);
                chatBox=chatb;
                stompClient.connect({}, onConnected2, onError);
                affichageAllMsg(chatb.chats);
            },
            error: function(response) {
                console.log("error "+response);
            }
        });

    }


}

function onConnected2() {
    console.log("onConnected2");

    /*var chat = { recipientEmail: email };
    stompClient.send("/app/chat.connect", {}, JSON.stringify(chat));*/
    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/channel/'+chatBox.id, onMessageReceived);
    stompClient.send("/app/chat.addUser",
        {},
        JSON.stringify({sender: email, type: 'JOIN'})
    )
/*
    stompClient.send("/app/chat.addUser",
        {},
        JSON.stringify({sender: tokenConnected, type: 'JOIN', chatId: "user-" + email + "-chat" })
    )

    connectingElement.classList.add('hidden');*/
}



function onConnected() {

    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/public', onMessageReceived);
    // Tell your username to the server
    stompClient.send("/app/chat.addUser",
        {},
        JSON.stringify({sender: email, type: 'JOIN'})
    )

    connectingElement.classList.add('hidden');
}


function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}


function sendMessage(event) {

}
function sendMessageUsers(event) {
    console.log("sendMessageUsers");
    if(typeChat==1){
        var messageContent = messageInput.value.trim();

        if(messageContent && stompClient) {
            var chatMessage = {
                sender: email,
                content: messageInput.value,
                type: 'CHAT'
            };
            var chatMessage2 = {
                chat: chatMessage,
                token: tokenConnected,
                id: idUserLogin
            };
            stompClient.send("/app/channel/"+chatBox.id+"/Sender/"+tokenConnected.token+"/to/"+idUserLogin, {}, JSON.stringify(chatMessage));
            messageInput.value = '';
        }
        event.preventDefault();
    }
    else {
        var messageContent = messageInput.value.trim();

        if(messageContent && stompClient) {
            var chatMessage = {
                sender: email,
                content: messageInput.value,
                type: 'CHAT'
            };
            stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
            messageInput.value = '';
        }
        event.preventDefault();
    }
}

function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);

    var messageElement = document.createElement('li');

    if(message.type === 'JOIN') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' joined!';
    } else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' left!';
    } else {
        messageElement.classList.add('chat-message');

        var avatarElement = document.createElement('i');
        var avatarText = document.createTextNode(message.sender[0]);
        avatarElement.appendChild(avatarText);
        avatarElement.style['background-color'] = getAvatarColor(message.sender);

        messageElement.appendChild(avatarElement);

        var usernameElement = document.createElement('span');
        var usernameText = document.createTextNode(message.sender);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
    }

    var textElement = document.createElement('p');
    var messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}


function getAvatarColor(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }

    var index = Math.abs(hash % colors.length);
    return colors[index];
}

function affichageAllMsg(chats){
    debugger;
    for (var c in chats){
        var message = chats[c];

        var messageElement = document.createElement('li');

        if(message.type === 'JOIN') {
            messageElement.classList.add('event-message');
            message.content = message.sender + ' joined!';
        } else if (message.type === 'LEAVE') {
            messageElement.classList.add('event-message');
            message.content = message.sender + ' left!';
        } else {
            messageElement.classList.add('chat-message');
            var avatarElement = document.createElement('i');
            var avatarText = document.createTextNode(message.sender[0]);
            avatarElement.appendChild(avatarText);
            avatarElement.style['background-color'] = getAvatarColor(message.sender);

            messageElement.appendChild(avatarElement);

            var usernameElement = document.createElement('span');
            var usernameText = document.createTextNode(message.sender);
            usernameElement.appendChild(usernameText);
            messageElement.appendChild(usernameElement);
        }

        var textElement = document.createElement('p');
        var messageText = document.createTextNode(message.content);
        textElement.appendChild(messageText);

        messageElement.appendChild(textElement);

        messageArea.appendChild(messageElement);
        messageArea.scrollTop = messageArea.scrollHeight;
    }

}




function clickLive(){
    if (useridStore!=0){
        console.log("clickLive");
        useridStore=0;
        typeChat=0;
        chatPage.classList.remove('hidden');
        var socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, onConnected, onError);
        $.ajax({
            type: "GET",
            async:false,
            url: "/public/retrieveAllMsg",
            contentType: "application/json",
            dataType: "json",
            success: function(chats) {
                console.log("success chats ");
                console.log(chats);
                affichageAllMsg(chats);
            },
            error: function(response) {
                console.log("error "+response);
            }
        });
    }

}

function deleteAllMsg(){
    while (messageArea.firstChild) {
        messageArea.removeChild(messageArea.firstChild);
    }
}



usernameForm.addEventListener('submit', connect, true)
messageForm.addEventListener('submit', sendMessageUsers, true)
