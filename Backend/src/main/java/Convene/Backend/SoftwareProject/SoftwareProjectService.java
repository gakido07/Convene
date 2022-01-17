package Convene.Backend.SoftwareProject;

import Convene.Backend.Exception.CustomExceptions.AuthExceptions;
import Convene.Backend.Exception.CustomExceptions.SoftwareProjectExceptions;
import Convene.Backend.User.AppUser;
import Convene.Backend.User.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SoftwareProjectService {

    @Autowired
    private SoftwareProjectRepository repository;

    @Autowired
    private AppUserRepository userRepository;

    public SoftwareProject loadById(Long id) throws SoftwareProjectExceptions.SoftwareProjectNotFound {
        return repository.findById(id).orElseThrow(
                () -> new SoftwareProjectExceptions.SoftwareProjectNotFound());

    }

    public SoftwareProjectDto createProject(SoftwareProjectDto.CreateSoftwareProjectRequest request, Long id) {
        AppUser appUser = userRepository.findById(id).orElseThrow(() -> new AuthExceptions.UserNotFoundException());
        SoftwareProject newProject = new SoftwareProject(request.getName(),  request.getType(),request.getDescription(), appUser);
        repository.save(newProject);
        return new SoftwareProjectDto(newProject);
    }

    public SoftwareProject getSoftwareProject(Long id) {
        return repository.findById(id).orElseThrow(() -> new SoftwareProjectExceptions.SoftwareProjectNotFound());
    }

    /*TODO
    *   Write logic to create Sprints
    *   Write logic to assign issues
    * */

}
