package sb.rf.generalchat.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class YoutubeSearchCriteria {
    @Size(min=5, max=64, message="Search term must be between 5 and 64 characters")
    private String queryTerm;

    public String getQueryTerm() {
        return queryTerm;
    }

    public void setQueryTerm(String queryTerm) {
        this.queryTerm = queryTerm;
    }

}
