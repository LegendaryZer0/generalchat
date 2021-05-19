package sb.rf.generalchat.service;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sb.rf.generalchat.model.YouTubeVideo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Slf4j
@Service
public class YouTubeService {

    private static final long MAX_SEARCH_RESULTS = 7;


    public List<YouTubeVideo> fetchVideosByQuery(String queryTerm) {
        List<YouTubeVideo> videos = new ArrayList<YouTubeVideo>();

        try {

            YouTube youtube = getYouTube();


            YouTube.Search.List search = youtube.search().list("id,snippet");


            String apiKey = "AIzaSyAea0nVkFuNfkN296MN0MYVjxog0dRb5-Q";
            search.setKey(apiKey);


            search.setQ(queryTerm);


            search.setType("video");


            search.setFields("items(id/kind,id/videoId,snippet/title,snippet/description,snippet/publishedAt,snippet/thumbnails/default/url)");


            search.setMaxResults(MAX_SEARCH_RESULTS);

            DateFormat df = new SimpleDateFormat("MMM dd, yyyy");


            SearchListResponse searchResponse = search.execute();
            log.info("base url {}",youtube.getBaseUrl());
            log.info( " servicepath {}",youtube.getServicePath());
            ;
            log.info("search object is {}",search.toString());
            log.info("UriTemplate is {}",search.getUriTemplate());
            List<SearchResult> searchResultList = searchResponse.getItems();
            if (searchResultList != null) {
                for (SearchResult result : searchResultList) {
                    YouTubeVideo video = new YouTubeVideo();
                    video.setTitle(result.getSnippet().getTitle());
                    video.setUrl(buildVideoUrl(result.getId().getVideoId()));

                    video.setThumbnailUrl(result.getSnippet().getThumbnails().getDefault().getUrl());
                    video.setDescription(result.getSnippet().getDescription());

                    //parse the date
                    DateTime dateTime = result.getSnippet().getPublishedAt();
                    Date date = new Date(dateTime.getValue());
                    String dateString = df.format(date);
                    video.setPublishDate(dateString);

                    videos.add(video);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return videos;
    }



    private String buildVideoUrl(String videoId) {
        StringBuilder builder = new StringBuilder();
        builder.append("https://www.youtube.com/watch?v=");
        builder.append(videoId);

        return builder.toString();
    }



    private YouTube getYouTube() {
        YouTube youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(),
                (reqeust) -> {}).setApplicationName("youtube-spring-boot-demo").build();

        return youtube;
    }



}
