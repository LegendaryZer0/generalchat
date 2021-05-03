$(document).ready(function () {
    $('#youTubeSearch').click(function () {
        /* let sendSearchCriteria = {
             queryTerm:$('#YBSearchCriteria').val()

         }*/

        $.ajax({
            type: 'GET',
            url: 'https://www.googleapis.com/youtube/v3/search?fields=items(id/kind,id/videoId,snippet/title,snippet/description,snippet/publishedAt,snippet/thumbnails/default/url)&key=AIzaSyAea0nVkFuNfkN296MN0MYVjxog0dRb5-Q&maxResults=7&part=id,snippet&q='+$('#YBSearchCriteria').val().replace(" ","%20") +'&type=video',
            /*  data: sendSearchCriteria,*/

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
                for (let video in data['items']){
                    console.log("video is:")
                    console.log(data['items'][video])
                    finData +="<a href="+ "https://www.youtube.com/watch?v="+ data['items'][video]['id']['videoId']  +" class=\"list-group-item list-group-item-action active\">"+ data['items'][video]['snippet']['title'] +"\n" +
                        "       <img alt=\"\"src="+ data['items'][video]['snippet']['thumbnails']['default']['url'] + " />                                    </a>"+
                        "<p >"+data['items'][video]['snippet']['description']+"</p>"
                    +"<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/"+data['items'][video]['id']['videoId']+"\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>"
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