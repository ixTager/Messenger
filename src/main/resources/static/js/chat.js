const openChat = async () => {
    try {
        await loadMessages();
        connectToDialog(dialogId);
    } catch (e) {
        console.error("Cannot open chat:", e);
    }
};

openChat();