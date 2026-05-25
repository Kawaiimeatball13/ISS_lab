var jq = document.createElement('script');
jq.src = 'https://code.jquery.com/jquery-3.6.3.min.js';
document.getElementsByTagName('head')[0].appendChild(jq);

function on_signup_click(redirect_path) {
  //TO DO: add validation
  
  let name = $("#name_field").val();
  let familyName = $("#family_name_field").val();
  let email = $("#email_field").val();
  let password = $("#password_field").val();
  let passwordConf = $("#password_conf_field").val();
  
  let signupUrl = "http://localhost:8080/auth/signup";
  let signupRequest = {
    name: name,
    familyName: familyName,
    email: email,
    password: password,
  };
  
  fetch(signupUrl, {
    method: "POST",
    body: JSON.stringify(signupRequest),
    headers: {
			"Content-type": "application/json; charset=UTF-8"
		}
  }).then(response => {
      if(response.ok) {
          window.location.replace("http://localhost:8080/login_redirect");
      }
      else{
          alert(response.text());
      }
    });

  
}