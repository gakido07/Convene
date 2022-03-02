package Convene.Backend.SoftwareProject.Issue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {
    Optional<Issue> findIssueById(long id);
    List<Issue> findIssuesBySprintId(long id);
    Optional<List<Issue>> findIssuesByAssignee_Id(long assigneeId);
    Optional<List<Issue>> findIssuesBySoftwareProject_Id(long softwareProjectId);
    void deleteIssueById(long id);
}
