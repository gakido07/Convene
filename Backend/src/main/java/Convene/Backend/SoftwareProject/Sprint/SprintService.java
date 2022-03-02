package Convene.Backend.SoftwareProject.Sprint;

import Convene.Backend.Exception.CustomExceptions.SoftwareProjectExceptions;
import Convene.Backend.Exception.CustomExceptions.SprintExceptions;
import Convene.Backend.SoftwareProject.Issue.Issue;
import Convene.Backend.SoftwareProject.Issue.IssueServiceImpl;
import Convene.Backend.SoftwareProject.ProjectType;
import Convene.Backend.SoftwareProject.SoftwareProject;
import Convene.Backend.SoftwareProject.SoftwareProjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SprintService implements SprintServiceImpl {

    private final SprintRepository sprintRepository;

    private final SoftwareProjectServiceImpl softwareProjectServiceImpl;

    private final IssueServiceImpl issueServiceImpl;

    @Autowired
    public SprintService(SprintRepository sprintRepository, SoftwareProjectServiceImpl softwareProjectServiceImpl, IssueServiceImpl issueServiceImpl) {
        this.sprintRepository = sprintRepository;
        this.softwareProjectServiceImpl = softwareProjectServiceImpl;
        this.issueServiceImpl = issueServiceImpl;
    }

    @Override
    public Sprint findSprintById(long id) {
        return sprintRepository.findSprintById(id).orElseThrow(SprintExceptions.SprintNotFoundException::new);
    }

    @Override
    public void deleteSprint(Sprint sprint) {
        sprintRepository.delete(sprint);
    }

    @Override
    public Sprint saveSprint(Sprint sprint) {
        return sprintRepository.save(sprint);
    }

    @Override
    public void deleteSprintById(long sprintId) {
        sprintRepository.deleteSprintById(sprintId);
    }

    @Override
    public List<Sprint> findSprintsBySoftwareProjectId(long softwareProjectId) {
        return sprintRepository.findSprintBySoftwareProjectId(softwareProjectId).orElse(new ArrayList<>());
    }

    @Override
    public Sprint createSprint(SprintDto.CreateSprintRequest request) throws Exception {
        SoftwareProject project = softwareProjectServiceImpl.findSoftwareProjectById(request.getSoftwareProjectId());
        if(project.getProjectType().equals(ProjectType.KANBAN) && project.getSprints().size() > 0) {
            throw new SoftwareProjectExceptions.KanbanProjectSprintException();
        }
        Sprint newSprint = new Sprint(request.getName(), request.getStartDate(), request.getEndDate(), project);
        return saveSprint(newSprint);
    }

    @Override
    public Sprint changeSprintStatus(long id) throws Exception {
//        findSprintById(id).setStatus();
        return null;
    }

    @Override
    public Issue addIssueToSprint(long issueId, long sprintId) {
        Sprint sprint = findSprintById(sprintId);
        Issue issue = issueServiceImpl.findIssueById(issueId);
        sprint.addIssue(issue);
        saveSprint(sprint);
        return issue;
    }
}
