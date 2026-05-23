package ro.iss.agorainretea.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.iss.agorainretea.domain.Team;

@Repository
public interface GroupRepository extends JpaRepository<Team, Integer> {
}
