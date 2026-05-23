package ro.iss.agorainretea.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ro.iss.agorainretea.domain.Article;
import ro.iss.agorainretea.domain.ArticleDto;
import ro.iss.agorainretea.domain.requests.ArticleSaveRequest;
import ro.iss.agorainretea.domain.requests.CreateArticleRequest;
import ro.iss.agorainretea.domain.requests.ArticleRequest;
import ro.iss.agorainretea.exceptions.GeneralPurposeException;
import ro.iss.agorainretea.service.ArticleService;
import ro.iss.agorainretea.service.LoginUtilityService;

@Controller
@RestController
@RequestMapping("/articles")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private LoginUtilityService loginUtilityService;
    @Autowired
    private ConversionService conversionService;

    @GetMapping
    public Article[] getAllArticlesFiltered(@RequestParam (value="published", required=false) String published,
                                            @RequestParam (value="group", required = false) Integer groupId) {

        if(groupId != null) {
            return articleService.getArticlesByGroup(groupId).toArray(new Article[0]);
        }

        if(published != null) {
            return articleService.getAllPublishedArticles().toArray(new Article[0]);
        }

        return articleService.getAll().toArray(new Article[0]);
    }

    @GetMapping("/create")
    public ModelAndView getCreateArticlePage(Model model) {
        if(loginUtilityService.getLoggedInUser().isPresent()) {
            var loggedUser = loginUtilityService.getLoggedInUser().get();

            ModelAndView modelAndView = new ModelAndView("article_editor");
            Article newArticle = new Article();
            newArticle.setAuthor(loggedUser);
            newArticle.setTitle("NewArticle");

            ArticleDto newArticleContent = new ArticleDto();
            newArticleContent.setAuthorId(loggedUser.getId());
            newArticleContent.setTitle("NewArticle");
            modelAndView.addObject("article", newArticle);
            modelAndView.addObject("article_content", newArticleContent);

            return modelAndView;
        }
        else {
            throw new GeneralPurposeException("You must be logged in!");
        }
    }

    @GetMapping("/{id}")
    public ModelAndView getArticle(@PathVariable int id, Model model, @RequestParam (value="edit", required=false) String edit) {
        var artOpt = articleService.findById(id);
        if(artOpt.isEmpty()) {
            throw new GeneralPurposeException("Article does not exist!");
        }

        if(edit != null) {
            var art = artOpt.get();

            if(loginUtilityService.getLoggedInUser().isPresent()) {
                var loggedUser = loginUtilityService.getLoggedInUser().get();
                if(loggedUser.getId() != art.getAuthor().getId()) {
                    throw new GeneralPurposeException("You are not allowed to edit this article!");
                }
            }

            ModelAndView modelAndView = new ModelAndView("article_editor");
            modelAndView.addObject("article", art);
            modelAndView.addObject("article_content", articleService.getArticleContent(id));

            return modelAndView;
        }

        var art = artOpt.get();
        ModelAndView modelAndView = new ModelAndView("article_view");
        modelAndView.addObject("article", art);
        modelAndView.addObject("article_content", articleService.getArticleContent(id));


        return modelAndView;
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<?> getArticleContent(@PathVariable int id) {
//        var article = articleService.getArticleContent(id);
//
//        if(article == null)
//            return new ResponseEntity<>("Id not found!", HttpStatus.NOT_FOUND);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.TEXT_MARKDOWN);
//        headers.setContentDisposition(ContentDisposition.builder("attachment").filename(article.getTitle() + "~" + article.getId() + "~" + article.getAuthorId() + ".txt").build());
//        return ResponseEntity.ok()
//                .headers(headers)
//                .body(article.getContent());
//    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateArticle(@PathVariable int id, @RequestBody ArticleRequest request) {
        articleService.updateArticle(id, request.getTitle(), request.getText(), request.isPublished());

        return ResponseEntity.ok("Article modified succesfully!");
    }

    @PostMapping
    public ResponseEntity<?> saveArticle(@RequestBody ArticleSaveRequest request) {
        var created = articleService.saveArticle(request.getAuthorId(), request.getTitle(), request.getText());
        var article = articleService.getArticleContent(created.getId());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        //headers.setContentDisposition(ContentDisposition.builder("attachment").filename(article.getTitle() + "~" + article.getId() + "~" + article.getAuthorId() + ".txt").build());
        return ResponseEntity.ok()
                .headers(headers)
                .body(article);
    }
}
