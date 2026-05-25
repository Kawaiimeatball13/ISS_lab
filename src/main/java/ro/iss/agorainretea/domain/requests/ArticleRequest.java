package ro.iss.agorainretea.domain.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleRequest {
    private String text;
    private String title;
    private boolean isPublished;
}
