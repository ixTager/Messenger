const socket = (uniqueDialogId) => {
    if (uniqueDialogId) {
        const socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, (frame) => {
                console.log('Connected ' + frame);
                stompClient.subscribe('/topic/dialog/' + uniqueDialogId, (res) => {
                    const input_msg = JSON.parse(res.body);
                    renderNewMsg(input_msg);
                });
            }, (err) => {
                console.error("Stomp error: " + err)
            }
        );
    }
}
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
}