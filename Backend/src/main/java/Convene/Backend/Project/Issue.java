package Convene.Backend.Project;

public class Issue {
    private Long id;
    private Long assigneeId;
    private String name;
    private String description;
    private IssueStatus issueStatus;
    private IssueType issueType;
    private Priority priority;

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
