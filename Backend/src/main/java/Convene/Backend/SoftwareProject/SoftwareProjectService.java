package Convene.Backend.SoftwareProject;

import Convene.Backend.AppUser.AppUserService;
import Convene.Backend.Exception.CustomExceptions.AuthExceptions;
import Convene.Backend.Exception.CustomExceptions.SoftwareProjectExceptions;
import Convene.Backend.AppUser.AppUser;
import Convene.Backend.SoftwareProject.Issue.CustomIssueStatus.CustomIssueStatus;
import Convene.Backend.SoftwareProject.Issue.CustomIssueStatus.CustomIssueStatusService;
import Convene.Backend.SoftwareProject.Issue.Issue;
import Convene.Backend.SoftwareProject.SoftwareProjectRole.SoftwareProjectRole;
import Convene.Backend.SoftwareProject.SoftwareProjectRole.SoftwareProjectRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SoftwareProjectService implements SoftwareProjectServiceImpl {

    private final SoftwareProjectRepository softwareProjectRepository;

    private final AppUserService appUserService;

    private final SoftwareProjectRoleService softwareProjectRoleService;

    @Autowired
    public SoftwareProjectService(SoftwareProjectRepository softwareProjectRepository, AppUserService appUserService,
                                  SoftwareProjectRoleService softwareProjectRoleService) {
        this.softwareProjectRepository = softwareProjectRepository;
        this.appUserService = appUserService;
        this.softwareProjectRoleService = softwareProjectRoleService;
    }

    @Override
    public SoftwareProject findSoftwareProjectById(long id) throws Exception {
        return softwareProjectRepository.findById(id).orElseThrow(SoftwareProjectExceptions.SoftwareProjectNotFound::new);
    }

    @Override
    public SoftwareProjectDto getSoftwareProjectDto(long id) throws SoftwareProjectExceptions.SoftwareProjectNotFound {
        return new SoftwareProjectDto(
                softwareProjectRepository.findById(id).orElseThrow(
                SoftwareProjectExceptions.SoftwareProjectNotFound::new)
        );
    }

    @Override
    public List<SoftwareProject> findSoftwareProjectsByAppUser(AppUser appUser) {
        return softwareProjectRepository.findSoftwareProjectsByTeamMembersContains(appUser).orElse(new ArrayList<>());
    }

    @Override
    public SoftwareProjectDto createProject(SoftwareProjectDto.CreateSoftwareProjectRequest request) throws Exception {
        AppUser appUser = appUserService.findAppUserById(request.getCreatorId());
        SoftwareProject newProject = new SoftwareProject(request, appUser);
        log.info("New Project: " + newProject, newProject);
        softwareProjectRepository.save(newProject);
        return new SoftwareProjectDto(newProject);
    }

    @Override
    public Object[] getProjectIssueStatuses(long id) throws Exception {
        SoftwareProject softwareProject = findSoftwareProjectById(id);
        Object[] issueStatuses = Arrays.stream(Issue.IssueStatus.values()).map(issueStatus -> issueStatus.name()).toArray();
        List<CustomIssueStatus> customIssueStatus = new ArrayList<>(softwareProject.getCustomIssueStatuses());
        if(customIssueStatus.size() < 1) {
            return issueStatuses;
        }
        else {
            List<Object> issueStatusList = Arrays.stream(issueStatuses).collect(Collectors.toList());
            customIssueStatus.forEach(status -> {
                issueStatusList.add(status.getIndex(), status.getName());
            });

            return issueStatusList.toArray();
        }
    }

    @Override
    public void addTeammate(String email) throws Exception {

    }
}
