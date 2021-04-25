$(document).ready(function () {
    $('#youTubeSearch').click(function () {
        let sendSearchCriteria = {
            queryTerm:$('#YBSearchCriteria').val()

        }

        $.ajax({
            type: 'GET',
            url: 'getVideos',
            data: sendSearchCriteria,

            beforeSend: function (xhr) {
                console.log("Дата:")
                console.log(this.data)
                console.log("Дата перед отправкой" + this.data)

                //nothing needed here
                //usually you can display a loading message
            },
            success: function (data) {
                console.log(data);
                let finData;
                for (let video in data){
                    console.log("video is:")
                    console.log(data[video])
                    finData +="<a href="+  data[video]['url']  +" class=\"list-group-item list-group-item-action active\">"+ data[video]['title'] +"\n" +
                        "       <img alt=\"\"src="+ data[video]['thumbnailUrl'] + " />                                    </a>"+
                        "<p >"+data[video]['description']+"</p>"
                }
                console.log('Findata :')
                console.log(finData)
                $("#youTubeSearchResults").html(
                    finData
                )
               /* $("youTubeSearchResults").append(
                    finData
                )*/


                /*    if(data.status===200) {
                        console.log("Показываю результат ajax")
                        $('#x').html(data);
                    }*/


                //on success show something to the user
                //data is the html returned from the calling page, in your case the servlet
                //put the html in the correct place
            }
        })






    })

})