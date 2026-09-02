let stompClient = null;
let stompConnection = null;

const connectWebSocket = () => {

    if (stompConnection) {
        return stompConnection;
    }

    stompConnection = new Promise((resolve, reject) => {

        const socket = new SockJS("/ws");
        stompClient = Stomp.over(socket);

        stompClient.connect(
            {},
            (frame) => {
                console.log("WebSocket connected:", frame);
                resolve(stompClient);
            },
            (error) => {
                console.error("STOMP error:", error);

                stompClient = null;
                stompConnection = null;

                reject(error);
            }
        );
    });

    return stompConnection;
};