package sb.rf.generalchat.controller.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sb.rf.generalchat.service.YouTubeVideoSearcher;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/user")
public class YouTubeSearchController {
    @Autowired
    private YouTubeVideoSearcher videoSearcher;

    @GetMapping("/getVideosYoutube/{videoName}")
    public ResponseEntity<String> getYouTubeSearch(@PathVariable("videoName") String videoName)  {
        log.info(videoName.toString());
        log.info("count {}",videoName.substring(0,1));
        log.info("name {}",videoName.substring(1));

        return   ResponseEntity.ok(videoSearcher.getVideoName(videoName.substring(0,1), videoName.substring(1)));
    }
}
