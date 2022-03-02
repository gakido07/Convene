package Convene.Backend.SoftwareProject.Issue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sprint/{sprintId}")
public class IssueController {

    private final IssueServiceImpl issueServiceImpl;

    @Autowired
    public IssueController(IssueServiceImpl issueServiceImpl) {
        this.issueServiceImpl = issueServiceImpl;
    }

    @GetMapping(path = "/issues")
    public List<Issue> getIssuesBySprint(@PathVariable(name = "sprintId") long id) {
        return issueServiceImpl.getIssuesBySprint(id);
    }

    @PostMapping(path = "/issue")
    public Issue createIssue(Issue issue) {
        return issueServiceImpl.saveIssue(issue);
    }


    @PutMapping(path = "/issue/{issueId}")
    public Issue changeIssueStatus(IssueDto.ChangeIssueStatusRequest request) {
        return issueServiceImpl.changeIssueStatus(request);
    }

    @GetMapping(path = "/issue/{issueId}")
    public List<Issue> findIssuesByAssignee(@PathVariable(name = "issueId") long assigneeId) {
        return issueServiceImpl.findIssuesByAssignee(assigneeId);
    }

    @DeleteMapping(path = "/issue/{issueId}")
    public ResponseEntity<Long> deleteIssue(@PathVariable(name = "issueId") long issueId) {
//        return issueServiceImpl.deleteIssueByid(issueId);
        return null;
    }
}
