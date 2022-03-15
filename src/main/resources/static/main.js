var welcomeMsg = 'List of the Pot Holes'

document.querySelector('h1').innerText = welcomeMsg;

fetch('/potHolesList').then(resp => resp.json()).then(potHolesList => {
        document.querySelector('body').innerHTML = listPotHolesList(potHolesList);
        }
    );

let listPotHoleList = function(potHoleList){
    return '<p>' + potHoleList.houseNumber + ": " + potHoleList.streetName + '</p>';
}

function listPotHolesList(json){
    return `
        <div id="listPotHolesList">
            ${json.map(listPotHoleList).join('\n')}
        </div>
    `
}



// function get(url) {
//     return new Promise(function (resolve,reject) {
//         var req = new XMLHttpRequest();
//         req.open('GET',url);
//         req.onload = function() {
//             if (req.status == 200){
//                 resolve(req.response);
//             }else {
//                 reject(Error(req.statusText));
//             }
//         }
        
//         req.onerror = function() {
//             reject(Error("Network Error"));
//         }
        
//         req.send();
//     });
// }


// get('/potHolesList').then(function(response){
//     console.log(JSON.parse(response));
// }, function(err){

// })


