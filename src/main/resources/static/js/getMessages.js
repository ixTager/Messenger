const divMessages = document.getElementById("divMessages");

const loadMessages = async (dialogId) => {
    const res = await fetch(`/api/chats/${dialogId}`);

    if (!res.ok) {
        throw new Error("Cannot load messages: " + res.status);
    }

    const messages = await res.json();

    divMessages.innerHTML = "";

    messages.forEach(message => {
        renderNewMsg(message)
    });

    await markMessagesAsRead(dialogId);
};
const markMessagesAsRead = async (dialogId) => {
    const res = await fetch(`/api/chats/${dialogId}/read`, {
        method: "PATCH"
    });

    if (!res.ok) {
        console.error("Cannot mark messages as read: " + res.status);
    }
};

const updateMessageStatus = (data) => {
    const li = divMessages.querySelector(`li[data-uuid="${data.uuidMessage}"]`);
    if (!li) return;

    const statusEl = li.querySelector(".message-status");
    if (statusEl) {
        statusEl.textContent = data.status;
    }
};

const renderNewMsg = (message) => {
    const msg = document.createElement("li");
    msg.dataset.uuid = message.messageUUID;

    const divMsgContent = document.createElement("div");
    const divMsgInfo = document.createElement("div");

    const msgContent = document.createElement("p");
    msgContent.textContent = message.messageContent;

    const msgStatus = document.createElement("span");
    msgStatus.textContent = message.messageStatus;
    msgStatus.classList.add("message-status");

    const msgTime = document.createElement("span");
    msgTime.textContent = message.sentAt;

    if (message.uniqueUserId === currentUserId) msg.classList.add("message-own");
    else msg.classList.add("message-other");

    divMsgContent.appendChild(msgContent);

    divMsgInfo.appendChild(msgTime);
    divMsgInfo.appendChild(msgStatus);

    msg.appendChild(divMsgContent);
    msg.appendChild(divMsgInfo);

    divMessages.appendChild(msg);
    divMessages.scrollTop = divMessages.scrollHeight;
};