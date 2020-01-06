function  pageLoad() {
    let teamHTML = '<table>' +
        '<tr>' +
        '<th>ID</th>' +
        '<th>Logo</th>' +
        '<th>Team</th>' +
        '<th>Earnings</th>' +
        '<th>Bio</th>' +
        '<th>Standing</th>'+
        '<th>Coach</th>' +
        '<th>Owner</th>'+
        '</tr>';
    fetch('/team/list', {method: 'get'}
    ).then(response => response.json()
    ).then(teams => {
        for (let team of teams) {
            teamHTML += '<tr>' +
                `<td>${team.id}</td>` +
                `<td><img src ='/client/img/${team.image}'
                    alt ='Picture of ${team.name}' height ='100px'></td>` +
                `<td>${team.name}</td>` +
                `<td>${team.earnings}</td>` +
                `<td>${team.bio}</td>` +
                `<td>${team.position}</td>` +
                `<td>${team.coach}</td>` +
                `<td>${team.owner}</td>` +
                `<td class ='last'>` +
                `<button class ='editButton' data-id='${team.id}'>EDIT</button>` +
                `<button class ='deleteButton' data-id='${team.id}'>DELETE</button>` +
                `</td>` +
                '</tr>'

        }
        teamHTML += '</table>';
        document.getElementById("listDiv").innerHTML = teamHTML;
        let editButtons = document.getElementsByClassName("editButton");
        for (let button of editButtons) {
            button.addEventListener("click", editTeam);
        }
        let deleteButtons = document.getElementsByClassName("deleteButton");
        for (let button of deleteButtons) {
            button.addEventListener("click", deleteTeam);
        }
    });
    document.getElementById("saveButton").addEventListener("click", saveEditTeam);
    document.getElementById("cancelButton").addEventListener("click",cancelEditTeam);
}

function editTeam(event) {
    const  id = event.target.getAttribute("data-id");
    if(id === null){
        document.getElementById("editHeading").innerHTML ='Add a new Team:';
        document.getElementById("teamID").value = '';
        document.getElementById("teamImage").value='';
        document.getElementById("teamName").value ='';
        document.getElementById("teamEarnings").value='';
        document.getElementById("teamBio").value ='';
        document.getElementById("standingPosition").value ='';
        document.getElementById("coachName").value ='';
        document.getElementById("ownerName").value='';
        document.getElementById("listDiv").style.display ='none';
        document.getElementById("editDiv").style.display ='block';
    }else{
        fetch('/team/get'+id,{method:'get'}
        ).then(response => response.json()
        ).then(team=>{
            if(team.hasOwnProperty('error')){
                alert(team.error);
            }else{
                document.getElementById("editHeading").innerHTML = 'Editing'+ team.name+ ':';
                document.getElementById("teamID").value = id;
                document.getElementById("teamImage").value = team.image;
                document.getElementById("teamName").value = team.name;
                document.getElementById("teamEarnings").value = team.earnings;
                document.getElementById("teamBio").value = team.bio;
                document.getElementById("StandingPosition").value = team.position;
                document.getElementById("coachName").value = team.coach;
                document.getElementById("ownerName").value= team.owner;


            }


            });
    }
    
}
function saveEditTeam(event) {
    event.preventDefault();
    if(document.getElementById("teamName").value.trim() ===''){
        alert("Please provide a team name")
        return;
    }
    if(document.getElementById("teamImage").value.trim()===''){
        alert("please provide a team image")
        return;
    }
    if(document.getElementById("teamEarnings").value.trim()===''){
        alert("please provide the team earnings")
        return;
    }
    if(document.getElementById("teamBio").value.trim()===''){
        alert("please provide a team Bio")
        return;
    }
    if(document.getElementById("StandingPosition").value.trim()===''){
        alert("please provide the standing position of the team")
        return;
    }
    if(document.getElementById("coachName").value.trim()===''){
        alert("please provide the name of the coach for the team")
        return;
    }
    if(document.getElementById("ownerName").value.trim()=='') {
        alert("please provide the owner of the team")
        return;
    }
    const id = document.getElementById("teamID").value;
    const form = document.getElementById("teamForm")
    const formData = new FormData(form);

    let apiPath = '';
    if(id ===''){
        apiPath ='/team/new';
    }else{
        apiPath ='/team/update';
    }
    fetch(apiPath,{method: 'post', body: formData}
    ).then(response => response.json()
    ).then(responseData =>{
        if(responseData.hasOwnProperty('error')){
            alert(responseData.error);
        }else{
            document.getElementById("listDiv").style.display = 'block';
            document.getElementById("editDiv").style.display= 'none';
            pageLoad();
        }
    }
    )
}
function cancelEditTeam(event) {
    event.preventDefault();
    document.getElementById("listDiv").style.display ='block';
    document.getElementById("editDiv").style.display ='none';
}
function deleteTeam(event){
    const ok = confirm("Are you sure?");
    if(ok == true){
        let id = event.target.getAttribute("data-id");
        let formData = new FormData();
        formData.append("id", id);
        fetch('/team/delete',{method: 'post',body:formData}
        ).then(response => response.json()
        ).then(responseData =>{
            if(responseData.hasOwnProperty('error')){
                alert(responseData.error);
            }else{
                pageLoad();
            }
            }
        )
    }
}