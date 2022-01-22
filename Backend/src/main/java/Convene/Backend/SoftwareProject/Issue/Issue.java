package Convene.Backend.SoftwareProject.Issue;

import Convene.Backend.SoftwareProject.Sprint.Sprint;
import Convene.Backend.AppUser.AppUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "issue")
@AllArgsConstructor
public class Issue {
    @Id
    @SequenceGenerator(
            name = "issue_sequence",
            sequenceName = "issue_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "issue_sequence"
    )
    private Long id;
    @ManyToOne
    @JoinColumn(name = "assignee", nullable = false, updatable = false)
    private AppUser assignee;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "created_by", referencedColumnName = "id")
    private AppUser createdBy;

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private IssueStatus issueStatus;

    @Enumerated(EnumType.STRING)
    private IssueType issueType;

    @Enumerated(EnumType.STRING)
    private Priority priority;
    @ManyToOne
    @JoinColumn(name = "sprint", nullable = false, updatable = false)
    private Sprint sprint;

    public enum IssueStatus {
        IN_PROGRESS, OPEN, CLOSED, REOPENED, RESOLVED
    }

    public enum Priority {
        CRITICAL, MAJOR, MINOR, BLOCKER, TRIVIAL
    }

    public enum IssueType {
        TASK, BUG, IMPROVEMENT, NEW_FEATURE, STORY
    }
}
