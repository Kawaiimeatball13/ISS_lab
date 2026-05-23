// let articleData = {
//     "title": "",
//     "id": "",
//     "idAuthor": "",
//     "text": ""
// };
//
// async function load_text(articleId) {
//     let url = "http://localhost:8080/articles/" + articleId;
//
//     let details = await fetch(url, {
//         method: "GET",
//     }).then(async response => {
//         return [await response.text(), response.headers.get("content-disposition").split("; ")[1]];
//     });
//
//     articleData.text = details[0];
//     let dets = details[1].split("~");
//     articleData.title= dets[0].split("\"")[1];
//     articleData.id = dets[1];
//     articleData.idAuthor = dets[2].split(".")[0];
// }


$(document).ready(function () {
    $("#update_button").on("click", function () {
        let url = "http://localhost:8080/articles/" + articleId;

        // console.log(articleData.text);
        let newTitle = $("#ftitle").val();
        let newContent = $("#editor").children("div").html();
        fetch(url, {
            method: "PUT",
            body: JSON.stringify({
                "title": newTitle,
                "text": newContent,
                "isPublished": isPublished
            }),
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            }
        }).then(resp => {
            console.log(resp.status);
        });
    });

    $("#save_button").on("click", function () {
        let url = "http://localhost:8080/articles";

        let newTitle = $("#ftitle").val();
        let newContent = $("#editor").children("div").html();
        fetch(url, {
            method: "POST",
            body: JSON.stringify({
                "title": newTitle,
                "text": newContent,
                "authorId": authorId
            }),
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            }
        }).then(resp => {
            if(resp.status !== 200) {
                console.log("Eroare!");
            }
            else {
                resp.json().then(art => {
                    articleId = art.id;

                    //modificam butonul din buton de save in buton de update
                    let updateButton = $("<button></button>");
                    updateButton.attr("id", "update_button");
                    updateButton.text("Salvare modificări");
                    updateButton.addClass("form_line");
                    updateButton.on("click", function () {
                        let url = "http://localhost:8080/articles/" + articleId;

                        // console.log(articleData.text);
                        let newTitle = $("#ftitle").val();
                        let newContent = $("#editor").children("div").html();
                        fetch(url, {
                            method: "PUT",
                            body: JSON.stringify({
                                "title": newTitle,
                                "text": newContent,
                                "isPublished": false
                            }),
                            headers: {
                                "Content-type": "application/json; charset=UTF-8"
                            }
                        }).then(resp => {
                            console.log(resp.status);
                        });
                    });

                    $("#article_data").append(updateButton);
                    $("#save_button").hide();
                });
            }
        });
    });
});

