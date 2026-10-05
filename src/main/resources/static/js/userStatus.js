const divCompanionStatus = document.getElementById("companion-status");

const connectToCompanionStatus = async () => {
    try {
        await connectWebSocket();

        if (companionStatusSubscription) {
            await companionStatusSubscription.unsubscribe();
        }

        const destination = `/topic/user/${companionUserId}/status`;

        console.log("Subscribe:", destination);

        companionStatusSubscription = stompClient.subscribe(destination, (message) => {
                console.log("Companion status received:", message.body);

                const response = JSON.parse(message.body);

                switch (response.type) {
                    case "USER_STATUS_UPDATE":
                        renderCompanionStatus(response.data);
                        break;

                    case "ERROR":
                        console.error(response.data);
                        break;

                    default:
                        console.warn("Unknown WS event:", response);
                }
            }
        );

        await loadCompanionStatus();
    }
    catch (e) {
        console.error(
            "Cannot subscribe to dialogs:",
            e
        );
    }
}
const changeCurrentUserStatus = async () => {
    try {
        const res = await fetch("/api/users/set_user_status", {
            method : "POST",
            headers : {
                "Content-Type": "application/json",
                "Accept": "application/json"
            },
            body : JSON.stringify({
                uniqueUserId : currentUserId,
                userStatus : "ONLINE"
            })
        });

        if (!res.ok) throw new Error("Error to update user Status");
    }
    catch (e) {
        console.error("Change user Status Error: " + e);
    }
}

const renderCompanionStatus = (companionStatus) => {
    divCompanionStatus.innerHTML = "";
    const status = document.createElement("span");
    status.textContent = companionStatus;

    divCompanionStatus.appendChild(status);
}

const loadCompanionStatus = async() => {
    try {
        const res = await fetch(`/api/users/${companionUserId}/get_status`);

        if (!res.ok) throw new Error("HTTP STATUS to loadCompanionStatus: " + res.status);
        const companionStatus = await res.text();
        renderCompanionStatus(companionStatus);
    }
    catch (e) {
        console.error("Catch Error to loadCompanionStatus: " + e);
    }
}