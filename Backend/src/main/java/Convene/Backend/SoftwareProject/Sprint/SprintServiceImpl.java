package Convene.Backend.SoftwareProject.Sprint;

import java.util.List;
import java.util.Optional;

public interface SprintServiceImpl {
    public Sprint findSprintById(Long id);
    public List<Sprint> findSprintsBySoftwareProjectId(Long softwareProjectId);
    public Sprint createSprint(SprintDto.CreateSprintRequest request) throws Exception;
}
