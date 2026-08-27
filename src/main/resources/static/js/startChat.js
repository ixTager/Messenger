const startChat = (button) => {

    button.addEventListener("click", async () => {
        button.disabled = true;

        try {
            const res = await fetch("/api/chats", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    uniqueUserId: button.dataset.userId
                })
            });

            if (!res.ok) throw new Error("Server error: " + res.status);

            const dialogId = await res.text();
            window.location.href = `/chats/${dialogId}`;
        }
        catch (e) {
            console.error("Cannot create dialog:", e);
            button.disabled = false;
        }
    });
};


