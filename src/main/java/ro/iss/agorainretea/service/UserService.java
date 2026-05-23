package ro.iss.agorainretea.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.iss.agorainretea.domain.User;
import ro.iss.agorainretea.domain.validators.UserValidator;
import ro.iss.agorainretea.exceptions.ServiceException;
import ro.iss.agorainretea.repository.UserRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)  //lombok shortcut for creating our @Autowire constructor
public class UserService {
    private final UserRepository userRepository;
    private final UserValidator userValidator;

    public void signUp(String name, String familyName, String email, String password){
        if(userRepository.existsByEmail(email)) {
            throw new ServiceException("Email is already taken!");
        }

        User toBeSaved = new User();
        toBeSaved.setName(name);
        toBeSaved.setFamilyName(familyName);
        toBeSaved.setEmail(email);
        toBeSaved.setPassword(password);
        userValidator.validate(toBeSaved);

        System.out.println("chiar salvez frate");
        userRepository.saveAndFlush(toBeSaved);
    }

    public long getUserIdByEmail(String email) {
        var userOptional = userRepository.findUserByEmail(email);
        return userOptional.map(User::getId).orElse(-1L);
    }

    public User getUserById(long id) {
        var userOptional = userRepository.findById(id);
        return userOptional.orElse(null);
    }

    public boolean userExistsById(long id) {
        return userRepository.existsById(id);
    }

    public List<User> findUsersByTeamId(int teamId) {
        return userRepository.findAllByTeam_Id(teamId);
    }


}
