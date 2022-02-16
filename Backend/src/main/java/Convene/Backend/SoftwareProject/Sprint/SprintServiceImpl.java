package Convene.Backend.SoftwareProject.Sprint;

import java.util.List;
import java.util.Optional;

public interface SprintServiceImpl {
    public Sprint findSprintById(long id);
    public List<Sprint> findSprintsBySoftwareProjectId(long softwareProjectId);
    public Sprint createSprint(SprintDto.CreateSprintRequest request) throws Exception;
    public Sprint deleteSprint(long sprintId) throws Exception;
    public Sprint changeSprintStatus() throws Exception;
}
