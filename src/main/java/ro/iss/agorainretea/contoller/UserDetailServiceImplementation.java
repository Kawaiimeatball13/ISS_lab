//package ro.iss.agorainretea.contoller;
//
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import ro.iss.agorainretea.domain.User;
//import ro.iss.agorainretea.repository.UserRepository;
//
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor(onConstructor_ = @Autowired)
//public class UserDetailServiceImplementation implements UserDetailsService {
//    private final UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<User> userOptional = userRepository.findUserByEmail(username);
//
//        if (userOptional.isPresent()) {
//            User user = userOptional.get();
//
//            return org.springframework.security.core.userdetails.User.builder()
//                    .username(user.getEmail())
//                    .password(user.getPassword())
//                    .accountExpired(false)
//                    .accountLocked(false)
//                    .credentialsExpired(false)
//                    .build();
//        } else {
//            throw new UsernameNotFoundException(username + " was not found.");
//        }
//    }
//
//
//}
