package Convene.Backend.SoftwareProject.Sprint;

import Convene.Backend.SoftwareProject.Issue.Issue;

import java.util.List;
import java.util.Optional;

public interface SprintServiceImpl {
    public Sprint findSprintById(long id);
    public void deleteSprintById(long id);
    public void deleteSprint(Sprint sprint) throws Exception;
    public Sprint saveSprint(Sprint sprint);
    public List<Sprint> findSprintsBySoftwareProjectId(long softwareProjectId);
    public Sprint createSprint(SprintDto.CreateSprintRequest request) throws Exception;
    public Sprint changeSprintStatus(long id) throws Exception;
    public Issue addIssueToSprint(long issueId, long sprintId);
}
