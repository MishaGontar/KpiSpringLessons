// <script th:inline="javascript">
//     async function urlAPI(url) {
//     await fetch(url).then(r => console.log(r)).catch(e => console.log(e));
//     await postAllUsers("list/" + url.split("/")[3])
//     location.reload()
// }
//
//     async function activeBtn(url) {
//     await fetch(url).then(r => console.log(r)).catch(e => console.log(e));
//     const a = document.getElementById("statusButton");
//     (a.innerText === "Open") ? a.innerText = "Close" : a.innerText = "Open"
// }
//
//     async function postAllUsers(url) {
//     console.log(url, "WHERE!")
//     await fetch(url)
//     .then(response => response.json())
//     .then(data => {
//     console.log(data)
//     const tableUser = document.getElementById("users")
//     tableUser.innerText = ""
//     for (let a = 0; a < data.length; a++) {
//     const user = data[a]
//     tableUser.innerText += `<tr><td>${user.firstName} ${user.lastName}</td>` + btnOwner(data["id"]) + `</tr>`
// }
// })
// }
//
//     function btnOwner(userID) {
//     if (/*[[${isOwner}]]*/) {
//     return `<td>
//                         <button type="button" class="btn btn-danger"
//                                 onclick=urlAPI('delete/'+/*[[${queue.id}]]*/+'/'+${userID})">
//                                 Delete user
//                             </button>
//                         </td>`
// }
//     return ""
// }
// </script>