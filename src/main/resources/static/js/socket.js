let stompClient = null;

const connectToDialog = (dialogId) => {
    if (stompClient) {
        stompClient.disconnect(() => {
            console.log("Previous socket disconnected");
        });

        stompClient = null;
    }

    const socket = new SockJS("/ws");

    stompClient = Stomp.over(socket);
    stompClient.connect({}, (frame) => {

        console.log("Connected:", frame);

        stompClient.subscribe(
            `/topic/dialog/${dialogId}`,
            (res) => {

                const message = JSON.parse(res.body);

                renderNewMsg(message);
            }
        );

    }, (error) => {
        console.error("STOMP error:", error);
    });
};
const renderNewMsg = (message) => {
    const li = document.createElement("li");

    const pSender = document.createElement("p");
    pSender.textContent = message.senderName;

    const pContent = document.createElement("p");
    pContent.textContent = message.content;

    const pTime = document.createElement("p");
    pTime.textContent = message.sentAt;

    li.appendChild(pSender);
    li.appendChild(pContent);
    li.appendChild(pTime);

    messageList.appendChild(li);
};

window.addEventListener("popstate", async (event) => {
    const params = new URLSearchParams(window.location.search);
    const dialogId = params.get("uniqueDialogId");

    if (!dialogId) {
        messageList.innerHTML = "";

        if (stompClient) {
            stompClient.disconnect();
            stompClient = null;
        }
        return;
    }

    await openDialog(dialogId);
});