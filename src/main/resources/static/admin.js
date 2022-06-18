const url = 'http://localhost:8080/api/admin/'
const addUserForm = document.querySelector('#addUser')
const editUserForm = document.querySelector('#modalEdit')
const deleteUserForm = document.querySelector('#modalDelete')
let currentUserId = null;

function getAllUsers() {
    fetch(url)
        .then(res => res.json())
        .then(users => {
            let temp = '';
            users.forEach(function (user) {
                temp += `
                  <tr>
                        <td>${user.id}</td>
                        <td>${user.firstname}</td>
                        <td>${user.lastname}</td>
                        <td>${user.age}</td>
                        <td>${user.email}</td>
                        <td>${user.roles.map(role => role.name === 'ROLE_USER' ? ' USER' : ' ADMIN')}</td>
                  <td>
                       <button type="button" data-userid="${user.id}" data-action="edit" class="btn btn-info"
                        data-toggle="modal" data-target="modal" id="edit-user" data-id="${user.id}">Edit</button>
                   </td>
                   <td>
                       <button type="button" class="btn btn-danger" id="delete-user" data-action="delete"
                       data-id="${user.id}" data-target="modal">Delete</button>
                        </td>
                  </tr>`;
            });
            document.querySelector('#allUsers').innerHTML = temp;
        });
}
getAllUsers()

function refreshTable() {
    let table = document.querySelector('#allUsers')
    while (table.rows.length > 1) {
        table.deleteRow(1)
    }
    setTimeout(getAllUsers, 50);
}

///////////ADD user/////////////////////
const addFirstName = document.getElementById('firstname1')
const addLastName = document.getElementById('lastname1')
const addAge = document.getElementById('age1')
const addPassword = document.getElementById('password1')
const addRoles = document.getElementById('roles1')
const addEmail = document.getElementById('email1')

const on = (element, event, selector, handler) => {
    element.addEventListener(event, e => {
        if (e.target.closest(selector)) {
            handler(e)
        }
    })
}
addUserForm.addEventListener('submit', (e) => {
    e.preventDefault();
    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            firstname: addFirstName.value,
            lastname: addLastName.value,
            age: addAge.value,
            email: addEmail.value,
            password: addPassword.value,
            roles: [
                addRoles.value
            ]
        })
    })
        .then(res => res.json())
        .then(data => {
           let users = data;
            getAllUsers(users);
        })
        .then(res => {
            document.getElementById('add_new_user').click()

        })
    refreshTable()
});


//////////// EDIT user////////////////

on(document, 'click', '#edit-user', e => {
    const userInfo = e.target.parentNode.parentNode
    document.querySelector('#id2').value = userInfo.children[0].innerHTML
    document.getElementById('firstname2').value = userInfo.children[1].innerHTML
    document.getElementById('lastname2').value = userInfo.children[2].innerHTML
    document.getElementById('age2').value = userInfo.children[3].innerHTML
    document.getElementById('email2').value = userInfo.children[4].innerHTML
    document.getElementById('password2').value = userInfo.children[5].innerHTML
    document.getElementById('roles2').value = userInfo.children[6].innerHTML
    $("#modalEdit").modal("show")

})


editUserForm.addEventListener('submit', (e) => {
    e.preventDefault();
    fetch(url, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            id: document.getElementById('id2').value,
            firstname: document.getElementById('firstname2').value,
            lastname: document.getElementById('lastname2').value,
            age: document.getElementById('age2').value,
            email: document.getElementById("email2").value,
            password: document.getElementById('password2').value,
            roles: [
                document.getElementById('roles2').value
            ]
        })
    }).then()


    $("#modalEdit").modal("hide")
    refreshTable()
})

/////////Delete user/////////////////////////////////
deleteUserForm.addEventListener('submit', (e) => {
    console.log(e.target.parentNode.parentNode)
    e.preventDefault();
    e.stopPropagation();
    fetch(url + currentUserId, {
        method: 'DELETE'
    })
        .then()

    $("#modalDelete").modal("hide")
    // alert('Пользователь удален')
    refreshTable()
})

on(document, 'click', '#delete-user', e => {
    const fila2 = e.target.parentNode.parentNode
    currentUserId = fila2.children[0].innerHTML

    document.getElementById('id3').value = fila2.children[0].innerHTML
    document.getElementById('firstname3').value = fila2.children[1].innerHTML
    document.getElementById('lastname3').value = fila2.children[2].innerHTML
    document.getElementById('age3').value = fila2.children[3].innerHTML
    document.getElementById('email3').value = fila2.children[4].innerHTML
    document.getElementById('roles3').value = fila2.children[5].innerHTML
    $("#modalDelete").modal("show")
})