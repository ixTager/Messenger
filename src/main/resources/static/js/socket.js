let stompClient = null;

const connectToDialog = (dialogId) => {

    if (stompClient) {
        stompClient.disconnect();
        stompClient = null;
    }

    const socket = new SockJS("/ws");

    stompClient = Stomp.over(socket);

    stompClient.connect({}, (frame) => {

        console.log("Connected:", frame);

        const destination = `/topic/chat/${dialogId}`;

        console.log("Subscribe:", destination);

        stompClient.subscribe(destination, (res) => {

            console.log("Received:", res.body);

            const message = JSON.parse(res.body);

            renderNewMsg(message);
        });

    }, (error) => {
        console.error("STOMP error:", error);
    });
};