package Convene.Backend.Project.SoftwareProject.SoftwareProjectRole;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoftwareProjectRoleRepository extends JpaRepository<SoftwareProjectRole, Long> {
    @Override
    SoftwareProjectRole getById(Long id);
}
