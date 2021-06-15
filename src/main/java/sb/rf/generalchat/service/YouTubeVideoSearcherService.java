package sb.rf.generalchat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;

@Service
@Slf4j
public class YouTubeVideoSearcherService {

    private RestTemplate restTemplate = new RestTemplateBuilder().build();
    @Autowired
    ObjectMapper objectMapper;
    private String BASE_URL = "https://www.googleapis.com/youtube/v3/search?fields=items(id/kind,id/videoId,snippet/title,snippet/description,snippet/publishedAt,snippet/thumbnails/default/url)&key=AIzaSyAea0nVkFuNfkN296MN0MYVjxog0dRb5-Q&maxResults=###&part=id,snippet&q=!!!!&type=video";
    public String getVideoName(String count ,String videoId)  {

        log.info(BASE_URL.replace("!!!!",videoId).replace("###",count));
        String responseText= restTemplate.getForObject(BASE_URL.replace("!!!!",videoId).replace("###",count),String.class);
        return responseText;
    }
}
