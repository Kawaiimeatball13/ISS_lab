function on_username_click() {
    if($("#username_div").html() === "Log in") {
        window.location.replace("http://localhost:8080/login");
    }
    else {
        window.location.replace("http://localhost:8080/users/" + userId);
    }
}

$(document).ready(function() {
    let articlesUrl = "http://localhost:8080/articles?published=true";

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
            console.log(r);
            return r.json();
        })
        .then(data => {
            data.forEach(art => {
                let curr_art_anch = $("<a></a>");
                curr_art_anch.attr("href", "http://localhost:8080/articles/" + art.id);
                curr_art_anch.addClass("articleSquare");

                let curr_art = $("<div></div>");
                curr_art.append($("<div></div>").text(art.title).addClass("articleTitle"));
                curr_art.append($("<div></div>").text(art.author.name + art.author.familyName).addClass("articleAuthor"));

                curr_art_anch.append(curr_art);
                $("#all_articles").append(curr_art_anch);
            });
        });
});