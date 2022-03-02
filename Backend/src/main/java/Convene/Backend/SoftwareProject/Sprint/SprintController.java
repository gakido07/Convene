package Convene.Backend.SoftwareProject.Sprint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/software-project/{softwareProjectId}")
public class SprintController {

    private final SprintService sprintService;

    @Autowired
    public SprintController(SprintService sprintService) {
        this.sprintService = sprintService;
    }

    @PostMapping(path = "/sprint")
    @PreAuthorize("hasPermission(#projectId, 'MEMBER')")
    public Sprint createSprint(SprintDto.CreateSprintRequest request) throws Exception {
        return sprintService.createSprint(request);
    }

    @GetMapping(path = "/sprints")
    @PreAuthorize("hasPermission(#projectId, 'MEMBER')")
    public List<Sprint> findSprintsBySoftwareProjectId (@PathVariable(name = "softwareProjectId") long softwareProjectId) throws Exception {
        return sprintService.findSprintsBySoftwareProjectId(softwareProjectId);
    }

    @DeleteMapping(path = "/sprint/{sprintId}")
    @PreAuthorize("hasPermission(#projectId, 'ADMIN')")
    public ResponseEntity<String> deleteSprint(@PathVariable(name = "sprintId") long sprintId) throws Exception {
        sprintService.deleteSprintById(sprintId);
        return new ResponseEntity<>(
                "Deleted", HttpStatus.ACCEPTED
        );
    }
}
