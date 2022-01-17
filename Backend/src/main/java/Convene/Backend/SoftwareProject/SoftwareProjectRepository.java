package Convene.Backend.SoftwareProject;

import Convene.Backend.User.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface SoftwareProjectRepository extends JpaRepository<SoftwareProject, Long> {
    @Override
    Optional<SoftwareProject> findById(Long id);

}
