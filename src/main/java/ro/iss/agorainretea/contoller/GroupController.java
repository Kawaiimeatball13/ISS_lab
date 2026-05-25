package ro.iss.agorainretea.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ro.iss.agorainretea.domain.requests.GroupSaveRequest;
import ro.iss.agorainretea.exceptions.GeneralPurposeException;
import ro.iss.agorainretea.service.GroupService;
import ro.iss.agorainretea.service.LoginUtilityService;

@Controller
@RestController
@RequestMapping("/groups")
public class GroupController {
    @Autowired
    private GroupService groupService;
    @Autowired
    private LoginUtilityService loginUtilityService;

    @GetMapping("/create")
    public ModelAndView getCreatePage() {
        var userOpt = loginUtilityService.getLoggedInUser();
        if(userOpt.isEmpty()) {
            throw new GeneralPurposeException("You must be logged in!");
        }

        var user = userOpt.get();
        ModelAndView modelAndView = new ModelAndView("create_group");
        modelAndView.addObject("user", user);

        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView getGroup(@PathVariable int id) {
        var groupOpt = groupService.findGroupById(id);

        if(groupOpt.isEmpty()) {
            throw new GeneralPurposeException("Group not found!");
        }

        var group = groupOpt.get();
        ModelAndView modelAndView = new ModelAndView("group_page");
        modelAndView.addObject("group", group);

        return modelAndView;
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<?> saveGroup(@RequestParam String name, @RequestParam String description, @RequestParam String location,
                                       @RequestParam long adminId) {
        var created = groupService.saveGroup(name, description, location, adminId);

        return ResponseEntity.ok(created);
    }
}
