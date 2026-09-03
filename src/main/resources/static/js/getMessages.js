const divMessages = document.getElementById("divMessages");

const loadMessages = async (dialogId) => {
    const res = await fetch(`/api/chats/${dialogId}`);

    if (!res.ok) {
        throw new Error("Cannot load messages: " + res.status);
    }

    const messages = await res.json();

    divMessages.innerHTML = "";

    messages.forEach(message => renderNewMsg(message));
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

    divMessages.appendChild(li);
};

loadMessages(dialogId);