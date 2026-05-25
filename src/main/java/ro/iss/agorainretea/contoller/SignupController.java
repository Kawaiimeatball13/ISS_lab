package ro.iss.agorainretea.contoller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.iss.agorainretea.domain.requests.SignupRequest;
import ro.iss.agorainretea.service.UserService;

@CrossOrigin
@RestController
@RequestMapping(path = "/auth")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SignupController {
    private final UserService userService;


    @RequestMapping(value="/signup", method=RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> signUp(@RequestBody SignupRequest signupRequest) {
        //System.out.println("ajunf la endpoint");
        userService.signUp(signupRequest.getName(), signupRequest.getFamilyName(),
                signupRequest.getEmail(), signupRequest.getPassword());

        return ResponseEntity.ok("User successfully added!");
    }

}
