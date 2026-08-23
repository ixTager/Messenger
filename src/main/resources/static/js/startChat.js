let dialogId = new URLSearchParams(window.location.search).get("uniqueDialogId");

const startChat = (button) => {

    button.addEventListener("click", async () => {
        button.disabled = true;

        try {

            const res = await fetch("/api/create_dialog", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    uniqueUserId: button.dataset.userId
                })
            });

            if (!res.ok) {
                throw new Error(
                    "Server error: " + res.status
                );
            }

            dialogId = await res.text();

            const url = new URL(window.location);

            url.searchParams.set(
                "uniqueDialogId",
                dialogId
            );

            history.pushState(
                {
                    uniqueDialogId: dialogId
                },
                "",
                url
            );

            await openDialog(dialogId);

        }
        catch (e) {

            console.error("Cannot create dialog:", e);

        }
        button.disabled = false;
    });
};

const openDialog = async (id) => {
    try {
        dialogId = id;

        await loadMessages(id);
        connectToDialog(id);

    } catch (e) {
        console.error("Cannot open dialog:", e);
    }
};

const loadMessages = async (dialogId) => {
    const res = await fetch(
        `/api/messages/dialogs/${dialogId}/messages`
    );

    if (!res.ok) {
        throw new Error("Cannot load messages: " + res.status);
    }
    const messages = await res.json();
    messageList.innerHTML = "";
    messages.forEach(renderNewMsg);
};
