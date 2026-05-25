package ro.iss.agorainretea.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto {
    private Integer id;
    private String title;
    private Long authorId;
    private Boolean isPublished;
    private String content;
}
