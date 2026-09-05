const divChatsCurrentUser = document.getElementById("divChatsCurrentUser");


const renderDialogs = (dialogs) => {
    divChatsCurrentUser.innerHTML = "";

    dialogs.forEach(dialog => {
        renderNewDialog(dialog);
    });
};

const renderNewDialog = (dialog) => {
    const li = document.createElement("li");
    const divSender = document.createElement("div");

    const dialogLink = document.createElement("a");
    dialogLink.textContent = dialog.uniqueDialogId;
    dialogLink.href = `/chats/${dialog.uniqueDialogId}`;

    const firstNameSender = document.createElement("span");
    firstNameSender.textContent = dialog.firstNameMember;

    const lastNameSender = document.createElement("span");
    lastNameSender.textContent = dialog.lastNameMember;

    const lastMessageContent = document.createElement("p");
    lastMessageContent.textContent = dialog.lastMessageContent;

    const sentAtLastMessage = document.createElement("p");
    sentAtLastMessage.textContent = dialog.sentAtLastMessage;

    divSender.appendChild(firstNameSender);
    divSender.appendChild(lastNameSender);

    li.appendChild(divSender);
    li.appendChild(lastMessageContent);
    li.appendChild(sentAtLastMessage);
    li.appendChild(dialogLink);

    console.log("RENDER DIALOG:", dialog);

    divChatsCurrentUser.appendChild(li);
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

        const destination =
            `/topic/user/${currentUserId}/chats`;

console.log("Subscribe:", destination);

stompClient.subscribe(
    destination,
    (message) => {
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
                console.warn(
                    "Unknown WS event:",
                    response
                );
        }
    }
);

} catch (e) {
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
