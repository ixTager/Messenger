const openChat = async () => {
    try {
        await connectToDialog(dialogId);
        await connectToCompanionStatus()
    } catch (e) {
        console.error("Cannot open chat:", e);
    }
};

openChat();