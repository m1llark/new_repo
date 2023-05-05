/**
 *
 */
const url = 'http://localhost:8080/users/';
const usersTable = document.getElementById('tableBody')
const userTable = document.getElementById('userTableBody')
const roleById = document.getElementById('roles');
const editRoleById = document.getElementById('roles2');
const nav = document.getElementById('MyPanel')
const select = document.querySelector('#roles2').getElementsByTagName('option');
const select1 = document.querySelector('#roles3').getElementsByTagName('option');


getAllUsers();
getAuthenticatedUser();

function getAllUsers() {

    let output = "";
    fetch(url)
        .then(res => res.json())
        .then(users =>
        {users.forEach(user =>

            output  +=

                        `<tr> <td>`
                        + user.id
                        + `</td> <td>`
                        + user.username
                        + `</td> <td>`
                        + user.email
                        + `</td> <td>`
                        + user.age
                        + `</td> <td>`
                        + JSON.stringify(user.roles.map(role => role.name + " ")).replace(/[^\w\s!?]/g,'')
                        + '</td> <td><input type="button"  class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#modalTwo" onclick="editStudent('
                        + user.id
                        + ')"  value="Edit"></input></td> <td> <input type="button" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#modal3" onclick="deleteStudent('
                        + user.id
                        + ');" value="Delete"></input></td> </tr>');

        })
        .then(() => {
            usersTable.innerHTML = output
        })
        .catch(err => {
            console.error('Error: ', err)
        })
}


function getAuthenticatedUser() {
    let output2 = ""
    fetch(url + 'authenticated')
        .then(res => res.json())
        .then(user => {
            output2  +=
                `<tr> <td>`
                + user.id
                + `</td> <td>`
                + user.username
                + `</td> <td>`
                + user.email
                + `</td> <td>`
                + user.age
                + `</td> <td>`
                + JSON.stringify(user.roles.map(role => role.name + " ")).replace(/[^\w\s!?]/g,'')
                + `</td> </tr>`

            nav.innerHTML = user.email + " with roles: " + JSON.stringify(user.roles.map(role => role.name + " ")).replace(/[^\w\s!?]/g,'')
        })
        .then(() => {
            userTable.innerHTML = output2
        })
        .catch(err => {
            console.error('Error: ', err)
        })

}











$('#saveStudent').click(function () {

    const roles = [];
    for (let i = 0; i < roleById.options.length; i++) {
        if (roleById.options[i].selected) {
            roles.push({
                id: roleById.options[i].value,
                name: roleById.options[i].text
            });
        }
    }

    fetch(url, {
        method: "POST",
        body: JSON.stringify({
            username: $("#username").val(),
            email: $("#email").val(),
            age: $("#age").val(),
            password: $("#password").val(),
            roles: roles
        }),
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        }
    })
        .then(res => res.json())
        .then(() => {
            getAllUsers();
            $("#form1")[0].reset();
            const triggerEl = document.querySelector('#myTab button[data-bs-target="#users_table"]')
            bootstrap.Tab.getInstance(triggerEl).show()
        })
        .catch(error => {
            console.log(error)
        })
});


function editStudent(id) {
    fetch(url + id)
        .then(res => res.json())
        .then(response => {

            $("#id2").val(response.id), $("#username2").val(response.username), $("#email2").val(response.email), $(
                "#age2").val(response.age);
            // $("#password2").val(response.password)
            checkRoles(id)
            $('.one').on('click', function () {
                editStudentAfter(id)

            });
        })
        .catch(error => {
            console.log(error)
        })
}
function checkRoles(id) {
    fetch(url + id)
        .then(res => res.json())
        .then(response => {
                if (response.roles.map(role => role.name).includes('ADMIN')) {
                    select[1].selected = true;
                }
                if (response.roles.map(role => role.name).includes('USER')) {
                    select[0].selected = true;
                }
        })
}
function checkRolesForDelete(id) {
    fetch(url + id)
        .then(res => res.json())
        .then(response => {
            if (response.roles.map(role => role.name).includes('ADMIN')) {
                select1[1].selected = true;
            }
            if (response.roles.map(role => role.name).includes('USER')) {
                select1[0].selected = true;
            }
        })
}






function editStudentAfter(id) {


    const roles2 = [];
    for (let i = 0; i < editRoleById.options.length; i++) {
        if (editRoleById.options[i].selected) {
            roles2.push({
                id: editRoleById.options[i].value,
                name: editRoleById.options[i].text
            });
        }
    }


    fetch(url + id, {
        method: "PUT",
        body: JSON.stringify({
            id: $("#id2").val(),
            username: $("#username2").val(),
            email: $("#email2").val(),
            age: $("#age2").val(),
            password: $("#password2").val(),
            roles: roles2
        }),
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        }
    })
        .then(res => res.json())
        .then(json => {
            getAllUsers();
            $('#userForm2')[0].reset()
            $("#modalTwo").modal('hide');
            console.log(json)
            select[1].selected = false;
            select[0].selected = false;

        })
        .catch(error => {
            console.log(error)
        })
}


function deleteStudent(id) {
    fetch(url + id)
        .then(res => res.json())
        .then(response => {
            $("#id3").val(response.id), $("#username3").val(response.username), $("#email3").val(response.email), $(
                "#age3").val(response.age), $("#password3").val(response.password), $(
                "#roles3").val(response.roles)

            checkRolesForDelete(id)
            $('.three').on('click', function () {
                deleteStudentAfter(id)
            });
        })
        .catch(error => {
            // enter your logic for when there is an error (ex. error toast)
            console.log(error)
        })
}
function deleteStudentAfter(id) {
    fetch(url + id, {
        method: 'DELETE'
    })
        .then(() => {
            getAllUsers();
            $("#modal3").modal('hide');
        })
        .catch(error => {
            console.log(error)
            select[1].selected = false;
            select[0].selected = false;
        })
}



















