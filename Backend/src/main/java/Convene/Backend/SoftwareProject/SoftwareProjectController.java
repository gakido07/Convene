package Convene.Backend.SoftwareProject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/software-project")
public class SoftwareProjectController {

    @Autowired
    private SoftwareProjectService service;

    @PostMapping(path = "/")
    public ResponseEntity<SoftwareProjectDto> createSoftwareProject(@RequestBody SoftwareProjectDto.CreateSoftwareProjectRequest request, @PathVariable(name = "userId") String userId) {
        SoftwareProjectDto projectDto = service.createProject(request, Long.valueOf(userId));

        return new ResponseEntity<>(
                projectDto,
                HttpStatus.ACCEPTED
        );
    }


    @GetMapping(path = "/{projectId}")
    public SoftwareProject getSoftwareProjectDetails(@PathVariable(name = "projectId") String projectId, @RequestParam String userId) {
        return service.getSoftwareProject(Long.valueOf(projectId));
    }



}
