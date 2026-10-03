const changeCurrentUserStatus = async () => {
    try {
        const res = await fetch("/api/users/set_user_status", {
            method : "POST",
            headers : {
                "Content-Type": "application/json",
                "Accept": "application/json"
            },
            body : JSON.stringify({
                uniqueUserId : currentUserId,
                userStatus : "ONLINE"
            })
        });

        if (!res.ok) throw new Error("Error to update user Status");
    }
    catch (e) {
        console.error("Change user Status Error: " + e);
    }
}