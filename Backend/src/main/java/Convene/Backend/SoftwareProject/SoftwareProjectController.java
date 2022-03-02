package Convene.Backend.SoftwareProject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/software-project")
public class SoftwareProjectController {

    private final SoftwareProjectServiceImpl softwareProjectServiceImpl;

    @Autowired
    public SoftwareProjectController(SoftwareProjectServiceImpl softwareProjectServiceImpl) {
        this.softwareProjectServiceImpl = softwareProjectServiceImpl;
    }

    @PostMapping(path = "/")
    public ResponseEntity<SoftwareProjectDto> createSoftwareProject(@RequestBody SoftwareProjectDto.CreateSoftwareProjectRequest request) throws Exception {
        SoftwareProjectDto projectDto = softwareProjectServiceImpl.createProject(request);
        return new ResponseEntity<>(
                projectDto,
                HttpStatus.ACCEPTED
        );
    }

    @GetMapping(path = "/{projectId}")
    @PreAuthorize("hasPermission(#projectId, 'MEMBER')")
    public SoftwareProjectDto getSoftwareProjectDetails(@PathVariable(name = "projectId") String projectId) {
        return softwareProjectServiceImpl.getSoftwareProjectDto(Long.valueOf(projectId));
    }


    @GetMapping(path = "/{projectId}/issue-status")
    public ResponseEntity<Object[]> getSoftwareProjectStatus(@PathVariable(name = "projectId") String projectId) throws Exception {
        return new ResponseEntity<>(softwareProjectServiceImpl.getProjectIssueStatuses(Long.valueOf(projectId)),
                HttpStatus.ACCEPTED);
    }
}
