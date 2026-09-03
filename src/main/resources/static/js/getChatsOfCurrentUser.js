const divChatsCurrentUser = document.getElementById("divChatsCurrentUser");

const receiveCurrentDialogs = async () => {
    try {
        const res = await fetch("/api/chats");
        if (!res.ok) throw new Error("SERVER ERROR: " + res.status);
        const dialogs = await res.json();
        dialogs.forEach(dialog => renderNewDialog(dialog));
    }
    catch (e) {
        console.error("SERVER ERROR: " + e);
    }
}

const renderNewDialog = (dialog) => {
    const li = document.createElement("li");

    const uniqueDialogId = document.createElement("a");
    uniqueDialogId.textContent = dialog.uniqueDialogId;
    uniqueDialogId.href = `/chats/${dialog.uniqueDialogId}`;

    const firstNameSender = document.createElement("span");
    firstNameSender.textContent = dialog.firstNameMember;

    const lastNameSender = document.createElement("span");
    lastNameSender.textContent = dialog.lastNameMember;

    const sentAtLastMessage = document.createElement("p");
    sentAtLastMessage.textContent = dialog.sentAtLastMessage;

    const lastMessageContent = document.createElement("p");
    lastMessageContent.textContent = dialog.lastMessageContent;

    li.appendChild(firstNameSender);
    li.appendChild(lastMessageContent);
    li.appendChild(sentAtLastMessage);
    li.appendChild(lastMessageContent)
    li.appendChild(uniqueDialogId);
    divChatsCurrentUser.appendChild(li);
}

receiveCurrentDialogs();