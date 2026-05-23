package ro.iss.agorainretea.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ro.iss.agorainretea.domain.User;
import ro.iss.agorainretea.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LoginUtilityService {
    @Autowired
    private UserRepository userRepository;

    public User findMatch(String username) {
        Optional<User> users = userRepository.findUserByEmail(username);
        if (users.isEmpty()) {
            throw new UsernameNotFoundException("Email " + username + " doesn't exists");
        }

        User user = users.get();
        System.out.println(user.getName() + " " + user.getEmail());

        return user;
    }

    public Optional<User> getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getPrincipal() == null)
            return null;

        if(authentication.getPrincipal().equals("anonymousUser"))
            return Optional.empty();
        return userRepository.findUserByEmail(((org.springframework.security.core.userdetails.User)authentication.getPrincipal()).getUsername());
    }
}
