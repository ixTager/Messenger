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


const connectToDialog = async (dialogId) => {
    try {
        await connectWebSocket();

        const destination =
            `/topic/chat/${dialogId}`;

        console.log("Subscribe:", destination);

        stompClient.subscribe(
            destination,
            (message) => {
                console.log(
                    "Message received:",
                    message.body
                );

                const response =
                    JSON.parse(message.body);

                switch (response.type) {

                    case "MESSAGE_RECEIVED":
                        renderNewMsg(response.data);
                        break;

                    case "ERROR":
                        console.error(response.data);
                        break;

                    default:
                        console.warn(
                            "Unknown WS event:",
                            response
                        );
                }
            }
        );

    } catch (e) {
        console.error(
            "Cannot subscribe to dialog:",
            e
        );
    }
};
