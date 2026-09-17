const divMessages = document.getElementById("divMessages");

const loadMessages = async (dialogId) => {
    const res = await fetch(`/api/chats/${dialogId}`);

    if (!res.ok) {
        throw new Error("Cannot load messages: " + res.status);
    }

    const messages = await res.json();

    divMessages.innerHTML = "";

    messages.forEach(message => renderNewMsg(message));

    await fetch(`/api/chats/${dialogId}/read`, {
        method : "PATCH"
    });

    messages.forEach(message => {
        if (message.uniqueUserId !== currentUserId) message.status = "READ";
    })
};

const renderNewMsg = (message) => {
    const li = document.createElement("li");

    const divSender = document.createElement("div");

    const pSenderFirstName = document.createElement("span");
    pSenderFirstName.textContent = message.senderFirstName;

    const pSenderLastName = document.createElement("span");
    pSenderLastName.textContent = message.senderLastName;

    const pContent = document.createElement("p");
    pContent.textContent = message.content;

    const status = document.createElement("p");
    status.textContent = message.status;

    const pTime = document.createElement("p");
    pTime.textContent = message.sentAt;

    if (message.uniqueUserId === currentUserId) li.classList.add("message-own");
    else li.classList.add("message-other");

    divSender.appendChild(pSenderFirstName);
    divSender.appendChild(pSenderLastName);

    li.appendChild(divSender);
    li.appendChild(pContent);
    li.appendChild(status);
    li.appendChild(pTime);

    divMessages.appendChild(li);
    divMessages.scrollTop = divMessages.scrollHeight;
};

loadMessages(dialogId);