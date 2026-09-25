let currentController = null;

const divFoundedUser = document.getElementById("foundedUser");
const inputUniqueUserId = document.getElementById("inputUniqueUserId");

inputUniqueUserId.addEventListener("change", async (event) => {
    event.preventDefault();

    const value = inputUniqueUserId.value.trim();

    if (!value) {
        return;
    }

    if (currentController) currentController.abort();

    currentController = new AbortController();
    const { signal } = currentController;
    try {
        const res = await fetch("/api/find_user", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json"
            },
            body: JSON.stringify({
                uniqueUserId: value
            }),
            signal
        });

        if (!res.ok) {
            throw new Error("Server error: " + res.status);
        }

        const user = await res.json();

        divFoundedUser.innerHTML = "";
        renderFoundedUser(user);

    } catch (e) {
        if (e.name === 'AbortError') {
            console.warn("Request was canceled by system / user");
        }
        else console.error("Server error:", e);
    }
    finally {
        if (currentController?.signal === signal) currentController = null;
    }

});

const renderFoundedUser = (user) => {

    const a = document.createElement("a");
    const div = document.createElement("div");
    const firstAndLastName = document.createElement("div");

    const firstName = document.createElement("span");
    firstName.textContent = user.firstName;

    const lastName = document.createElement("span");
    lastName.textContent = user.lastName;

    const uniqueFoundedUserId = document.createElement("p");
    uniqueFoundedUserId.textContent = user.uniqueUserId;

    a.dataset.userId = user.uniqueUserId;
    div.classList.add("foundedUser");
    firstAndLastName.classList.add("firstAndLastName");

    startChat(a);

    firstAndLastName.appendChild(firstName);
    firstAndLastName.appendChild(lastName);

    div.appendChild(firstAndLastName);
    div.appendChild(uniqueFoundedUserId);

    a.appendChild(div);
    divFoundedUser.appendChild(a);
};




