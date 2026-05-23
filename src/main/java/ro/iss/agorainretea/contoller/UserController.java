package ro.iss.agorainretea.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ro.iss.agorainretea.domain.Article;
import ro.iss.agorainretea.domain.User;
import ro.iss.agorainretea.exceptions.GeneralPurposeException;
import ro.iss.agorainretea.service.ArticleService;
import ro.iss.agorainretea.service.LoginUtilityService;
import ro.iss.agorainretea.service.UserService;

@RestController
@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private LoginUtilityService loginUtilityService;

    @GetMapping
    public User[] getUsers(@RequestParam(name="group", required = false) int groupId) {
        return userService.findUsersByTeamId(groupId).toArray(new User[0]);
    }

    @GetMapping("/{id}")
    public ModelAndView getUser(@PathVariable int id, Model model) {
        var user = userService.getUserById(id);
        if(user == null ) {
            throw new GeneralPurposeException("User does not exist!");
        }

        if(user.getId() != loginUtilityService.getLoggedInUser().get().getId()) {
            throw new GeneralPurposeException("You are not allowed to view this page!");
        }

        ModelAndView modelAndView = new ModelAndView("user_page");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @GetMapping("/{id}/articles")
    public Article[] getUserArticles(@PathVariable int id) {
        if(!userService.userExistsById(id)) {
            throw new GeneralPurposeException("User does not exist!");
        }

        return articleService.getArticlesByUser(id).toArray(new Article[0]);
    }
}
