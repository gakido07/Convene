package Convene.Backend.SoftwareProject;

import Convene.Backend.AppUser.AppUserService;
import Convene.Backend.Exception.CustomExceptions.AuthExceptions;
import Convene.Backend.Exception.CustomExceptions.SoftwareProjectExceptions;
import Convene.Backend.AppUser.AppUser;
import Convene.Backend.SoftwareProject.SoftwareProjectRole.SoftwareProjectRole;
import Convene.Backend.SoftwareProject.SoftwareProjectRole.SoftwareProjectRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;

@Service
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
    public SoftwareProject findSoftwareProjectById(Long id) throws SoftwareProjectExceptions.SoftwareProjectNotFound {
        return softwareProjectRepository.findById(id).orElseThrow(
                SoftwareProjectExceptions.SoftwareProjectNotFound::new);
    }

    @Override
    public SoftwareProjectDto createProject(SoftwareProjectDto.CreateSoftwareProjectRequest request, Long id) throws Exception {
        AppUser appUser = appUserService.findAppUserById(id);
        SoftwareProject newProject = new SoftwareProject(request.getName(),  request.getType(),request.getDescription(), appUser);
        softwareProjectRepository.save(newProject);
        SoftwareProject project = softwareProjectRepository.findSoftwareProjectByTeamMembersContains(appUser).orElseThrow(AuthExceptions.UserNotFoundException::new);
        SoftwareProjectRole role = new SoftwareProjectRole( "ADMIN", project)
                .withAppUsers(Arrays.asList(appUser).stream().collect(Collectors.toSet()));

        role.getTeamMembers();
        System.out.println(role.getRole());
        newProject.addRole(role);
        System.out.println(newProject.getRoles());
        return new SoftwareProjectDto(newProject);
    }

    /*TODO
    *   Write logic to create Sprints
    *   Write logic to assign issues
    */

}
