package Convene.Backend.SoftwareProject.Issue;

import lombok.AllArgsConstructor;
import lombok.Data;

public class IssueDto {
    @AllArgsConstructor
    @Data
    public static class CreateIssueRequest {
        private Long assigneeId;
        private String name;
        private String description;
        private Issue.IssueStatus issueStatus;
        private Issue.Priority priority;
        private Issue.IssueType issueType;
    }
}
