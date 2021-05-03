
    $(document).ready(function () {
        let finData;
    $('#submit').click(function () {
        var login = $('#login').val();
        var password =$('#password').val();

        let chex;
        if($('#chex').is(':checked')){
            chex=true;
        }else {
            chex=false;
        }

        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        console.log(token);
        console.log(header);
        console.log(chex)
        let sendData ={
            email: login,
            password: password,
            remember_me:chex,
            confirm: confirm,
            token:header
        /*    isChecked: chex,*/

        }
        function encode(data) {
            let ret = ''
            for (let it in data) {
                ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&'
            }
            return ret
        }
        sendData= encode(sendData);



        $.ajax({
           /* contentType: "application/json;charset=UTF-8",*/
            type: 'POST',
            url: 'login',
            data:sendData,

            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },



            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
                console.log("Дата:")
                console.log(this.data)
                console.log("Дата перед отправкой" + this.data)

                //nothing needed here
                //usually you can display a loading message
            },
            success: function (data) {
                console.log("AJAX data пришла");
                console.log(this.status);
                console.log("СтатусКод");
                console.log(this.statusCode);
                console.log("Показываю дату");
                console.log(data);
                finData=data;


            /*    if(data.status===200) {
                    console.log("Показываю результат ajax")
                    $('#x').html(data);
                }*/


                //on success show something to the user
                //data is the html returned from the calling page, in your case the servlet
                //put the html in the correct place
            },
            statusCode:{
                200:function (){
                    window.location.replace('user/selfProfile'); //Todo Спросить, как правильней это обрабатывать
                },
                203:function (){
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
