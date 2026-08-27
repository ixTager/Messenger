const divChatsCurrentUser = document.getElementById("divChatsCurrentUser");

const receiveCurrentDialogs = async () => {
    try {
        const res = await fetch("/api/chats/create_chat");
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

    const link = document.createElement("a");
    link.textContent = dialog.uniqueDialogId;
    link.href = `/chats/${dialog.uniqueDialogId}`;

    li.appendChild(link);
    divChatsCurrentUser.appendChild(li);
}

receiveCurrentDialogs();