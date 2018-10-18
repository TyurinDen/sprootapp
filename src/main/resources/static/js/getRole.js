function getRole() {
    $.ajax({
        type: "GET",
        url: 'users/api/getPrincipal',
        success: function (user) {
            console.log(user);
        },
        error: function (e) {
            //console.log("ERROR!!!");
            window.location.replace("/login");
        }
    })
}