package ro.iss.agorainretea.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.iss.agorainretea.domain.Team;
import ro.iss.agorainretea.exceptions.ServiceException;
import ro.iss.agorainretea.repository.GroupRepository;
import ro.iss.agorainretea.repository.UserRepository;

import java.util.Optional;

@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private UserRepository userRepository;

    public Optional<Team> findGroupById(int id) {
        return groupRepository.findById(id);
    }

    public Team saveGroup(String name, String description, String location, long adminId) {
        var userOpt = userRepository.findById(adminId);
        if(userOpt.isEmpty()){
            throw new ServiceException("User can't be found!");
        }

        var user = userOpt.get();
        Team group = new Team(null, name, description, location, user);
        user.setTeam(group);

        userRepository.save(user);
        groupRepository.save(group);
        return group;
    }
}
