package Convene.Backend.SoftwareProject.Issue;

import Convene.Backend.AppUser.AppUser;
import Convene.Backend.Exception.CustomExceptions.IssueExceptions;
import Convene.Backend.SoftwareProject.Issue.CustomIssueStatus.CustomIssueStatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class IssueService implements  IssueServiceImpl{

    private final IssueRepository issueRepository;

    private final CustomIssueStatusService customIssueStatusService;

    @Autowired
    public IssueService(IssueRepository issueRepository, CustomIssueStatusService customIssueStatusService) {
        this.issueRepository = issueRepository;
        this.customIssueStatusService = customIssueStatusService;
    }

    @Override
    public Issue findIssueById(long id) {
        return issueRepository.findIssueById(id).orElseThrow(IssueExceptions.IssueNotFoundException::new);
    }

    @Override
    public Issue createIssue(Issue issue) {
        return issueRepository.save(issue);
    }

    @Override
    public List<Issue> getIssuesBySprint(long id) {
        return issueRepository.findIssuesBySprintId(id);
    }

    @Override
    public Issue assignIssue(long issueId,AppUser appUser) {
        Issue issue = issueRepository.findIssueById(issueId).orElseThrow(IssueExceptions.IssueNotFoundException::new);
        issue.setAssignee(appUser);
        issueRepository.save(issue);
        return issue;
    }

    @Override
    public Issue changeIssueStatus(IssueDto.ChangeIssueStatusRequest request) {
        Issue issue = findIssueById(request.getIssueId());
        issue.setIssueStatus(request.getIssueStatus());
        return issue;
    }

    @Override
    public List<Issue> findIssuesByAssignee(long assigneeId) {
        return issueRepository.findIssuesByAssignee_Id(assigneeId).orElse(new ArrayList<Issue>());
    }

    @Override
    public Issue deleteIssue(long issueId) {
        Issue issue = findIssueById(issueId);
        issueRepository.delete(issue);
        return issue;
    }
}
