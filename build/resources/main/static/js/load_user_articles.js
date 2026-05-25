function get_user_articles(userId) {
    let articlesUrl = "http://localhost:8080/users/" + userId + "/articles";

    let headers = new Headers();
    headers.append('Accept', 'application/json');
    let request = new Request(
        articlesUrl,
        {
            method: "GET",
            headers: headers,
            mode: 'cors'
        }
    );
    fetch(request)
        .then(r => {
            return r.json();
        })
        .then(data => {
            data.forEach(art => {
                let curr_art_line = $("<div></div>");
                curr_art_line.addClass("articleLine");

                let curr_art = $("<div></div>").addClass("articleTitle");
                curr_art.append($("<a></a>").text(art.title));
                curr_art.attr("href", "http://localhost:8080/articles/" + art.id);
                curr_art_line.append(curr_art);

                let edit_button = $("<button></button>").addClass("article_line_button");
                edit_button.click(function () {
                    window.location.href = "http://localhost:8080/articles/" + art.id + "?edit=true";
                });
                edit_button.text("Edit");
                curr_art_line.append(edit_button);

                let publish_button = $("<button></button>").addClass("article_line_button");
                publish_button.click(function () {
                    let url = "http://localhost:8080/articles/" + art.id;
                    let toPublish = true;
                    if(art.isPublished)
                        toPublish = false;

                    fetch(url, {
                        method: "PUT",
                        body: JSON.stringify({
                            "title": null,
                            "text": null,
                            "isPublished": toPublish
                        }),
                        headers: {
                            "Content-type": "application/json; charset=UTF-8"
                        }
                    }).then(resp => {
                        if(resp.status === 200) {
                            location.reload();
                        }
                        console.log(resp.status);
                    });
                });
                if(art.isPublished)
                    publish_button.text("Unpublish");
                else
                    publish_button.text("Publish");
                curr_art_line.append(publish_button);

                $("#user_articles").append(curr_art_line);
            });
        });
}