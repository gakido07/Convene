package Convene.Backend.SoftwareProject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("{userId}/software-project")
public class SoftwareProjectController {

    private SoftwareProjectService softwareProjectService;

    @Autowired
    public SoftwareProjectController(SoftwareProjectService softwareProjectService) {
        this.softwareProjectService = softwareProjectService;
    }

    @PostMapping(path = "/")
    public ResponseEntity<SoftwareProjectDto> createSoftwareProject(@RequestBody SoftwareProjectDto.CreateSoftwareProjectRequest request, @PathVariable(name = "userId") String userId) throws Exception {
        SoftwareProjectDto projectDto = softwareProjectService.createProject(request, Long.valueOf(userId));
        return new ResponseEntity<>(
                projectDto,
                HttpStatus.ACCEPTED
        );
    }

    @GetMapping(path = "/{projectId}")
    public SoftwareProject getSoftwareProjectDetails(@PathVariable(name = "projectId") String projectId, @RequestParam String userId) {
        return softwareProjectService.findSoftwareProjectById(Long.valueOf(projectId));
    }
}
