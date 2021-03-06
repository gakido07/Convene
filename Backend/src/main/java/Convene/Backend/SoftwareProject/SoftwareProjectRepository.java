package Convene.Backend.SoftwareProject;

import Convene.Backend.AppUser.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SoftwareProjectRepository extends JpaRepository<SoftwareProject, Long> {
    Optional<SoftwareProject> findById(long id);
    Optional<List<SoftwareProject>> findSoftwareProjectsByTeamMembersContains(AppUser appUser);
}
