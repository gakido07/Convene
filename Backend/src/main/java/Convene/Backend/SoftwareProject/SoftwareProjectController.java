package Convene.Backend.SoftwareProject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/software-project")
public class SoftwareProjectController {

    private SoftwareProjectService softwareProjectService;

    @Autowired
    public SoftwareProjectController(SoftwareProjectService softwareProjectService) {
        this.softwareProjectService = softwareProjectService;
    }

    @PostMapping(path = "/")
    public ResponseEntity<SoftwareProjectDto> createSoftwareProject(@RequestBody SoftwareProjectDto.CreateSoftwareProjectRequest request) throws Exception {
        SoftwareProjectDto projectDto = softwareProjectService.createProject(request);
        return new ResponseEntity<>(
                projectDto,
                HttpStatus.ACCEPTED
        );
    }

    @GetMapping(path = "/{projectId}")
    @PreAuthorize("hasPermission(#projectId, 'MEMBER')")
    public SoftwareProjectDto getSoftwareProjectDetails(@PathVariable(name = "projectId") String projectId) {
        return softwareProjectService.getSoftwareProjectDto(Long.valueOf(projectId));
    }
}
