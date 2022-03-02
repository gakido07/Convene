package Convene.Backend.SoftwareProject.Sprint;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SprintRepository extends JpaRepository<Sprint, Long> {
    Optional<Sprint> findSprintById(long id);
    Optional<List<Sprint>> findSprintBySoftwareProjectId(long softwareProjectId);
    void deleteSprintById(long id);
}
