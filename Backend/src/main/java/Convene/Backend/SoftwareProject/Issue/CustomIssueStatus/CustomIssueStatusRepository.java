package Convene.Backend.SoftwareProject.Issue.CustomIssueStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomIssueStatusRepository extends JpaRepository<CustomIssueStatus, Long> {
    List<CustomIssueStatus> findCustomIssueStatusesBySoftwareProject_Id(long id);
}
