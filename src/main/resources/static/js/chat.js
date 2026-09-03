const openChat = async () => {
    try {
        await loadMessages();
        await connectToDialog(dialogId);
    } catch (e) {
        console.error("Cannot open chat:", e);
    }
};

openChat();
