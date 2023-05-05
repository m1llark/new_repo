const url = 'http://localhost:8080/users/';
const nav = document.getElementById('MyPanel2')
const userTable = document.getElementById('userTableBody2')

getSecondUser()



function getSecondUser() {
    let output3 = ""
    fetch(url + 'authenticated')
        .then(res => res.json())
        .then(user => {
            output3  +=
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
            userTable.innerHTML = output3
        })
        .catch(err => {
            console.error('Error: ', err)
        })

}