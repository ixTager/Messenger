const openChat = async () => {
    try {
        await connectToDialog(dialogId, currentUserId);
    } catch (e) {
        console.error("Cannot open chat:", e);
    }
};

openChat();
