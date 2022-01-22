package Convene.Backend.SoftwareProject;

import Convene.Backend.AppUser.AppUserService;
import Convene.Backend.Exception.CustomExceptions.AuthExceptions;
import Convene.Backend.Exception.CustomExceptions.SoftwareProjectExceptions;
import Convene.Backend.AppUser.AppUser;
import Convene.Backend.SoftwareProject.SoftwareProjectRole.SoftwareProjectRole;
import Convene.Backend.SoftwareProject.SoftwareProjectRole.SoftwareProjectRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SoftwareProjectService implements SoftwareProjectServiceImpl {

    private SoftwareProjectRepository softwareProjectRepository;

    private AppUserService appUserService;

    private SoftwareProjectRoleService softwareProjectRoleService;

    @Autowired
    public SoftwareProjectService(SoftwareProjectRepository softwareProjectRepository, AppUserService appUserService, SoftwareProjectRoleService softwareProjectRoleService) {
        this.softwareProjectRepository = softwareProjectRepository;
        this.appUserService = appUserService;
        this.softwareProjectRoleService = softwareProjectRoleService;
    }

    @Override
    public SoftwareProject findSoftwareProjectById(Long id) throws Exception {
        return softwareProjectRepository.findById(id).orElseThrow(SoftwareProjectExceptions.SoftwareProjectNotFound::new);
    }

    @Override
    public SoftwareProjectDto getSoftwareProjectDto(Long id) throws SoftwareProjectExceptions.SoftwareProjectNotFound {
        return new SoftwareProjectDto(
                softwareProjectRepository.findById(id).orElseThrow(
                SoftwareProjectExceptions.SoftwareProjectNotFound::new)
        );
    }

    @Override
    public List<SoftwareProject> findSoftwareProjectsByAppUser(AppUser appUser) throws Exception {
        return softwareProjectRepository.findSoftwareProjectsByTeamMembersContains(appUser).orElse(new ArrayList<>());
    }

    @Override
    public SoftwareProjectDto createProject(SoftwareProjectDto.CreateSoftwareProjectRequest request) throws Exception {
        AppUser appUser = appUserService.findAppUserById(request.getCreatorId());
        SoftwareProject newProject = new SoftwareProject(request, appUser);
        log.info("New Project: " + newProject.toString(), newProject.toString());
        softwareProjectRepository.save(newProject);
        return new SoftwareProjectDto(newProject);
    }

    /*TODO
    *   Write logic to create Sprints
    *   Write logic to assign issues
    */

}
