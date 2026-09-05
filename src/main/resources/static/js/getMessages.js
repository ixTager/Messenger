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

    const pSenderFirstName = document.createElement("p");
    pSenderFirstName.textContent = message.senderFirstName;

    const pSenderLastName = document.createElement("p");
    pSenderLastName.textContent = message.senderLastName;

    const pContent = document.createElement("p");
    pContent.textContent = message.content;

    const pTime = document.createElement("p");
    pTime.textContent = message.sentAt;

    li.appendChild(pSenderFirstName);
    li.appendChild(pSenderLastName);
    li.appendChild(pContent);
    li.appendChild(pTime);

    divMessages.appendChild(li);
};

loadMessages(dialogId);