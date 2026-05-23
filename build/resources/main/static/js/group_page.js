function load_all_members(groupId) {
    let membersUrl = "http://localhost:8080/users?group=" + groupId;

    let headers = new Headers();
    headers.append('Accept', 'application/json');
    let request = new Request(
        membersUrl,
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
            data.forEach(member => {
                let curr_mem = $("<div></div>");
                curr_mem.addClass("member_line");

                let text = member.name + ' ' + member.familyName;
                if(member.id === adminId) {
                    text += '(admin)';
                }

                curr_mem.text(text);
                $("#all_members").append(curr_mem);
            });
        });
}

function load_all_articles(groupId) {
    let articlesUrl = "http://localhost:8080/articles?group=" + groupId;

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
}
