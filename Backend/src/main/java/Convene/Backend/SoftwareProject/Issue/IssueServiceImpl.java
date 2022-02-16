package Convene.Backend.SoftwareProject.Issue;

import Convene.Backend.AppUser.AppUser;

import java.util.List;

public interface IssueServiceImpl {
    Issue findIssueById(long id);
    Issue createIssue(Issue issue);
    List<Issue> getIssuesBySprint(long id);
    Issue assignIssue(long sprintId,AppUser appUser);
    List<Issue> findIssuesByAssignee(long assigneeId);
    Issue changeIssueStatus(IssueDto.ChangeIssueStatusRequest request);
    Issue deleteIssue(long issueId);
}
