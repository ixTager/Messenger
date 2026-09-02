const divChatsCurrentUser = document.getElementById("divChatsCurrentUser");

const receiveCurrentDialogs = async () => {
    try {
        await connectWebSocket();

        const destination = `/topic/user/${currentUserId}`;

        console.log("Subscribe:", destination);

        stompClient.subscribe(
            destination,
            (message) => {
                const response = JSON.parse(message.body);

                switch (response.type) {

                    case "DIALOGS_UPDATED":
                        renderDialogs(response.data);
                        break;

                    case "ERROR":
                        console.error(response.data);
                        break;

                    default:
                        console.warn("Unknown WS event:", response);
                }
            }
        );

    } catch (e) {
        console.error("Cannot subscribe to dialogs:", e);
    }
};

const renderDialog = (dialog) => {
    const li = document.createElement("li");

    const dialogLink = document.createElement("a");
    dialogLink.textContent = dialog.uniqueDialogId;
    dialogLink.href = `/chats/${dialog.uniqueDialogId}`;

    const firstName = document.createElement("span");
    firstName.textContent = dialog.firstNameMember;

    const lastName = document.createElement("span");
    lastName.textContent = dialog.lastNameMember;

    const sentAt = document.createElement("p");
    sentAt.textContent = dialog.sentAtLastMessage;

    const content = document.createElement("p");
    content.textContent = dialog.lastMessageContent;

    li.appendChild(firstName);
    li.appendChild(lastName);
    li.appendChild(content);
    li.appendChild(sentAt);
    li.appendChild(dialogLink);

    divChatsCurrentUser.appendChild(li);
};


const renderDialogs = (dialogs) => {
    divChatsCurrentUser.innerHTML = "";

    dialogs.forEach(renderDialog);
};

receiveCurrentDialogs();