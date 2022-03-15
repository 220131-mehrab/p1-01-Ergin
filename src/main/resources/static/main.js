var welcomeMsg = 'List of the Pot Holes'

document.querySelector('h1').innerText = welcomeMsg;

fetch('/potHolesList').then(resp => resp.json()).then(potHolesList => {
        document.querySelector('#potHolesList').innerHTML = listPotHolesList(potHolesList);
        }
    );

function listPotHolesList(json){
    return `${json.map(listPotHoleList).join('\n')}`;
};

let listPotHoleList = function(potHoleList){
    return '<p>' + potHoleList.houseNumber + ": " + potHoleList.streetName + '</p>';
};

function postAddress(){
    let address = {
        "houseNumber":document.getElementById("houseNumber").value,
        "streetName" :document.getElementById("streetName").value
    }
    fetch("/potHolesList",{
        method:"POST",
        headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
        },
        body: JSON.stringify(address)
    }).then((result)=>{
        if (result.status!=200){
            throw new Error("unable to post");
        }
        console.log(result.text());
    }).catch((error)=>{console.log(error);})
    fetch('/potHolesList').then(resp => resp.json()).then(potHolesList => {
        document.querySelector('#potHolesList').innerHTML = listPotHolesList(potHolesList);
        }
    );
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


