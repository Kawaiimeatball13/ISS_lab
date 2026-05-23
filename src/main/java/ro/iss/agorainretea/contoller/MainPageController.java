package ro.iss.agorainretea.contoller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ro.iss.agorainretea.domain.User;
import ro.iss.agorainretea.exceptions.ServiceException;
import ro.iss.agorainretea.service.LoginUtilityService;
import ro.iss.agorainretea.service.UserService;

@CrossOrigin
@RestController
@Controller
@RequestMapping({"/home", "/"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MainPageController {
    private final UserService userService;
    private final LoginUtilityService loginUtilityService;

    @GetMapping
    public ModelAndView getHomePage(Model model) {
        var user = loginUtilityService.getLoggedInUser().orElse(new User(-1, "in", "Log", "", "", null));

        ModelAndView modelAndView = new ModelAndView("main_page");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

//    @RequestMapping(value="/username", method=RequestMethod.GET)
//    @ResponseBody
//    public String getUsername() {
//        var user = loginUtilityService.getLoggedInUser();
//
//        if(user.isPresent()){
//            var actualUser = user.get();
//            return actualUser.getName() + " " + actualUser.getFamilyName();
//        }
//
//        return "";
//    }
}
