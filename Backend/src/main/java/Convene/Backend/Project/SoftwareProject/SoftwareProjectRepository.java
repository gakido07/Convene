package Convene.Backend.Project.SoftwareProject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SoftwareProjectRepository extends JpaRepository<SoftwareProject, Long> {
    @Override
    Optional<SoftwareProject> findById(Long id);
}
