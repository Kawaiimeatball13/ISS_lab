let articleData = {
    "title": "",
    "id": "",
    "idAuthor": "",
    "text": ""
};

async function load_text(articleId) {
    let url = "http://localhost:8080/articles/" + articleId;

    let details = await fetch(url, {
        method: "GET",
    }).then(async response => {
        return [await response.text(), response.headers.get("content-disposition").split("; ")[1]];
    });

    articleData.text = details[0];
    let dets = details[1].split("~");
    articleData.title= dets[0].split("\"")[1];
    articleData.id = dets[1];
    articleData.idAuthor = dets[2].split(".")[0];
}

$(document).ready(function() {
    load_text(1).then(function () {
        $("#ftitle").val(articleData.title);
        $("#editor").children("div").html(articleData.text);
    });
});