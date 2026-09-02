const openChat = async () => {
    try {
        await loadMessages();
        await subscribeToDialog();
    } catch (e) {
        console.error("Cannot open chat:", e);
    }
};
openChat();

const subscribeToDialog = async () => {
    try {
        await connectWebSocket();

        const destination = `/topic/chat/${dialogId}`;

        console.log("Subscribe:", destination);

        stompClient.subscribe(destination, (message) => {

            const response = JSON.parse(message.body);

            switch (response.type) {

                case "MESSAGE_RECEIVED":
                    renderNewMsg(response.data);
                    break;

                case "ERROR":
                    console.error(response.data);
                    break;

                default:
                    console.warn("Unknown WS event:", response);
            }
        });

    } catch (e) {
        console.error("Cannot subscribe to dialog:", e);
    }
};