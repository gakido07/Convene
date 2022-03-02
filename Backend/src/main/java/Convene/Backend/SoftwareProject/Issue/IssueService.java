package Convene.Backend.SoftwareProject.Issue;

import Convene.Backend.AppUser.AppUser;

import java.util.List;

public interface IssueService {
    Issue findIssueById(long id);
    void deleteIssueByid(long id);
    Issue saveIssue(Issue issue);
    List<Issue> getIssueBySoftwareProject(Long softwareProjectId);
    List<Issue> getIssuesBySprint(long id);
    Issue changeIssueName(long id, String newIssueName);
    Issue assignIssue(long sprintId,AppUser appUser);
    List<Issue> findIssuesByAssignee(long assigneeId);
    Issue changeIssueStatus(IssueDto.ChangeIssueStatusRequest request);
}
