package Convene.Backend.SoftwareProject.Sprint;

import Convene.Backend.Exception.CustomExceptions.SoftwareProjectExceptions;
import Convene.Backend.Exception.CustomExceptions.SprintExceptions;
import Convene.Backend.SoftwareProject.ProjectType;
import Convene.Backend.SoftwareProject.SoftwareProject;
import Convene.Backend.SoftwareProject.SoftwareProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SprintService implements SprintServiceImpl {

    private final SprintRepository sprintRepository;

    private final SoftwareProjectService softwareProjectService;

    @Autowired
    public SprintService(SprintRepository sprintRepository, SoftwareProjectService softwareProjectService) {
        this.sprintRepository = sprintRepository;
        this.softwareProjectService = softwareProjectService;
    }

    @Override
    public Sprint findSprintById(long id) {
        return sprintRepository.findSprintById(id).orElseThrow(SprintExceptions.SprintNotFoundException::new);
    }

    @Override
    public List<Sprint> findSprintsBySoftwareProjectId(long softwareProjectId) {
        return sprintRepository.findSprintBySoftwareProjectId(softwareProjectId).orElse(new ArrayList<>());
    }

    @Override
    public Sprint createSprint(SprintDto.CreateSprintRequest request) throws Exception {
        SoftwareProject project = softwareProjectService.findSoftwareProjectById(request.getSoftwareProjectId());
        if(project.getProjectType().equals(ProjectType.KANBAN) && project.getSprints().size() > 0) {
            throw new SoftwareProjectExceptions.KanbanProjectSprintException();
        }
        Sprint newSprint = new Sprint(request.getName(), request.getStartDate(), request.getEndDate(), project);
        return sprintRepository.save(newSprint);
    }

    @Override
    public Sprint deleteSprint(long sprintId) throws Exception {
        Sprint sprint = findSprintById(sprintId);
        sprintRepository.delete(sprint);
        return  sprint;
    }

    @Override
    public Sprint changeSprintStatus() throws Exception {
        return null;
    }
}
