function pageLoad(){
    if(window.location.search ==='?logout'){
        document.getElementById('content').innerHTML ='<h1> Logging Out</h1>;
            logout();
    }else{
        document.getElementById("loginButton").addEventListener("click",login);
    }
}
function  login(event) {
    event.preventDefault();
    const form = document.getElementById("loginForm")
    const formData = new FormData(form);
    fetch("/user/login",{method: 'post', body: formData}
    ).then(response => response.json()
    ).then(responseData =>{
        if(responseData.hasOwnProperty('error')){
            alert(responseData.error)
        }else{
            Cookies.set("username", responseData.username);
            Cookies.set("token",responseData.token);
            window.location.href = '/client/index.html';

        }
    });
}

function logout() {
    fetch("/user/logout",{method: 'post'}
    ).then(response => response.json()
    ).then(responseData =>{
        if (responseData.hasOwnProperty('error')){
            alert(responseData.error)
        }else {
            Cookies.remove("username");
            Cookies.remove("token");
            window.location.href ='/client/index.html';

        }
        }
    )
}
function checkLogin() {
    let username = Cookies.get("username");
    let loginHTML =" ";
    if(username === undefined){
        let editButtons = document.getElementsByClassName("editButton");
        for(let button of editButtons){
            button.style.visibility ="hidden";
        }
        let deleteButtons = document.getElementsByClassName("deleteButton");
        for(let button of deleteButtons){
            button.style.visibility = "hidden";
        }
        loginHTML ="Not Logged in. <a href='/client/login.html'>Log in</a>";

    }
    
}