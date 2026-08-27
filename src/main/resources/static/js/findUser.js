const divFoundedUser = document.getElementById("foundedUser");
const formFindUser = document.getElementById("formFindUser");
const inputUniqueUserId = document.getElementById("inputUniqueUserId");

formFindUser.addEventListener("submit", async (event) => {
    event.preventDefault();

    const value = inputUniqueUserId.value.trim();

    if (!value) {
        return;
    }

    try {
        const res = await fetch("/api/find_user", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json"
            },
            body: JSON.stringify({
                uniqueUserId: value
            })
        });

        if (!res.ok) {
            throw new Error("Server error: " + res.status);
        }

        const user = await res.json();

        inputUniqueUserId.value = "";

        divFoundedUser.innerHTML = "";

        renderFoundedUser(user);

    } catch (e) {
        console.error("Server error:", e);
    }
});

const renderFoundedUser = (user) => {

    const li = document.createElement("li");

    const firstName = document.createElement("span");
    firstName.textContent = user.firstName;

    const lastName = document.createElement("span");
    lastName.textContent = user.lastName;

    const startChatButton = document.createElement("button");

    startChatButton.type = "button";
    startChatButton.textContent = "Start chat";
    startChatButton.dataset.userId = user.uniqueUserId;

    startChat(startChatButton);

    li.appendChild(firstName);
    li.appendChild(document.createTextNode(" "));
    li.appendChild(lastName);
    li.appendChild(startChatButton);

    divFoundedUser.appendChild(li);
};




