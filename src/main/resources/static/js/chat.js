const dialogId = window.location.pathname.split("/").pop();

const loadMessages = async () => {

    const res = await fetch(`/api/chats/${dialogId}`);

    if (!res.ok) {
        throw new Error("Cannot load messages: " + res.status);
    }

    const messages = await res.json();

    messages.forEach(message => renderNewMsg(message));
};

const openChat = async () => {
    try {
        await loadMessages();
        connectToDialog(dialogId);
    } catch (e) {
        console.error("Cannot open chat:", e);
    }
};

openChat();