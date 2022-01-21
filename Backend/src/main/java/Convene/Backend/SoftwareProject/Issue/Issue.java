package Convene.Backend.SoftwareProject.Issue;


import Convene.Backend.SoftwareProject.Sprint.Sprint;
import Convene.Backend.AppUser.AppUser;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "issue")
public class Issue {
    @Id
    @SequenceGenerator(
            name = "issue_sequence",
            sequenceName = "issue_sequence",
            allocationSize = 1
    )
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id", nullable = false, insertable = false, updatable = false)
    private AppUser assignee;
    private String name;
    private String description;
    private IssueStatus issueStatus;
    private IssueType issueType;
    private Priority priority;

    @ManyToOne
    @JoinColumn(name = "id", nullable = false, insertable = false, updatable = false)
    private Sprint sprint;

    private enum IssueStatus {
        IN_PROGRESS, OPEN, CLOSED, REOPENED, RESOLVED
    }

    private enum Priority {
        CRITICAL, MAJOR, MINOR, BLOCKER, TRIVIAL
    }

    public enum IssueType {
        TASK, BUG, IMPROVEMENT, NEW_FEATURE, STORY
    }
}
