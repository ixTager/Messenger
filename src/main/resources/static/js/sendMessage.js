const messageForm = document.getElementById("formMessage");
const messageInput = document.getElementById("inputMessage");

messageForm.addEventListener("submit", async (event) => {
    event.preventDefault();

    const content = messageInput.value.trim();

    if (!content) {
        return;
    }

    try{
        const res = await fetch("/api/messages/send_message", {
            method: "POST",
            headers: {
                "Content-Type" : "application/json"
            },
            body: JSON.stringify({
                uniqueDialogId: dialogId,
                content: content
            })
        });
        if (!res.ok) throw new Error("Error to fetch messages");
    }
    catch (e) {
        console.error("SERVER ERROR: " + e);
    }
    messageInput.value = "";
    messageInput.focus();
});