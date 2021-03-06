package sb.rf.generalchat.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class YouTubeVideo {

  private String title;
  private String url;
  private String thumbnailUrl;
  private String publishDate;
  private String description;
}
