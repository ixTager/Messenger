formToFindUser.addEventListener("submit", async (event) => {
    event.preventDefault();

    const value = inputUniqueUserId.value.trim();
    if (!value) {
        return;
    }

    try {
        const res = await fetch("http://localhost:8080/chats/find_user", {
            method: "POST",
            headers: {
                "Content-Type" : "application/x-www-form-urlencoded"
            },
            body: new URLSearchParams({
                uniqueUserId: value
            })
        });

        if (!res.ok) throw new Error("Server error: " + res.status);

        const html = await res.parse();
    }
    catch (e) {
        console.error("Server error: " + e);
    }
});