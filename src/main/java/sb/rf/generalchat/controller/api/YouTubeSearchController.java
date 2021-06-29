package sb.rf.generalchat.controller.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sb.rf.generalchat.service.YouTubeVideoSearcherService;

@Slf4j
@RestController
@RequestMapping("/user")
public class YouTubeSearchController {
  @Autowired private YouTubeVideoSearcherService videoSearcher;

  @GetMapping("/getVideosYoutube/{videoName}")
  public ResponseEntity<String> getYouTubeSearch(@PathVariable("videoName") String videoName) {
    log.info(videoName);
    log.info("count {}", videoName.charAt(0));
    log.info("name {}", videoName.substring(1));

    return ResponseEntity.ok(
        videoSearcher.getVideoName(videoName.substring(0, 1), videoName.substring(1)));
  }
}
