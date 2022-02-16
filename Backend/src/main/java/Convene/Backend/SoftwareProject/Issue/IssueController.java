package Convene.Backend.SoftwareProject.Issue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sprint/{sprintId}")
public class IssueController {

    private final IssueService issueService;

    @Autowired
    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @GetMapping(path = "/issues")
    public List<Issue> getIssuesBySprint(@PathVariable(name = "sprintId") long id) {
        return issueService.getIssuesBySprint(id);
    }

    @PostMapping(path = "/issue")
    public Issue createIssue(Issue issue) {
        return issueService.createIssue(issue);
    }


    @PutMapping(path = "/issue/{issueId}")
    public Issue changeIssueStatus(IssueDto.ChangeIssueStatusRequest request) {
        return issueService.changeIssueStatus(request);
    }

    @GetMapping(path = "/issue/{issueId}")
    public List<Issue> findIssuesByAssignee(@PathVariable(name = "issueId") long assigneeId) {
        return issueService.findIssuesByAssignee(assigneeId);
    }

    @DeleteMapping(path = "/issue/{issueId}")
    public Issue deleteIssue(@PathVariable(name = "issueId") long issueId) {
        return issueService.deleteIssue(issueId);
    }
}
