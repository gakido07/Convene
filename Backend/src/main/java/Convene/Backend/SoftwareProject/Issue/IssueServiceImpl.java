package Convene.Backend.SoftwareProject.Issue;

import Convene.Backend.AppUser.AppUser;
import Convene.Backend.Exception.CustomExceptions.IssueExceptions;
import Convene.Backend.SoftwareProject.Issue.CustomIssueStatus.CustomIssueStatusServiceImpl;
import Convene.Backend.SoftwareProject.Sprint.SprintService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class IssueServiceImpl implements IssueService {

    private final IssueRepository issueRepository;

    private final CustomIssueStatusServiceImpl customIssueStatusServiceImpl;

    private final SprintService sprintService;

    @Autowired
    public IssueServiceImpl(IssueRepository issueRepository, CustomIssueStatusServiceImpl customIssueStatusServiceImpl, SprintService sprintService) {
        this.issueRepository = issueRepository;
        this.customIssueStatusServiceImpl = customIssueStatusServiceImpl;
        this.sprintService = sprintService;
    }

    @Override
    public Issue findIssueById(long id) {
        return issueRepository.findIssueById(id).orElseThrow(IssueExceptions.IssueNotFoundException::new);
    }

    @Override
    public void deleteIssueByid(long id) {
        issueRepository.deleteIssueById(id);
    }

    @Override
    public Issue saveIssue(Issue issue) {
        return issueRepository.save(issue);
    }

    @Override
    public List<Issue> getIssueBySoftwareProject(Long softwareProjectId) {
        return issueRepository.findIssuesBySoftwareProject_Id(softwareProjectId).orElse(new ArrayList<>());
    }

    @Override
    public List<Issue> getIssuesBySprint(long id) {
        return issueRepository.findIssuesBySprintId(id);
    }

    @Override
    public Issue changeIssueName(long id, String newIssueName) {
        Issue issue = findIssueById(id);
        issue.setName(newIssueName);
        saveIssue(issue);
        return issue;
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
        return issueRepository.findIssuesByAssignee_Id(assigneeId).orElse(new ArrayList<>());
    }
}
