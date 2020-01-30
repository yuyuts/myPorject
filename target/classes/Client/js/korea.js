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
        '<th>Region</th>'+
        '</tr>';
    fetch('/team/list', {method: 'get'}
    ).then(response => response.json()
    ).then(teams => {
        for (let team of teams) {
            teamHTML += '<tr>' +
                `<td>${team.teamID}</td>` +
                `<td><img src ='/client/img/${team.Image}'
                    alt ='Picture of ${team.teamName}' height ='100px'></td>` +
                `<td>${team.teamName}</td>` +
                `<td>${team.Earnings}</td>` +
                `<td>${team.teamBio}</td>` +
                `<td>${team.standingPo}</td>` +
                `<td>${team.coachID}</td>` +
                `<td>${team.ownerID}</td>` +
                `<td>${team.regionID}</td>`+
                `<td class ='last'>` +
                `<button class ='editButton' data-id='${team.teamID}'>EDIT</button>` +
                `<button class ='deleteButton' data-id='${team.teamID}'>DELETE</button>` +
                `</td>` +
                `</tr>`;

        }
        teamHTML += '</table>';
        document.getElementById("listKR").innerHTML = teamHTML;
        let editButtons = document.getElementsByClassName("editButton");
        for (let button of editButtons) {
            button.addEventListener("click", editTeam);
        }
        let deleteButtons = document.getElementsByClassName("deleteButton");
        for (let button of deleteButtons) {
            button.addEventListener("click", deleteTeam);
        }
        checkLogin();
    });
    document.getElementById("saveButton").addEventListener("click", saveEditTeam);
    document.getElementById("cancelButton").addEventListener("click",cancelEditTeam);
}

function editTeam(event) {
    const  id = event.target.getAttribute("data-id");
    if(id === null){
        document.getElementById("editHeading").innerHTML ='Add a new Team:';

        document.getElementById("teamID").value = ' ';
        document.getElementById("teamImage").value=' ';
        document.getElementById("teamName").value =' ';
        document.getElementById("teamEarnings").value=' ';
        document.getElementById("teamBio").value =' ';
        document.getElementById("standingPosition").value =' ';
        document.getElementById("coachName").value =' ';
        document.getElementById("ownerName").value=' ';
        document.getElementById("regionID").value=' ';
        document.getElementById("listKR").style.display ='none';
        document.getElementById("editKR").style.display ='block';

    }else{
        fetch('/team/get/'+id,{method:'get'}
        ).then(response => response.json()
        ).then(team=>{
            if(team.hasOwnProperty('error')){
                alert(team.error);
            }else{
                document.getElementById("editHeading").innerHTML = 'Editing'+ team.teamName+ ':';
                document.getElementById("teamID").value = team.teamID;
                document.getElementById("teamImage").value = team.Image;
                document.getElementById("teamName").value = team.name;
                document.getElementById("teamEarnings").value = team.Earnings;
                document.getElementById("teamBio").value = team.teamBio;
                document.getElementById("StandingPosition").value = team.standingPo;
                document.getElementById("coachName").value = team.coachID;
                document.getElementById("ownerName").value= team.ownerID;
                document.getElementById("regionName").value= team.regionID;

                document.getElementById("listKR").style.display ='none';
                document.getElementById("editKR").style.display ='block';

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
    if(document.getElementById("regionName").value.trim()==''){
        alert("please provide the owner of the team")
        return;
    }
    const teamID = document.getElementById("teamID").value;
    const form = document.getElementById("krForm")
    const formData = new FormData(form);

    let apiPath = ' ';
    if(teamID ===' '){
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
            document.getElementById("listKR").style.display = 'block';
            document.getElementById("editKR").style.display= 'none';
            pageLoad();
        }
    }
    )
}
function cancelEditTeam(event) {
    event.preventDefault();
    document.getElementById("listKR").style.display ='block';
    document.getElementById("editKR").style.display ='none';
}
function deleteTeam(event){
    const ok = confirm("Are you sure?");
    if(ok == true){
        let teamID = event.target.getAttribute("data-id");
        let formData = new FormData();
        formData.append("teamID", teamID);
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
    checkLogin();


}
