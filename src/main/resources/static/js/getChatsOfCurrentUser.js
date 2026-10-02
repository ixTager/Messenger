const divChatsCurrentUser = document.getElementById("divChatsCurrentUser");


const renderDialogs = (dialogs) => {
    divChatsCurrentUser.innerHTML = "";

    dialogs.forEach(dialog => {
        renderNewDialog(dialog);
    });
};

const renderNewDialog = (dialog) => {
    const divDialog = document.createElement("div");
    divDialog.className = "dialog";

    const divSenderPersonalInfo = document.createElement("div");
    const divMessageLeftBlock = document.createElement("div");
    const divLastMessageBlock = document.createElement("div");
    const divLastMessageInfo = document.createElement("div");
    const divCountUnreadMessages = document.createElement("div");
    const divMessageRightBlock = document.createElement("div");

    const dialogLink = document.createElement("a");
    dialogLink.href = `/chats/${dialog.uniqueDialogId}`;

    const firstNameSender = document.createElement("span");
    firstNameSender.textContent = dialog.firstNameMember;

    const lastNameSender = document.createElement("span");
    lastNameSender.textContent = dialog.lastNameMember;

    const lastMessageContent = document.createElement("span");
    lastMessageContent.textContent = dialog.lastMessageContent;

    const lastMessageStatus = document.createElement("span");
    lastMessageStatus.textContent = dialog.lastMessageStatus;

    const sentAtLastMessage = document.createElement("span");
    sentAtLastMessage.textContent = dialog.sentAtLastMessage;

    const countUnreadMessages = document.createElement("span");
    countUnreadMessages.textContent = dialog.countUnreadMessages;

    divSenderPersonalInfo.appendChild(firstNameSender);
    divSenderPersonalInfo.appendChild(lastNameSender);

    divMessageLeftBlock.appendChild(divSenderPersonalInfo);
    divMessageLeftBlock.appendChild(lastMessageContent);

    divLastMessageInfo.appendChild(lastMessageStatus);
    divLastMessageInfo.appendChild(sentAtLastMessage);

    divLastMessageBlock.appendChild(divLastMessageInfo);

    if (dialog.countUnreadMessages > 0) divCountUnreadMessages.appendChild(countUnreadMessages);

    divMessageRightBlock.appendChild(divLastMessageBlock);

    divMessageRightBlock.appendChild(divCountUnreadMessages);

    divDialog.appendChild(divMessageLeftBlock);
    divDialog.appendChild(divMessageRightBlock);

    dialogLink.appendChild(divDialog);

    console.log("RENDER DIALOG:", dialog);

    divChatsCurrentUser.appendChild(dialogLink);
};


const loadCurrentDialogs = async () => {
    try {
        const res = await fetch("/api/chats");

        if (!res.ok) {
            throw new Error("SERVER ERROR: " + res.status);
        }

        const dialogs = await res.json();

        renderDialogs(dialogs);
    } catch (e) {
        console.error("Cannot load dialogs:", e);
    }
};


const subscribeToCurrentDialogs = async () => {
    try {
        await connectWebSocket();
        const destination = `/topic/user/${currentUserId}/chats`;

        console.log("Subscribe:", destination);

        stompClient.subscribe(
            destination, (message) => {
                console.log("Dialogs received:", message.body);

                const response = JSON.parse(message.body);

                switch (response.type) {
                    case "DIALOGS_UPDATE":
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
    }
    catch (e) {
        console.error(
            "Cannot subscribe to dialogs:",
            e
        );
    }
};


const initDialogs = async () => {
    await loadCurrentDialogs();
    await subscribeToCurrentDialogs();
};


initDialogs();
