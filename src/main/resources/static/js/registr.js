

$(document).ready(function () {
    let finData;
    $('#submit-btn').click(function () {
        let login = $('#login').val();
        let password = $('#password').val();
        let confirm = $('#confirm').val();

        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");

        let sendData ={
            login: login,
            password: password,


        }
        console.log(login);
        console.log(password);
        console.log(confirm);
        $.ajax({
          /*  headers: {
                'Authorization':'Basic xxxxxxxxxxxxx',
                'X-CSRF-TOKEN':'xxxxxxxxxxxxxxxxxxxx',
                'Content-Type':'application/json'
            },*/

            type: 'POST',
            url: 'register',
            contentType: "application/json",
            data: JSON.stringify(sendData),
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
                console.log("Дата:")
                console.log(this.data)
                //nothing needed here
                //usually you can display a loading message
            },
            success: function (data) {
                console.log(data);
                finData=data


                //on success show something to the user
                //data is the html returned from the calling page, in your case the servlet
                //put the html in the correct place
            },
            statusCode:{
                302: function (){
                    console.log("Перевожу на профиль");
                    window.location.replace('confirmPage');
                },
                200:function (){
                    console.log("Показываю результат ajax")
                    $('#x').html(finData);
                }
            }
            /* error: function () {
                 //show some error message here
             }*/
        });
    });
});



