function create_group() {
    $("#save_button").on("click", function () {
        let url = "http://localhost:8080/groups";

        let name = $("#name_field").val();
        let location = $("#location_field").val();
        let description = $("#description_field").val();

        fetch(url, {
            method: "POST",
            body: JSON.stringify({
                "name": name,
                "description": description,
                "location": location,
                "adminId": adminId
            }),
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            }
        }).then(resp => {
            if (resp.status !== 200) {
                console.log("Eroare!");
            }
            else {
                window.location.replace("http://localhost:8080/users/" + adminId);
            }
        });
    });
}

$(document).ready(function () {
    $("#submit_button").on("click", create_group);
});